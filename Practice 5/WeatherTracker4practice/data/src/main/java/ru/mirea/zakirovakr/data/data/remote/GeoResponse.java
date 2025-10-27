package ru.mirea.zakirovakr.data.data.remote;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GeoResponse {
    @SerializedName("results")
    public List<Result> results;

    public static class Result {
        @SerializedName("name")      public String name;
        @SerializedName("latitude")  public double latitude;
        @SerializedName("longitude") public double longitude;
        @SerializedName("country")   public String country;
        @SerializedName("admin1")    public String admin1;
    }
}
