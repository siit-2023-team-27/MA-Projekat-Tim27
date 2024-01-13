package com.example.nomad.services;

import com.example.nomad.services.apis.AccommodationApi;
import com.example.nomad.services.apis.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {

    private static UserClient instance = null;
    private UserApi userApi;

    private UserClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        userApi = retrofit.create(UserApi.class);
    }

    public static synchronized UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }

    public UserApi getMyApi() {
        return userApi;
    }
}
