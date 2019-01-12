package com.apps.balceda.fruits.activities.payment;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.culqi.Card;
import com.apps.balceda.fruits.culqi.Token;
import com.apps.balceda.fruits.culqi.TokenCallback;
import com.apps.balceda.fruits.validation.Validation;

import org.json.JSONObject;

import static com.apps.balceda.fruits.activities.login.LoginActivity.mAuth;

public class CulqiPaymentActivity extends AppCompatActivity {

    Validation validation;

    ProgressDialog progress;

    TextView txt_cardnumber, txt_cvv, txt_month, txt_year, txt_email, kind_card, result;
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culqi_payment);

        validation = new Validation();

        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.validation_message));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        txt_cardnumber = findViewById(R.id.txt_cardnumber);
        txt_cvv = findViewById(R.id.txt_cvv);
        txt_month = findViewById(R.id.txt_month);
        txt_year = findViewById(R.id.txt_year);
        txt_email = findViewById(R.id.txt_email);
        kind_card = findViewById(R.id.kind_card);
        result = findViewById(R.id.token_id);
        btnPay = findViewById(R.id.btn_pay);

        txt_cvv.setEnabled(false);

        txt_cardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    txt_cvv.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txt_cardnumber.getText().toString();
                // Validations go here
                if (s.length() == 0) {
                    // txt_cardnumber.setBackgroundResource(R.drawable.border_error);
                }

                if (validation.luhn(text)) {
                    // txt_cardnumber.setBackgroundResource(R.drawable.border_sucess);
                } else {
                    // txt_cardnumber.setBackgroundResource(R.drawable.border_error);
                }

                int cvv = validation.bin(text, kind_card);
                if (cvv > 0) {
                    txt_cvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cvv)});
                    txt_cvv.setEnabled(true);
                } else {
                    txt_cvv.setEnabled(false);
                    txt_cvv.setText("");
                }
            }
        });

        txt_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txt_year.getText().toString();
                // Validation goes here
                if (validation.year(text)) {
                    // txt_year.setBackgroundResource(R.drawable.border_error);
                } else {
                    // txt_year.setBackgroundResource(R.drawable.border_sucess);
                }
            }
        });

        txt_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txt_month.getText().toString();
                // Validation goes here

                if (validation.month(text)) {
                    // txt_month.setBackgroundResource(R.drawable.border_error);
                } else {
                    // txt_month.setBackgroundResource(R.drawable.border_sucess);
                }
            }
        });

        txt_email.setText(mAuth.getCurrentUser().getEmail());
        btnPay.setOnClickListener(view -> {
            progress.show();
            Card card = new Card(txt_cardnumber.getText().toString(),
                    txt_cvv.getText().toString(),
                    Integer.valueOf(txt_month.getText().toString()),
                    Integer.valueOf(txt_year.getText().toString()),
                    txt_email.getText().toString());

            Token token = new Token("pk_test_vzMuTHoueOMlgUPj");

            token.createToken(getApplicationContext(), card, new TokenCallback() {
                @Override
                public void onSuccess(JSONObject token) {
                    try {
                        result.setText(token.get("id").toString());
                        Log.println(Log.INFO, "", token.get("id").toString());
                    } catch (Exception ex) {
                        Log.println(Log.ERROR, "", ex.getMessage());
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        progress.hide();
                    }
                    progress.hide();
                    Toast.makeText(getApplicationContext(), "Pago realizado correctamente", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Exception error) {
                    Log.println(Log.ERROR, "", error.getMessage());
                    progress.hide();
                }
            });

        });
    }
}
