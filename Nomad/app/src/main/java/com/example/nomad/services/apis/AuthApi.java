package com.example.nomad.services.apis;

import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {


    String BASE_URL = "http://192.168.1.8:8080/auth/";
    @POST("signup")
    public Call<AppUser> register(@Body UserRegistrationDTO userDTO);

    @POST("login")
    public Call<UserTokenState> login(@Body LoginDTO loginDTO);

}