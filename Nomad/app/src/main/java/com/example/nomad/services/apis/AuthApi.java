package com.example.nomad.services.apis;

import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;
import com.example.nomad.helper.Consts;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {


    String BASE_URL = Consts.BASEURL+"/auth/";
    @POST("signup")
    public Call<AppUser> register(@Body UserRegistrationDTO userDTO);

    @POST("login")
    public Call<UserTokenState> login(@Body LoginDTO loginDTO);

    @POST("reauthenticate")
    public Call<LoginDTO> reauthenticate(@Body LoginDTO loginDTO);

}