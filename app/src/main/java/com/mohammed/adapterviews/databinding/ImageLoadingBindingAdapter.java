package com.mohammed.adapterviews.databinding;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class ImageLoadingBindingAdapter {

    @BindingAdapter("setImage")
    public static void lodeImage(ImageView imageView, Uri imageUri){
        imageView.setImageURI(imageUri);
    }
}
