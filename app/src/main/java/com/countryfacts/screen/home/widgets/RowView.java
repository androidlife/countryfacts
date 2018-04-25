package com.countryfacts.screen.home.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.countryfacts.R;
import com.countryfacts.image.ImageLoadOptions;
import com.countryfacts.image.ImageLoader;
import com.countryfacts.model.CountryInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class RowView extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.img_view)
    ImageView imageView;

    ImageLoadOptions imageLoadOptions;


    public RowView(View view) {
        super(view);
        ButterKnife.bind(this, view);
        int imgWidth = view.getResources().getDimensionPixelSize(R.dimen.img_width);
        int imgHeight = view.getResources().getDimensionPixelSize(R.dimen.img_height);
        imageLoadOptions = new ImageLoadOptions.Builder().setErrorDrawable(R.drawable.ic_placeholder_error)
                .setPlaceHolder(R.drawable.ic_placeholder)
                .setResizeWidthHeight(imgWidth, imgHeight)
                .build();
    }

    public void setData(CountryInfo countryInfo) {
        tvTitle.setText(countryInfo.title);
        tvDescription.setText(countryInfo.description);
        if (URLUtil.isValidUrl(countryInfo.imageHref))
            ImageLoader.loadImage(countryInfo.imageHref, imageView, imageLoadOptions);
        else
            imageView.setImageResource(R.drawable.ic_placeholder_error);
    }
}
