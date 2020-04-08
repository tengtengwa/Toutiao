package com.example.toutiao.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.toutiao.R;

public class ImageUtil {
    @BindingAdapter({"loadImage"})
    public static void GlideLoadImage(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .error(R.drawable.ic_load_error)
                    .into(imageView);
        }
    }
}
