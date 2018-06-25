package com.apps.balceda.fruits.activities.hogar;

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
import com.apps.balceda.fruits.activities.hogar.HogarHomeActivity;
import com.apps.balceda.fruits.models.Fruit;
import com.apps.balceda.fruits.models.ShopCar;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FruitDetailsActivity extends AppCompatActivity {

    TextView fruta, precio, subtotal;
    ImageView imagen;
    double precioKilo, precioSubTotal;
    Button botonComprar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_details);
        setTitle("Detalles de la Fruta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen = findViewById(R.id.imagen);
        fruta = findViewById(R.id.fruta);
        precio = findViewById(R.id.precio);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        subtotal = findViewById(R.id.subtotal);
        botonComprar = findViewById(R.id.toShop);

        Picasso.with(getBaseContext()).load(getIntent().getExtras().getString("imagen")).into(imagen);
        fruta.setText(getIntent().getExtras().getString("frutaName"));
        precioKilo = Double.parseDouble(getIntent().getExtras().getString("precio"));
        precio.setText("Precio por kilogramo: S/ " + precioKilo);

        // OnClickListener
        numberPicker.setOnClickListener((view) -> {
        });
        // OnValueChangeListener
        numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
            precioSubTotal = precioKilo * newVal;
            subtotal.setText("Total a pagar: S/ " + precioSubTotal);
        });
        precioSubTotal = precioKilo;
        subtotal.setText("Total a pagar: S/ " + precioKilo);

        botonComprar.setOnClickListener((view) -> {
            ShopCar newShopCar = new ShopCar();
            //Setting Fruit
            Fruit frutaSolicitada = new Fruit();
            frutaSolicitada.setName(fruta.getText().toString());
            frutaSolicitada.setImage(getIntent().getExtras().getString("imagen"));
            frutaSolicitada.setPrice(String.valueOf(precioKilo));

            //Adding Fruit to ShopCar
            ArrayList<Fruit> pedido = new ArrayList<>();
            pedido.add(frutaSolicitada);
            newShopCar.setPedido(pedido);
            newShopCar.setSubTotal(precioSubTotal);
            newShopCar.setProduct(null);
            //Adding to Final List
            HogarHomeActivity.pedidoFinal.add(newShopCar);
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
