package com.example.nomad.services.apis;

import com.example.nomad.dto.NotificationDTO;
import com.example.nomad.helper.Consts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NotificationApi {

    String BASE_URL = Consts.BASEURL+"/api/";

    @GET("notifications/user/{id}")
    public Call<ArrayList<NotificationDTO>> getNotificationsForUser(@Path("id") Long id, @Header("Authorization") String authHeader);

}
