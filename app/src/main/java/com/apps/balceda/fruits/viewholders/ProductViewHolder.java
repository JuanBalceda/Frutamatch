package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;

public class ProductViewHolder extends RecyclerView.ViewHolder{

  private TextView productName;
  private ImageView productImage;
  private String productPrice;
  private String imagenURL;

  private ProductViewHolder.ClickListener mClickListener;

  public ProductViewHolder(View itemView) {
    super(itemView);

    productImage = itemView.findViewById(R.id.fruit_image);
    productName = itemView.findViewById(R.id.fruit_name);

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

  public void setOnClickListener(ProductViewHolder.ClickListener clickListener){
    mClickListener = clickListener;
  }

  public TextView getProductName() {
    return productName;
  }

  public void setProductName(TextView fruitName) {
    this.productName = fruitName;
  }

  public ImageView getProductImage() {
    return productImage;
  }

  public void setProductImage(ImageView productImage) {
    this.productImage = productImage;
  }

  public String getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }

  public String getImagenURL() {
    return imagenURL;
  }

  public void setImagenURL(String imagenURL) {
    this.imagenURL = imagenURL;
  }
}
