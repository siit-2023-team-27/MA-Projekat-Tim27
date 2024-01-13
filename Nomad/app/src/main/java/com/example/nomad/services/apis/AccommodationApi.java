package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRating;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;
import com.example.nomad.helper.Consts;
import com.example.nomad.model.AccommodationRatingDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface AccommodationApi {


    String BASE_URL = Consts.BASEURL+"/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("./accommodations")
    public Call<AccommodationDTO> create(@Body AccommodationDTO accommodationDTO, @Header("Authorization") String authHeader);

    @POST("./unavailable/id/")
    public Call<String> makeUnavailable(@Path("id") Long id, @Body DateRange dateRange, @Header("Authorization") String authHeader);


    @GET("accommodations/verified")
    public Call<ArrayList<AccommodationDTO>> getAccommodations(@Header("Authorization") String authHeader);

    @GET("accommodations/taken-dates/{accommodationId}")
    public Call<List<Long>> getTakenDates(@Path("accommodationId") Long accommodationId);

    @GET("accommodations/price/{accommodationId}/{date}")
    public Call<Double> getPrice(@Path("accommodationId") Long accommodationId, @Path("date") Date date, @Header("Authorization") String authHeader);

}