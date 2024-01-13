package com.example.nomad.services.apis;

<<<<<<< HEAD
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
=======
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.UserReportDto;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    String BASE_URL = "http://192.168.1.8:8080/api/";

    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @GET("users")
    public Call<ArrayList<UserDTO>> getAllUsers(@Header("Authorization") String authHeader);

    @PUT("users/suspend/{id}")
    public Call<UserDTO> suspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("users/un-suspend/{id}")
    public Call<UserDTO> unSuspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);

    @POST("host-ratings")
    public Call<RatingCreationDTO> create(@Body RatingCreationDTO accommodationDTO, @Header("Authorization") String authHeader);
    @POST("user_reports")
    public Call<UserReportDto> report(@Body UserReportDto userReportDto, @Header("Authorization") String authHeader);
    @GET("host-ratings/host/{userId}")
    Call<Collection<DTO.RatingDTO>> getRatings(@Path("userId") Long userId, @Header("Authorization") String s);

}

