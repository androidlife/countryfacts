package com.countryfacts.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 */

public class Country {
    @SerializedName("title")
    public String name;
    @SerializedName("rows")
    public List<CountryInfo> countryInfos = null;
}
