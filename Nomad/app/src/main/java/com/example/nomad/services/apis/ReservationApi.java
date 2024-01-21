package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.helper.Consts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationApi {
    String BASE_URL = Consts.BASEURL+"/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("./reservations")
    public Call<ReservationResponseDTO> createReservationRequest(@Body ReservationDTO reservationDTO, @Header("Authorization") String authHeader);

    @GET("reservations/with-guest/{id}")
    public Call<Collection<ReservationResponseDTO>> getReservationsForGuest(@Path("id") Long id, @Header("Authorization") String authHeader);
    @GET("reservations/with-host/{id}")
    public Call<Collection<ReservationResponseDTO>> getReservationsForHost(@Path("id") Long id, @Header("Authorization") String authHeader);
    @DELETE("reservations/{id}")
    public Call<Long> deleteReservation(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("reservations/cancel/{id}")
    public Call<Long> cancelReservation(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("reservations/confirm/{id}")
    public Call<Long> acceptReservation(@Path("id") Long id, @Header("Authorization") String authHeader);

    @PUT("reservations/reject/{id}")
    public Call<Long> rejectReservation(@Path("id") Long id, @Header("Authorization") String authHeader);
    @GET("reservations/search-host/{id}")
    public Call<Collection<ReservationResponseDTO>> getSearchedANdFIlteredHost(@Path("id") Long id, @Query("name") String name,
                                                                               @Query("minimumDate")String minimumDate, @Query("maximumDate")String maximumDate,
                                                                               @Query("status") String status, @Header("Authorization") String authHeader);
    @GET("reservations/search-guest/{id}")
    public Call<Collection<ReservationResponseDTO>> getSearchedANdFIlteredGuest(@Path("id") Long id, @Query("name") String name,
                                                                                @Query("minimumDate")String minimumDate, @Query("maximumDate")String maximumDate,
                                                                                @Query("status") String status, @Header("Authorization") String authHeader);

}
