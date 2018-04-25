package com.countryfacts.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 */

public class CountryInfo implements Parcelable {
    public String title;
    public String description;
    public String imageHref;


    public CountryInfo() {

    }

    public boolean isDataEmpty() {
        return isEmpty(title) && isEmpty(description)
                && isEmpty(imageHref);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
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
