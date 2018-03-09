package com.countryfacts.image;

import com.countryfacts.R;

/**
 */

public class ImageLoadOptions {
    public int errorDrawableId, placeHolderDrawableId;
    public int resizeWidth, resizeHeight;

    private ImageLoadOptions() {

    }

    public static class Builder {
        private int errorDrawableId = R.drawable.ic_placeholder_error;
        private int placeHolderDrawableId = R.drawable.ic_placeholder;
        private int resizeWidth = 144, resizeHeight = 144;

        public Builder() {

        }

        public Builder setErrorDrawable(int errorDrawableId) {
            this.errorDrawableId = errorDrawableId;
            return this;
        }

        public Builder setPlaceHolder(int placeHolderDrawableId) {
            this.placeHolderDrawableId = placeHolderDrawableId;
            return this;
        }

        public Builder setResizeWidthHeight(int resizeWidth, int resizeHeight) {
            this.resizeHeight = resizeHeight;
            this.resizeWidth = resizeWidth;
            return this;
        }

        public ImageLoadOptions build() {
            ImageLoadOptions imageLoadOptions = new ImageLoadOptions();
            imageLoadOptions.errorDrawableId = errorDrawableId;
            imageLoadOptions.placeHolderDrawableId = placeHolderDrawableId;
            imageLoadOptions.resizeWidth = resizeWidth;
            imageLoadOptions.resizeHeight = resizeHeight;
            return imageLoadOptions;
        }
    }
}
