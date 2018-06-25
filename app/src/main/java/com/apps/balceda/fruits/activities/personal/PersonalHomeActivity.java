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

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.models.Product;
import com.apps.balceda.fruits.viewholders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class PersonalHomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FirebaseDatabase database;
    DatabaseReference products;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Futamatch Personal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //iniciar Firebase
        database = FirebaseDatabase.getInstance();
        products = database.getReference("Personal");

        //Lista
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadFirebaseData(products);
    }


    private void loadFirebaseData(Query databaseReference) {
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class, R.layout.item_fruit,
                ProductViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(final ProductViewHolder viewHolder, final Product model, int position) {
                viewHolder.getProductName().setText(model.getName());
                viewHolder.setProductPrice(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.getProductImage());
                viewHolder.setImagenURL(model.getImage());
            }

            @Override
            public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ProductViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new ProductViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(PersonalHomeActivity.this, PersonalFruitsActivity.class);
                        intent.putExtra("productName", viewHolder.getProductName().getText());
                        intent.putExtra("productPrice", viewHolder.getProductPrice());
                        intent.putExtra("productImg", viewHolder.getImagenURL());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                return viewHolder;
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
            loadFirebaseData(products);
        } else {
            Query fruitsFiltered = products.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
            loadFirebaseData(fruitsFiltered);
        }
    }

}
