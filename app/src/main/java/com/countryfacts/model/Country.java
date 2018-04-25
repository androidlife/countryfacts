package com.countryfacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 */

public class Country implements Parcelable {
    public String name;
    public List<CountryInfo> countryInfos = null;

    public Country() {

    }

    protected Country(Parcel in) {
        name = in.readString();
        countryInfos = in.createTypedArrayList(CountryInfo.CREATOR);
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public boolean isValid() {
        return name != null && name.length() > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(countryInfos);
    }
}
