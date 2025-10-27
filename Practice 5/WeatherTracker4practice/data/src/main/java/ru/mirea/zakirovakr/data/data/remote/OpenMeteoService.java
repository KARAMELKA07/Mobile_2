package ru.mirea.zakirovakr.data.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mirea.zakirovakr.data.data.remote.GeoResponse;
import ru.mirea.zakirovakr.data.data.remote.ForecastResponse;

public interface OpenMeteoService {

    @GET("v1/search")
    Call<GeoResponse> geocode(
            @Query("name") String name,
            @Query("count") int count,
            @Query("language") String language
    );

    @GET("v1/forecast")
    Call<ForecastResponse> current(
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("current") String current,
            @Query("timezone") String timezone,
            @Query("wind_speed_unit") String windUnit,
            @Query("temperature_unit") String tempUnit
    );
}

