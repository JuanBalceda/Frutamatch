package com.apps.balceda.fruits.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.balceda.fruits.activities.hogar.HogarHomeActivity;
import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.adapters.ShopCarAdapter;

public class ShopCarActivity extends AppCompatActivity {

  RecyclerView recycler_menu;
  RecyclerView.LayoutManager layoutManager;
  ShopCarAdapter adapter;
  Button comprar;

  //Shopping Car
  static TextView subTotal, igv, total;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_car);
    setTitle("Carrito");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    //Lista
    recycler_menu = findViewById(R.id.recycler_shop_car);
    recycler_menu.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(this);
    recycler_menu.setLayoutManager(layoutManager);
    adapter = new ShopCarAdapter(getApplicationContext(), HogarHomeActivity.pedidoFinal);
    recycler_menu.setAdapter(adapter);

    subTotal = findViewById(R.id.subTotalCompra);
    igv = findViewById(R.id.igvCompra);
    total = findViewById(R.id.totalCompra);

    calcular();

    comprar = findViewById(R.id.comprar);
    comprar.setOnClickListener((v) ->{
      Toast.makeText(getApplicationContext(),"A pagar! $_$!!!",Toast.LENGTH_LONG).show();
    });
  }

  //Calculate Final Amount
  public static void calcular(){
    double subTotalCompraFinal = 0.0;
    double igvCompraFinal = 0.0;
    double totalCompraFinal = 0.0;

    if (HogarHomeActivity.pedidoFinal.size() > 0){
      for (int i = 0; i < HogarHomeActivity.pedidoFinal.size(); i++){
        subTotalCompraFinal += HogarHomeActivity.pedidoFinal.get(i).getSubTotal();
        igvCompraFinal += HogarHomeActivity.pedidoFinal.get(i).getIgvCompra();
        totalCompraFinal += HogarHomeActivity.pedidoFinal.get(i).getTotalCompra();
      }
    }else {
      subTotalCompraFinal = 0;
      igvCompraFinal = 0;
      totalCompraFinal = 0;
    }
    subTotal.setText(String.format("S/ %1$,.2f", subTotalCompraFinal));
    igv.setText(String.format("S/  %1$,.2f",igvCompraFinal));
    total.setText(String.format("S/  %1$,.2f",totalCompraFinal));
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
