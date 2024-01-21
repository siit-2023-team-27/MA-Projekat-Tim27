package com.example.nomad.services;

import com.example.nomad.services.apis.AccommodationApi;
import com.example.nomad.services.apis.ReservationApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationClient {
    private static ReservationClient instance = null;
    private ReservationApi reservationApi;

    private ReservationClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AccommodationApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        reservationApi = retrofit.create(ReservationApi.class);
    }

    public static synchronized ReservationClient getInstance() {
        if (instance == null) {
            instance = new ReservationClient();
        }
        return instance;
    }

    public ReservationApi getMyApi() {
        return reservationApi;
    }
}
