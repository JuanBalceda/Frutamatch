package com.apps.balceda.fruits.activities.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.models.Fruit;
import com.apps.balceda.fruits.viewholders.PersonalFruitViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalFruitsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String productName;
    String productPrice;
    String productImg;
    double adicional;
    String detalle;

    FirebaseDatabase database;
    DatabaseReference fruits;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Fruit, PersonalFruitViewHolder> adapter;

    Button button;

    ArrayList<PersonalFruitViewHolder> frutas;
    ArrayList<Fruit> frutasProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_fruits);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Seleccione sus frutas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productName = getIntent().getExtras().getString("productName");
        productPrice = getIntent().getExtras().getString("productPrice");
        productImg = getIntent().getExtras().getString("productImg");

        frutas = new ArrayList<>();
        frutasProducto = new ArrayList<>();

        //iniciar Firebase
        database = FirebaseDatabase.getInstance();
        fruits = database.getReference("Fruits");

        //Lista
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadFirebaseData(fruits);

        button = findViewById(R.id.personal_toShop);
        button.setOnClickListener((v) -> {
            calcular();
        });
    }

    private void calcular() {
        if (frutas.size() > 0) {
            detalle = "";
            for (int i = 0; i < frutas.size(); i++) {
                adicional += frutas.get(i).getSubtotal();
                if ((i + 1) == frutas.size()) {
                    detalle += frutas.get(i).getFruitName().getText().toString() + ".";
                } else {
                    detalle += frutas.get(i).getFruitName().getText().toString() + ", ";
                }
            }
            Intent intent = new Intent(PersonalFruitsActivity.this, ProductDetailsActivity.class);
            intent.putExtra("productName", productName);
            intent.putExtra("productPrice", productPrice);
            intent.putExtra("productImg", productImg);
            intent.putExtra("adicional", adicional);
            intent.putExtra("detalle", detalle);
            intent.putExtra("frutasProducto", frutasProducto);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Seleccione las frutas", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFirebaseData(Query databaseReference) {
        adapter = new FirebaseRecyclerAdapter<Fruit, PersonalFruitViewHolder>(
                Fruit.class, R.layout.item_personal_fruit,
                PersonalFruitViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(final PersonalFruitViewHolder viewHolder, final Fruit model, int position) {
                viewHolder.getPriceUnit().setText("S/ " + model.getPriceUnit());
                viewHolder.getFruitName().setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.getFruitImage());
                viewHolder.setImagenURL(model.getImage());
                viewHolder.getPriceSubTotal().setText("Total a pagar: " + String.format("S/ %1$,.2f", Double.parseDouble(model.getPriceUnit())));
                viewHolder.setSubtotal(Double.parseDouble(model.getPriceUnit()));
                // OnValueChangeListener
                viewHolder.numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
                    double precioSubTotal = Double.parseDouble(model.getPriceUnit()) * newVal;
                    viewHolder.getPriceSubTotal().setText("Total a pagar: " + String.format("S/ %1$,.2f", precioSubTotal));
                    viewHolder.setSubtotal(precioSubTotal);
                    viewHolder.cbFruit.setChecked(true);
                });
                viewHolder.cbFruit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            frutas.add(viewHolder);
                            frutasProducto.add(model);
                        } else {
                            frutas.remove(viewHolder);
                            frutasProducto.remove(model);
                        }
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_home, menu);

        final MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.shopping:
                intent = new Intent(this, ShopCarActivity.class);
                startActivity(intent);
                return true;
            //Go Back to home Activity
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String query;
        if (newText.isEmpty()) {
            query = "";
        } else {
            query = Character.toUpperCase(newText.charAt(0)) + newText.substring(1);
        }
        loadMenu(query.trim());
        adapter.notifyDataSetChanged();
        return true;
    }

    private void loadMenu(String searchText) {
        if (searchText.isEmpty()) {
            loadFirebaseData(fruits);
        } else {
            Query fruitsFiltered = fruits.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
            loadFirebaseData(fruitsFiltered);
        }
    }

}
