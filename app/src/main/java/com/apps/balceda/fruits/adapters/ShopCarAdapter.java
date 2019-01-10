package com.apps.balceda.fruits.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.balceda.fruits.activities.home.HomeActivity;
import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.viewholders.ShopCarViewHolder;
import com.apps.balceda.fruits.models.ShopCar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarViewHolder> {

    private Context context;
    private ArrayList<ShopCar> orders;

    public ShopCarAdapter(Context context, ArrayList<ShopCar> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ShopCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_car, parent, false);
        return new ShopCarViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ShopCarViewHolder holder, int position) {

        if (orders.get(position).getProduct() == null) {
            holder.fruitName.setText(orders.get(position).getOrder().get(0).getName());

            double unitPrice = Double.parseDouble(orders.get(position).getOrder().get(0).getPrice());
            String pricePerKilogram = String.format("S/ %1$,.2f", unitPrice);
            holder.fruitPrice.setText("Precio por kilo: " + pricePerKilogram);

            double productAmount = orders.get(position).getSubTotal() / Double.parseDouble(orders.get(position).getOrder().get(0).getPrice());
            holder.kilograms.setText("Kilos solicitados: " + String.format("%1$,.0f", productAmount));
            holder.subtotal.setText("Cargo: " + String.format("S/ %1$,.2f", orders.get(position).getSubTotal()));
            Picasso.with(context).load(orders.get(position).getOrder().get(0).getImage()).into(holder.FruitImage);

            holder.trash.setOnClickListener((v) -> {
                orders.remove(position);
                notifyDataSetChanged();
                ShopCarActivity.calculate();
            });
        } else {
            holder.fruitName.setText(orders.get(position).getProduct().getName());

            double unitPrice = Double.parseDouble(orders.get(position).getProduct().getPrice());
            String pricePerKilogram = String.format("S/ %1$,.2f", unitPrice);
            holder.fruitPrice.setText("Precio unitario: " + pricePerKilogram);

            double productAmount = orders.get(position).getSubTotal() / Double.parseDouble(orders.get(position).getProduct().getPrice());
            holder.kilograms.setText("Unidades solicitadas: " + String.format("%1$,.0f", productAmount));
            holder.subtotal.setText("Cargo: " + String.format("S/ %1$,.2f", orders.get(position).getSubTotal()));
            Picasso.with(context).load(orders.get(position).getProduct().getImage()).into(holder.FruitImage);

            holder.trash.setOnClickListener((v) -> {
                orders.remove(position);
                notifyDataSetChanged();
                ShopCarActivity.calculate();
            });
        }


    }

    @Override
    public int getItemCount() {
        return HomeActivity.finalOrder.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<ShopCar> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<ShopCar> orders) {
        this.orders = orders;
    }
}
