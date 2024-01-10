package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.RatingCreationDTO;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {


    String BASE_URL = "http://192.168.1.144:8080/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("host-ratings")
    public Call<RatingCreationDTO> create(@Body RatingCreationDTO accommodationDTO, @Header("Authorization") String authHeader);

    @GET("host/{userId}")
    Call<Collection<DTO.RatingDTO>> getRatings(@Path("userId") Long userId, String s);
}