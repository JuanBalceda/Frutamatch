package com.apps.balceda.fruits.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.balceda.fruits.activities.hogar.HogarHomeActivity;
import com.apps.balceda.fruits.activities.personal.PersonalHomeActivity;
import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.viewholders.ModeViewHolder;
import com.apps.balceda.fruits.models.Mode;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeSelectorActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference modes;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_selector);

        //iniciar Firebase
        database = FirebaseDatabase.getInstance();
        modes = database.getReference("Modes");

        //Lista
        recycler_menu = findViewById(R.id.recycler_home_selector);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadMenu();
    }

    private void loadMenu() {
        FirebaseRecyclerAdapter<Mode, ModeViewHolder> adapter =
                new FirebaseRecyclerAdapter<Mode, ModeViewHolder>(
                        Mode.class, R.layout.item_home_selector,
                        ModeViewHolder.class, modes) {

                    @Override
                    protected void populateViewHolder(
                            final ModeViewHolder viewHolder,
                            final Mode model,
                            int position) {
                        viewHolder.getFruitName().setText(model.getName());
                        Picasso.with(getBaseContext())
                                .load(model.getImage())
                                .into(viewHolder.getFruitImage());
                        viewHolder.setImagenURL(model.getImage());
                    }

                    @NonNull
                    @Override
                    public ModeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        final ModeViewHolder viewHolder =
                                super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ModeViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent;
                                switch (position) {
                                    case 0:
                                        intent = new Intent(
                                                HomeSelectorActivity.this,
                                                PersonalHomeActivity.class);
                                        startActivity(intent);
                                        //Toast.makeText(getApplicationContext(),
                                        // viewHolder.getFruitName().getText(),
                                        // Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        intent = new Intent(
                                                HomeSelectorActivity.this,
                                                HogarHomeActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        Toast.makeText(getApplicationContext(),
                                                viewHolder.getFruitName().getText(),
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(),
                                                "Proximamente",
                                                Toast.LENGTH_SHORT).show();
                                }
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
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem search = menu.findItem(R.id.action_search);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
