package com.example.nomad.services.apis;

import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageApi {


    String BASE_URL = "http://192.168.1.144:8080/images/";

    @Multipart
    @POST("upload")
    public Call<List<String>> upload(@Part MultipartBody.Part images, @Header("Authorization") String authHeader);



}