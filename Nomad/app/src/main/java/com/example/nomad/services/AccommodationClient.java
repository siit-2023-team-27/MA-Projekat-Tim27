package com.example.nomad.services;

import com.example.nomad.services.apis.AccommodationApi;
import com.example.nomad.services.apis.AuthApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccommodationClient {

    private static AccommodationClient instance = null;
    private AccommodationApi accommodationApi;

    private AccommodationClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AccommodationApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        accommodationApi = retrofit.create(AccommodationApi.class);
    }

    public static synchronized AccommodationClient getInstance() {
        if (instance == null) {
            instance = new AccommodationClient();
        }
        return instance;
    }

    public AccommodationApi getMyApi() {
        return accommodationApi;
    }
}