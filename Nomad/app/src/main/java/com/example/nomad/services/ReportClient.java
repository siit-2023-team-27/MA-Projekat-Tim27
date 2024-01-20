package com.example.nomad.services;

import com.example.nomad.services.apis.ReportApi;
import com.example.nomad.services.apis.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportClient {
    private static ReportClient instance = null;
    private ReportApi reportApi;

    private ReportClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(reportApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        reportApi = retrofit.create(ReportApi.class);
    }
    public static synchronized ReportClient getInstance() {
        if (instance == null) {
            instance = new ReportClient();
        }
        return instance;
    }

    public ReportApi getMyApi() {
        return reportApi;
    }

}
