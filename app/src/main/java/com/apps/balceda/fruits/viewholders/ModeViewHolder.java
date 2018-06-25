package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;

public class ModeViewHolder extends RecyclerView.ViewHolder{

  private TextView fruitName;
  private ImageView fruitImage;
  private String imagenURL;

  private ModeViewHolder.ClickListener mClickListener;

  public ModeViewHolder(View itemView) {
    super(itemView);

    fruitImage = itemView.findViewById(R.id.mode_image);
    fruitName = itemView.findViewById(R.id.mode_name);

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

  public void setOnClickListener(ModeViewHolder.ClickListener clickListener){
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

  public String getImagenURL() {
    return imagenURL;
  }

  public void setImagenURL(String imagenURL) {
    this.imagenURL = imagenURL;
  }
}
