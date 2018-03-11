package com.countryfacts.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 */

public class CountryInfo implements Parcelable {
    public String title;
    public String description;
    public String imageHref;


    public CountryInfo() {

    }

    public boolean isDataEmpty() {
        return TextUtils.isEmpty(title) && TextUtils.isEmpty(description)
                && TextUtils.isEmpty(imageHref);
    }

    protected CountryInfo(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageHref = in.readString();
    }

    public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
        @Override
        public CountryInfo createFromParcel(Parcel in) {
            return new CountryInfo(in);
        }

        @Override
        public CountryInfo[] newArray(int size) {
            return new CountryInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageHref);
    }
}
