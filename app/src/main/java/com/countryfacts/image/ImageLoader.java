package com.countryfacts.image;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 */

public class ImageLoader {
    static RequestOptions requestOptions;

    public static void loadImage(String url, ImageView imageView,
                                 ImageLoadOptions imageLoadOptions) {
        applyRequestOptions(imageLoadOptions);
        //the reason we are casting to activity is that
        //we are targeting for Android 7 and above and using
        //no support fragment
        //glide checks whether the context is of fragment or not
        //to avoid this we are casting to activity directly
        Glide.with((Activity) imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    // since there is only image items in recycler view or only one type of image item
    // to be loaded, we are creating single requestOptions
    // this can later be customized to handle multiple type of image load
    private static void applyRequestOptions(ImageLoadOptions imageLoadOptions) {
        if (requestOptions == null) {
            requestOptions = new RequestOptions().error(imageLoadOptions.errorDrawableId)
                    .placeholder(imageLoadOptions.placeHolderDrawableId)
                    .override(imageLoadOptions.resizeWidth, imageLoadOptions.resizeHeight)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
    }
}
