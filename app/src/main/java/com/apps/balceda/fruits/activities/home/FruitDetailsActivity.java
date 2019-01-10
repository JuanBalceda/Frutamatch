package com.apps.balceda.fruits.activities.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.models.Fruit;
import com.apps.balceda.fruits.models.ShopCar;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FruitDetailsActivity extends AppCompatActivity {

    TextView fruit, price, subtotal;
    ImageView image;
    double pricePerKilogram, subTotalPrice;
    Button shopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_details);
        setTitle("Detalles de la Fruta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = findViewById(R.id.image);
        fruit = findViewById(R.id.txt_fruit);
        price = findViewById(R.id.txt_price);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        subtotal = findViewById(R.id.subtotal);
        shopButton = findViewById(R.id.toShop);

        Picasso.with(getBaseContext()).load(getIntent().getExtras().getString("image")).into(image);
        fruit.setText(getIntent().getExtras().getString("frutaName"));
        pricePerKilogram = Double.parseDouble(getIntent().getExtras().getString("price"));
        price.setText("Precio por kilogramo: S/ " + pricePerKilogram);

        // OnClickListener
        numberPicker.setOnClickListener((view) -> {
        });
        // OnValueChangeListener
        numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
            subTotalPrice = pricePerKilogram * newVal;
            subtotal.setText("Total a pagar: S/ " + subTotalPrice);
        });
        subTotalPrice = pricePerKilogram;
        subtotal.setText("Total a pagar: S/ " + pricePerKilogram);

        shopButton.setOnClickListener((view) -> {
            ShopCar newShopCar = new ShopCar();
            //Setting Fruit
            Fruit frutaSolicitada = new Fruit();
            frutaSolicitada.setName(fruit.getText().toString());
            frutaSolicitada.setImage(getIntent().getExtras().getString("image"));
            frutaSolicitada.setPrice(String.valueOf(pricePerKilogram));

            //Adding Fruit to ShopCar
            ArrayList<Fruit> pedido = new ArrayList<>();
            pedido.add(frutaSolicitada);
            newShopCar.setOrder(pedido);
            newShopCar.setSubTotal(subTotalPrice);
            newShopCar.setProduct(null);
            //Adding to Final List
            HomeActivity.finalOrder.add(newShopCar);
            Toast.makeText(getApplicationContext(), "Añadido al carrito", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.shopping:
                Intent intent = new Intent(this, ShopCarActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
