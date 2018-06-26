package com.apps.balceda.fruits.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.apps.balceda.fruits.models.Product;
import com.apps.balceda.fruits.models.ShopCar;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    String product;
    double productPrice;
    String productImg;
    double adicional;
    String detalle;
    ArrayList<Fruit> frutasProducto;

    TextView productName, precio, subtotal, descripcion;
    ImageView imagen;
    double precioUnit, precioSubTotal;
    Button botonComprar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitle("Detalles del Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent's extras
        product = getIntent().getExtras().getString("productName");
        productPrice = Double.parseDouble(getIntent().getExtras().getString("productPrice"));
        productImg = getIntent().getExtras().getString("productImg");
        adicional = getIntent().getExtras().getDouble("adicional");
        detalle = getIntent().getExtras().getString("detalle");
        frutasProducto = (ArrayList<Fruit>) getIntent().getExtras().get("frutasProducto");



        imagen = findViewById(R.id.imagen);
        productName = findViewById(R.id.fruta);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);
        NumberPicker numberPicker = findViewById(R.id.number_picker);
        subtotal = findViewById(R.id.subtotal);
        botonComprar = findViewById(R.id.toShop);

        Picasso.with(getBaseContext()).load(productImg).into(imagen);
        productName.setText(product);
        descripcion.setText("Frutas: " + detalle);

        precioUnit = productPrice + adicional;
        precio.setText("Precio: " + String.format("S/ %1$,.2f", precioUnit));

        precioSubTotal = precioUnit;
        subtotal.setText("Total a pagar: " + String.format("S/ %1$,.2f", precioUnit));
        // OnValueChangeListener
        numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
            precioSubTotal = precioUnit * newVal;
            subtotal.setText("Total a pagar: " + String.format("S/ %1$,.2f", precioSubTotal));
        });

        botonComprar.setOnClickListener((view) -> {
          ShopCar newShopCar = new ShopCar();
          //Setting Fruit
          Product productoSolicitado = new Product();
          productoSolicitado.setName(productName.getText().toString());
          productoSolicitado.setImage(getIntent().getExtras().getString("productImg"));
          productoSolicitado.setPrice(String.valueOf(precioUnit));
          newShopCar.setProduct(productoSolicitado);

          newShopCar.setPedido(frutasProducto);
          newShopCar.setSubTotal(precioSubTotal);

          //Adding to Final List
          HogarHomeActivity.pedidoFinal.add(newShopCar);
          Toast.makeText(getApplicationContext(), "Añadido al carrito",Toast.LENGTH_LONG).show();

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
