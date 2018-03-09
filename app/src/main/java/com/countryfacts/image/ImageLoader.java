package com.countryfacts.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 */

public class ImageLoader {
    public static RequestOptions requestOptions;

    public static void loadImage(String url, ImageView imageView,
                                 ImageLoadOptions imageLoadOptions) {
        applyRequestOptions(imageLoadOptions);
        Glide.with(imageView).load(url).apply(requestOptions).into(imageView);
    }

    // since there is only image items in recycler view or only one type of image item
    // to be loaded, we are creating single requestOptions
    // this can later be customized to handle multiple type of image load
    private static void applyRequestOptions(ImageLoadOptions imageLoadOptions) {
        if (requestOptions == null) {
            requestOptions = new RequestOptions().error(imageLoadOptions.errorDrawableId)
                    .placeholder(imageLoadOptions.placeHolderDrawableId)
                    .override(imageLoadOptions.resizeWidth, imageLoadOptions.resizeHeight)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
    }
}
