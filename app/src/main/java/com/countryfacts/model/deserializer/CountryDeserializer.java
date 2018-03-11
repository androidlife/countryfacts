package com.countryfacts.model.deserializer;

import com.countryfacts.model.Country;
import com.countryfacts.model.CountryInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This is just added to remove list item having empty or null values
 * so that it won't be populated in the list
 */

public class CountryDeserializer implements JsonDeserializer<Country> {
    @Override
    public Country deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        Country country = new Country();
        final JsonElement mainTitleElement = jsonObject.get("title");
        if (!mainTitleElement.isJsonNull())
            country.name = jsonObject.get("title").getAsString();
        country.countryInfos = new ArrayList<>();
        final JsonArray jsonArray = jsonObject.getAsJsonArray("rows");

        if (jsonArray != null) {
            for (JsonElement jsonElement : jsonArray) {
                CountryInfo countryInfo = new CountryInfo();
                JsonObject countryObj = jsonElement.getAsJsonObject();
                final JsonElement titleElement = countryObj.get("title");
                final JsonElement descElement = countryObj.get("description");
                final JsonElement imgElement = countryObj.get("imageHref");
                if (!titleElement.isJsonNull())
                    countryInfo.title = titleElement.getAsString();
                if (!descElement.isJsonNull())
                    countryInfo.description = descElement.getAsString();
                if (!imgElement.isJsonNull())
                    countryInfo.imageHref = imgElement.getAsString();
                if (!countryInfo.isDataEmpty())
                    country.countryInfos.add(countryInfo);
            }
        }
        return country;
    }
}
