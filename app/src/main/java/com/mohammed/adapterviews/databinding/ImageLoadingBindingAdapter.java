package com.mohammed.adapterviews.databinding;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.mohammed.adapterviews.R;

public class ImageLoadingBindingAdapter {

    @BindingAdapter("setImage")
    public static void lodeImage(ImageView imageView, Uri imageUri) {

        try {
            imageView.setImageURI(imageUri);

        } catch (Exception e) {
            imageView.setImageResource(R.drawable.ic_photo);
            Log.d("ImageLoadingBinding", " Error: " + e);
        }

    }
}