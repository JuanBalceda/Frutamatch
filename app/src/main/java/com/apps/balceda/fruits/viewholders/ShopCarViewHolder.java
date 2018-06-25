package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;

public class ShopCarViewHolder extends RecyclerView.ViewHolder {
  public TextView nombreFruta, precioFruta, kilos, subtotal;
  public ImageView imagenFruta, trash;

  public ShopCarViewHolder(View itemView) {
    super(itemView);
    this.nombreFruta = itemView.findViewById(R.id.frutaNombreShopCar);
    this.precioFruta = itemView.findViewById(R.id.frutaPrecioShopCar);
    this.imagenFruta = itemView.findViewById(R.id.imagenFrutaShopCar);
    this.kilos = itemView.findViewById(R.id.frutaKilosShopCar);
    this.subtotal = itemView.findViewById(R.id.frutaSubtotalShopCar);
    this.trash = itemView.findViewById(R.id.trash);
  }
}