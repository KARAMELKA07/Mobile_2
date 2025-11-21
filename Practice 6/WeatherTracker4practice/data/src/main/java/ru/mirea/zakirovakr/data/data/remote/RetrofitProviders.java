package ru.mirea.zakirovakr.data.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProviders {

    private static Retrofit geoRetrofit;
    private static Retrofit meteoRetrofit;

    private static final String GEO_BASE = "https://geocoding-api.open-meteo.com/";
    private static final String METEO_BASE = "https://api.open-meteo.com/";

    public static OpenMeteoService geoService() {
        if (geoRetrofit == null) {
            geoRetrofit = new Retrofit.Builder()
                    .baseUrl(GEO_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return geoRetrofit.create(OpenMeteoService.class);
    }

    public static OpenMeteoService meteoService() {
        if (meteoRetrofit == null) {
            meteoRetrofit = new Retrofit.Builder()
                    .baseUrl(METEO_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return meteoRetrofit.create(OpenMeteoService.class);
    }
}
