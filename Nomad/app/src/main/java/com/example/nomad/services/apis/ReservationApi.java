package com.example.nomad.services.apis;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.helper.Consts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReservationApi {
    String BASE_URL = Consts.BASEURL+"/api/";
    @Headers(
            value = {
                    "Accept: application/json",
                    "Content-type:application/json"}
    )
    @POST("./reservations")
    public Call<ReservationResponseDTO> createReservationRequest(@Body ReservationDTO reservationDTO, @Header("Authorization") String authHeader);

}
