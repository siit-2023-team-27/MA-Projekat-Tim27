package com.example.nomad.activities;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class SliderData {
    // image url is used to
    // store the url of image
    private String imgUrl;
    private int imgUrl2;

    // Constructor method.
    public SliderData(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public SliderData(int imgUrl) {
        this.imgUrl2 = imgUrl;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }
    public int getImgUrl2() {
        return imgUrl2;
    }
    /*public Drawable getDrawable() {
        int imageResource = getResources().getIdentifier(imgUrl, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        return res;
    }*/

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
