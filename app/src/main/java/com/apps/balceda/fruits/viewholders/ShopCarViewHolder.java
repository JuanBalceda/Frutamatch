package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;

public class ShopCarViewHolder extends RecyclerView.ViewHolder {
  public TextView fruitName, fruitPrice, kilograms, subtotal;
  public ImageView FruitImage, trash;

  public ShopCarViewHolder(View itemView) {
    super(itemView);
    this.fruitName = itemView.findViewById(R.id.fruit_name_shop_car);
    this.fruitPrice = itemView.findViewById(R.id.fruit_price_shop_car);
    this.FruitImage = itemView.findViewById(R.id.image_fruit_shop_car);
    this.kilograms = itemView.findViewById(R.id.fruit_kilograms_shop_car);
    this.subtotal = itemView.findViewById(R.id.fruit_subtotal_shop_car);
    this.trash = itemView.findViewById(R.id.trash);
  }
}