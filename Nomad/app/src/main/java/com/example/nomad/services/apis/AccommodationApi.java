package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;

import org.osmdroid.library.BuildConfig;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface AccommodationApi {


    String BASE_URL = "http://192.168.1.8:8080/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("accommodations")
    public Call<AccommodationDTO> create(@Body AccommodationDTO accommodationDTO, @Header("Authorization") String authHeader);

    @POST("accommodations/unavailable/{id}")
    public Call<String> makeUnavailable(@Path("id") Long id, @Body DateRange dateRange, @Header("Authorization") String authHeader);
    @POST("accommodations/price/{id}")
    public Call<String> setPrice(@Path("id") Long id, @Body DateRange dateRange, @Header("Authorization") String authHeader);
    @POST("accommodation-ratings")
    public Call<AccommodationRatingCreationDTO> addComment(@Body AccommodationRatingCreationDTO accommodationRatingCreationDTO, @Header("Authorization") String authHeader);
    @POST("comment-reports")
    public Call<AddCommentReportDTO> reportComment(@Body AddCommentReportDTO addCommentReportDTO, @Header("Authorization") String authHeader);
    @GET("accommodation-ratings/for-accommodation/{id}")
    public Call<Collection<AccommodationRatingDTO>> getComments(@Path("id") Long id, @Header("Authorization") String authHeader);

    @GET("accommodations/unverified")
    public Call<ArrayList<AccommodationDTO>> getUnverified(@Header("Authorization") String authHeader);

    @PUT("accommodations/verify/{id}")
    public Call<AccommodationDTO> verifyAccommodation(@Path("id") Long id, @Header("Authorization") String authHeader);

    @DELETE("accommodations/{id}")
    public Call<AccommodationDTO> deleteAccommodation(@Path("id") Long id, @Header("Authorization") String authHeader);
}