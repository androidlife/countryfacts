package com.countryfacts.screen.home.widgets;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

public class RowView extends ConstraintLayout {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.img_view)
    ImageView imageView;

    ImageLoadOptions imageLoadOptions;

    public RowView(Context context) {
        this(context, null);
    }

    public RowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.row_view_bg);
        int padding = getResources().getDimensionPixelSize(R.dimen.row_padding);
        setPadding(padding, padding, padding, padding);
        LayoutInflater.from(context).inflate(R.layout.row_view, this, true);
        ButterKnife.bind(this);

        int imgWidth = getResources().getDimensionPixelSize(R.dimen.img_width);
        int imgHeight = getResources().getDimensionPixelSize(R.dimen.img_height);

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
