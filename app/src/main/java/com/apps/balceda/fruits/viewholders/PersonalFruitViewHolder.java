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
    private TextView priceUnit, priceSubTotal;
    private String imagenURL;
    private double subtotal;
    public CheckBox cbFruit;
    public NumberPicker numberPicker;

    public PersonalFruitViewHolder(View itemView) {
        super(itemView);

        fruitImage = itemView.findViewById(R.id.personal_fruta_imagen);
        fruitName = itemView.findViewById(R.id.personal_fruta_nombre);
        priceUnit = itemView.findViewById(R.id.personal_fruta_precio);
        priceSubTotal = itemView.findViewById(R.id.personal_fruta_precio_subtotal);
        cbFruit = itemView.findViewById(R.id.checkBox);
        numberPicker = itemView.findViewById(R.id.personal_number_picker);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public TextView getPriceSubTotal() {
        return priceSubTotal;
    }

    public void setPriceSubTotal(TextView priceSubTotal) {
        this.priceSubTotal = priceSubTotal;
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

    public TextView getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(TextView priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }
}
