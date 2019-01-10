package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;

public class FruitViewHolder extends RecyclerView.ViewHolder{

  private TextView fruitName;
  private ImageView fruitImage;
  private String price;
  private String imageURL;

  private FruitViewHolder.ClickListener mClickListener;

  public FruitViewHolder(View itemView) {
    super(itemView);

    fruitImage = itemView.findViewById(R.id.fruit_image);
    fruitName = itemView.findViewById(R.id.fruit_name);

    itemView.setOnClickListener((v) -> {
       mClickListener.onItemClick(v, getAdapterPosition());
    });
    itemView.setOnLongClickListener((v) -> {
        mClickListener.onItemLongClick(v, getAdapterPosition());
        return true;
    });

  }
  //Interface to send callbacks...
  public interface ClickListener{
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
  }

  public void setOnClickListener(FruitViewHolder.ClickListener clickListener){
    mClickListener = clickListener;
  }

  public TextView getFruitName() {
    return fruitName;
  }

  public void setFruitName(TextView fruitName) {
    this.fruitName = fruitName;
  }

  public ImageView getFruitImage() {
    return fruitImage;
  }

  public void setFruitImage(ImageView fruitImage) {
    this.fruitImage = fruitImage;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
}
