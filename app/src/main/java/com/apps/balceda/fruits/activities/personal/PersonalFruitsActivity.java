package com.apps.balceda.fruits.activities.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class PersonalFruitsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String productName;
    String productPrice;
    String productImg;
    double additional;
    String detail;

    FirebaseDatabase database;
    DatabaseReference fruitsReference;

    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Fruit, PersonalFruitViewHolder> adapter;

    Button button;

    ArrayList<PersonalFruitViewHolder> personalFruitViewHolders;
    ArrayList<Fruit> fruits;
    SparseBooleanArray fruitStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_fruits);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Seleccione sus Frutas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productName = getIntent().getExtras().getString("productName");
        productPrice = getIntent().getExtras().getString("productPrice");
        productImg = getIntent().getExtras().getString("productImage");

        personalFruitViewHolders = new ArrayList<>();
        fruits = new ArrayList<>();
        fruitStates = new SparseBooleanArray();

        //iniciar Firebase
        database = FirebaseDatabase.getInstance();
        fruitsReference = database.getReference("Fruits");

        //Lista
        recyclerMenu = findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);
        loadFirebaseData(fruitsReference);

        button = findViewById(R.id.personal_toShop);
        button.setOnClickListener((v) -> {
            for (int i = 0; i > fruitStates.size(); i++) {
                fruitStates.get(i);
            }
            calcular();
        });
    }

    private void calcular() {
        if (personalFruitViewHolders.size() > 0) {
            detail = "";
            for (int i = 0; i < personalFruitViewHolders.size(); i++) {
                additional += personalFruitViewHolders.get(i).getSubtotal();
                if ((i + 1) == personalFruitViewHolders.size()) {
                    detail += personalFruitViewHolders.get(i).getFruitName().getText().toString() + ".";
                } else {
                    detail += personalFruitViewHolders.get(i).getFruitName().getText().toString() + ", ";
                }
            }
            Intent intent = new Intent(PersonalFruitsActivity.this, ProductDetailsActivity.class);
            intent.putExtra("productName", productName);
            intent.putExtra("productPrice", productPrice);
            intent.putExtra("productImage", productImg);
            intent.putExtra("additional", additional);
            intent.putExtra("detail", detail);
            intent.putExtra("fruits", fruits);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar al menos una fruta para continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFirebaseData(Query databaseReference) {
        adapter = new FirebaseRecyclerAdapter<Fruit, PersonalFruitViewHolder>(
                Fruit.class, R.layout.item_personal_fruit,
                PersonalFruitViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(final PersonalFruitViewHolder viewHolder, final Fruit model, int position) {
                boolean currentState = fruitStates.get(position, false);
                if (currentState) {
                    viewHolder.cbFruit.setChecked(true);
                } else {
                    viewHolder.cbFruit.setChecked(false);
                }
                double pricePerProduct = Double.parseDouble(model.getPriceUnit()) * Double.parseDouble(productPrice);
                viewHolder.getUnitPrice().setText(String.format("S/ %1$,.2f", pricePerProduct));
                viewHolder.getFruitName().setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.getFruitImage());
                viewHolder.setImageURL(model.getImage());
                viewHolder.getSubTotalPrice().setText("Total a pagar: " + String.format("S/ %1$,.2f", pricePerProduct));
                viewHolder.setSubtotal(pricePerProduct);

                if (productName.equalsIgnoreCase("Jugos") || productName.equalsIgnoreCase("Ensaladas") || productName.equalsIgnoreCase("Aguas")) {
                    viewHolder.getSubTotalPrice().setVisibility(View.GONE);
                    viewHolder.numberPicker.setVisibility(View.GONE);
                } else {
                    // OnValueChangeListener
                    viewHolder.numberPicker.setOnValueChangedListener((NumberPicker picker, int oldVal, int newVal) -> {
                        double precioSubTotal = pricePerProduct * newVal;
                        viewHolder.getSubTotalPrice().setText("Total a pagar: " + String.format("S/ %1$,.2f", precioSubTotal));
                        viewHolder.setSubtotal(precioSubTotal);
                        // viewHolder.cbFruit.setChecked(true);
                    });
                }

                viewHolder.cbFruit.setOnClickListener(v -> {
                    if (viewHolder.cbFruit.isChecked()) {
                        viewHolder.cbFruit.setChecked(true);
                        fruitStates.append(position, true);
                        personalFruitViewHolders.add(viewHolder);
                        fruits.add(model);
                    } else {
                        viewHolder.cbFruit.setChecked(false);
                        fruitStates.append(position, false);
                        personalFruitViewHolders.remove(viewHolder);
                        fruits.remove(model);
                    }
                });
            }
        };
        recyclerMenu.setAdapter(adapter);
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
            loadFirebaseData(fruitsReference);
        } else {
            Query fruitsFiltered = fruitsReference.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
            loadFirebaseData(fruitsFiltered);
        }
    }

}
