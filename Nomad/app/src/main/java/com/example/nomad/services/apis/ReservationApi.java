package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.helper.Consts;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

}
