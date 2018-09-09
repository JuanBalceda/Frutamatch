package com.apps.balceda.fruits.activities.personal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.activities.login.LoginActivity;
import com.apps.balceda.fruits.models.Product;
import com.apps.balceda.fruits.viewholders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class PersonalHomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference products;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home);

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

            @NonNull
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
                        //Info
                    }
                });
                return viewHolder;
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    /*
     * Load menu on the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    /*
     * Load options for the menu on the toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.shopping:
                intent = new Intent(this, ShopCarActivity.class);
                startActivity(intent);
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
