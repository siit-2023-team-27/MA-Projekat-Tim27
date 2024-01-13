package com.example.nomad.services.apis;

import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    String BASE_URL = "http://192.168.1.8:8080/api/";

    @GET("users")
    public Call<ArrayList<UserDTO>> getAllUsers(@Header("Authorization") String authHeader);

    @PUT("users/suspend/{id}")
    public Call<UserDTO> suspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("users/un-suspend/{id}")
    public Call<UserDTO> unSuspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);


}
