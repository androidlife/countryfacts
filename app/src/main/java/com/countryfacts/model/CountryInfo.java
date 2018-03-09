package com.countryfacts.model;

import com.google.gson.annotations.SerializedName;

/**
 */

public class CountryInfo {
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("imageHref")
    public String imageHref;
}
