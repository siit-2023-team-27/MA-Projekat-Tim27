package com.example.nomad.services;

import com.example.nomad.services.apis.AccommodationApi;
import com.example.nomad.services.apis.ImageApi;
import com.example.nomad.services.apis.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageClient {
    private static ImageClient instance = null;
    private ImageApi imageApi;

    private ImageClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ImageApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        imageApi = retrofit.create(ImageApi.class);
    }

    public static synchronized ImageClient getInstance() {
        if (instance == null) {
            instance = new ImageClient();
        }
        return instance;
    }

    public ImageApi getMyApi() {
        return imageApi;
    }
}
