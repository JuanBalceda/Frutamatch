package com.apps.balceda.fruits.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.balceda.fruits.activities.home.HomeActivity;
import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.payment.CulqiPaymentActivity;
import com.apps.balceda.fruits.adapters.ShopCarAdapter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;

public class ShopCarActivity extends AppCompatActivity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 0;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    ShopCarAdapter adapter;
    Button checkout;

    //Shopping Car
    static TextView subTotal, igv, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);

        setTitle("Carrito");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // List
        recycler_menu = findViewById(R.id.recycler_shop_car);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        adapter = new ShopCarAdapter(getApplicationContext(), HomeActivity.finalOrder);
        recycler_menu.setAdapter(adapter);

        subTotal = findViewById(R.id.subtotal_amount);
        igv = findViewById(R.id.igv_amount);
        total = findViewById(R.id.total);

        calculate();

        checkout = findViewById(R.id.buy);
        checkout.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), CulqiPaymentActivity.class)));
    }

    //Calculate Final Amount
    public static void calculate() {
        double finalSubtotalAmount = 0.0;
        double finalIgvAmount = 0.0;
        double finalTotalAmount = 0.0;

        if (HomeActivity.finalOrder.size() > 0) {
            for (int i = 0; i < HomeActivity.finalOrder.size(); i++) {
                finalSubtotalAmount += HomeActivity.finalOrder.get(i).getSubTotal();
                finalIgvAmount += HomeActivity.finalOrder.get(i).getIgvAmount();
                finalTotalAmount += HomeActivity.finalOrder.get(i).getTotal();
            }
        } else {
            finalSubtotalAmount = 0;
            finalIgvAmount = 0;
            finalTotalAmount = 0;
        }
        String moneyFormat = "S/ %1$,.2f";
        subTotal.setText(String.format(moneyFormat, finalSubtotalAmount));
        igv.setText(String.format(moneyFormat, finalIgvAmount));
        total.setText(String.format(moneyFormat, finalTotalAmount));
    }

    //Go Back to home Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
