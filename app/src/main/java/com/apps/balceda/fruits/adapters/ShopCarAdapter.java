package com.apps.balceda.fruits.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.balceda.fruits.activities.hogar.HogarHomeActivity;
import com.apps.balceda.fruits.R;
import com.apps.balceda.fruits.activities.ShopCarActivity;
import com.apps.balceda.fruits.viewholders.ShopCarViewHolder;
import com.apps.balceda.fruits.models.ShopCar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarViewHolder> {

    Context context;
    ArrayList<ShopCar> pedidos;

    public ShopCarAdapter(Context context, ArrayList<ShopCar> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public ShopCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_car, parent, false);

        ShopCarViewHolder shopCarViewHolder = new ShopCarViewHolder(view);
        return shopCarViewHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ShopCarViewHolder holder, int position) {

        if (pedidos.get(position).getProduct() == null){
            holder.nombreFruta.setText(pedidos.get(position).getPedido().get(0).getName());

            double precioUnitario = Double.parseDouble(pedidos.get(position).getPedido().get(0).getPrice());
            String precioKilo = String.format("S/ %1$,.2f", precioUnitario);
            holder.precioFruta.setText("Precio por kilo: " + precioKilo);

            double cantidad = pedidos.get(position).getSubTotal() / Double.parseDouble(pedidos.get(position).getPedido().get(0).getPrice());
            holder.kilos.setText("Kilos solicitados: " + String.format("%1$,.0f", cantidad));
            holder.subtotal.setText("Cargo: " + String.format("S/ %1$,.2f", pedidos.get(position).getSubTotal()));
            Picasso.with(context).load(pedidos.get(position).getPedido().get(0).getImage()).into(holder.imagenFruta);

            holder.trash.setOnClickListener((v) -> {
                pedidos.remove(position);
                notifyDataSetChanged();
                ShopCarActivity.calcular();
            });
        }else{
            holder.nombreFruta.setText(pedidos.get(position).getProduct().getName());

            double precioUnitario = Double.parseDouble(pedidos.get(position).getProduct().getPrice());
            String precioKilo = String.format("S/ %1$,.2f", precioUnitario);
            holder.precioFruta.setText("Precio unitario: " + precioKilo);

            double cantidad = pedidos.get(position).getSubTotal() / Double.parseDouble(pedidos.get(position).getProduct().getPrice());
            holder.kilos.setText("Unidades solicitadas: " + String.format("%1$,.0f", cantidad));
            holder.subtotal.setText("Cargo: " + String.format("S/ %1$,.2f", pedidos.get(position).getSubTotal()));
            Picasso.with(context).load(pedidos.get(position).getProduct().getImage()).into(holder.imagenFruta);

            holder.trash.setOnClickListener((v) -> {
                pedidos.remove(position);
                notifyDataSetChanged();
                ShopCarActivity.calcular();
            });
        }


    }

    @Override
    public int getItemCount() {
        return HogarHomeActivity.pedidoFinal.size();
    }
}
