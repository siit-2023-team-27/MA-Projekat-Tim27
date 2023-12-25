package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface AccommodationApi {


    String BASE_URL = "http://192.168.1.144:8080/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("./accommodations")
    public Call<AccommodationDTO> create(@Body AccommodationDTO accommodationDTO, @Header("Authorization") String authHeader);

    @POST("./unavailable/id/")
    public Call<String> makeUnavailable(@Path("id") Long id, @Body DateRange dateRange, @Header("Authorization") String authHeader);

}