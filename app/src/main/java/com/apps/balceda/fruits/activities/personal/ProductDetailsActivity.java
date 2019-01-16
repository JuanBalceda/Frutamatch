package com.apps.balceda.fruits.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.activities.home.HomeActivity;
import com.apps.balceda.fruits.activities.login.LoginActivity;
import com.apps.balceda.fruits.models.Fruit;
import com.apps.balceda.fruits.models.Product;
import com.apps.balceda.fruits.models.ShopCar;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    String product;
    double productPrice;
    String productImage;
    double additional;
    String detail;
    ArrayList<Fruit> fruits;

    TextView productName, priceTextView, subtotal, description;
    ImageView imageView;
    double unitPrice, subtotalPrice;
    Button shopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitle("Detalles del Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent's extras
        product = getIntent().getExtras().getString("productName");
        productPrice = Double.parseDouble(getIntent().getExtras().getString("productPrice"));
        productImage = getIntent().getExtras().getString("productImage");
        additional = getIntent().getExtras().getDouble("additional");
        detail = getIntent().getExtras().getString("detail");
        fruits = (ArrayList<Fruit>) getIntent().getExtras().get("fruits");


        imageView = findViewById(R.id.image);
        productName = findViewById(R.id.txt_fruit);
        description = findViewById(R.id.txt_description);
        priceTextView = findViewById(R.id.txt_price);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        subtotal = findViewById(R.id.subtotal);
        shopButton = findViewById(R.id.toShop);

        Picasso.with(getBaseContext()).load(productImage).into(imageView);
        productName.setText(product);
        description.setText("Frutas: " + detail);

        // unitPrice = productPrice + additional;
        unitPrice = additional;
        priceTextView.setText("Precio: " + String.format("S/ %1$,.2f", unitPrice));

        subtotalPrice = unitPrice;
        subtotal.setText("Total a pagar: " + String.format("S/ %1$,.2f", unitPrice));
        // OnValueChangeListener
        numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
            subtotalPrice = unitPrice * newVal;
            subtotal.setText("Total a pagar: " + String.format("S/ %1$,.2f", subtotalPrice));
        });

        shopButton.setOnClickListener((view) -> {
            ShopCar newShopCar = new ShopCar();
            //Setting Fruit
            Product productoSolicitado = new Product();
            productoSolicitado.setName(productName.getText().toString());
            productoSolicitado.setImage(getIntent().getExtras().getString("productImage"));
            productoSolicitado.setPrice(String.valueOf(unitPrice));
            newShopCar.setProduct(productoSolicitado);

            newShopCar.setOrder(fruits);
            newShopCar.setSubTotal(subtotalPrice);

            //Adding to Final List
            HomeActivity.finalOrder.add(newShopCar);
            Intent intent = new Intent(this, ShopCarActivity.class);
            startActivity(intent);
            finish();
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
                //finish();
                return true;
            case R.id.exit:
                LoginActivity.signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
