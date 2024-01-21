package com.example.nomad.services.apis;

import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.RatingDTO;
import com.example.nomad.dto.UserDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.UserReportDetailsDTO;
import com.example.nomad.dto.UserReportDto;
import com.example.nomad.helper.Consts;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    String BASE_URL = Consts.BASEURL+"/api/";

    @GET("users")
    public Call<ArrayList<UserDTO>> getAllUsers(@Header("Authorization") String authHeader);
    @GET("user_reports/details")
    public Call<Collection<UserReportDetailsDTO>> getUserReportDetails(@Header("Authorization") String authHeader);
    @PUT("user_reports/archive/{id}")
    public Call<UserReportDto> archiveReport(@Path("id") Long reportId,@Header("Authorization") String authHeader);

    @PUT("user_reports/accept/{id}")
    public Call<UserReportDto> acceptReport(@Path("id") Long reportId,@Header("Authorization") String authHeader);


    @GET("users/{id}")
    public Call<UserDTO> getLoggedUser(@Path("id") Long id);

    @PUT("users/suspend/{id}")
    public Call<UserDTO> suspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("users/un-suspend/{id}")
    public Call<UserDTO> unSuspendUser(@Path("id") Long id, @Header("Authorization") String authHeader);

    @POST("host-ratings")
    public Call<RatingCreationDTO> create(@Body RatingCreationDTO accommodationDTO, @Header("Authorization") String authHeader);
    @GET("host-comments/can-rate/{hostId}/{userId}")
    public Call<Boolean> canRate(@Path("hostId") Long hostId, @Path("userId") Long userId,@Header("Authorization") String authHeader);
    @POST("user_reports")
    public Call<UserReportDto> report(@Body UserReportDto userReportDto, @Header("Authorization") String authHeader);
    @GET("host-ratings/host/{userId}")
    Call<Collection<RatingDTO>> getRatings(@Path("userId") Long userId, @Header("Authorization") String s);
    @GET("host-ratings/host-rating/{userId}/{hostId}")
    Call<Long> hasRating(@Path("userId") Long userId, @Path("hostId") Long hostId, @Header("Authorization") String s);
    @DELETE("host-ratings/{ratingId}")
    Call<RatingDTO> deleteRating(@Path("ratingId") Long ratingId, @Header("Authorization") String s);

}

