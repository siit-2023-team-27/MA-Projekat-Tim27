package com.example.nomad.services;

import com.example.nomad.services.apis.AccommodationApi;
import com.example.nomad.services.apis.NotificationApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationClient {

    private static NotificationClient instance = null;
    private NotificationApi notificationApi;

    private NotificationClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AccommodationApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        notificationApi = retrofit.create(NotificationApi.class);
    }

    public static synchronized NotificationClient getInstance() {
        if (instance == null) {
            instance = new NotificationClient();
        }
        return instance;
    }

    public NotificationApi getMyApi() {
        return notificationApi;
    }
}
