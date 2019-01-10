package com.apps.balceda.fruits.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.balceda.fruits.R;
import com.shawnlin.numberpicker.NumberPicker;

public class PersonalFruitViewHolder extends RecyclerView.ViewHolder {

    private TextView fruitName;
    private ImageView fruitImage;
    private TextView unitPrice, subTotalPrice;
    private String imageURL;
    private double subtotal;
    public CheckBox cbFruit;
    public NumberPicker numberPicker;

    public PersonalFruitViewHolder(View itemView) {
        super(itemView);

        fruitImage = itemView.findViewById(R.id.personal_fruit_image);
        fruitName = itemView.findViewById(R.id.personal_fruit_name);
        unitPrice = itemView.findViewById(R.id.personal_fruit_price);
        subTotalPrice = itemView.findViewById(R.id.personal_fruit_price_subtotal);
        cbFruit = itemView.findViewById(R.id.checkBox);
        numberPicker = itemView.findViewById(R.id.personal_number_picker);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public TextView getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(TextView subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
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

    public TextView getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(TextView unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
