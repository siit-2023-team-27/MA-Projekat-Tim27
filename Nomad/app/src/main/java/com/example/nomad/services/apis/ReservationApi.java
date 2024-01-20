package com.example.nomad.services.apis;

import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationDateDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ReservationApi {

    String BASE_URL = "http://192.168.1.8:8080/api/";

    @GET("reservations/with-guest/{id}")
    public Call<ArrayList<ReservationDTO>> getReservationsForGuest(@Path("id") Long id, @Header("Authorization") String authHeader);

    @GET("reservations/with-host/{id}")
    public Call<ArrayList<ReservationDTO>> getReservationsForHost(@Path("id") Long id, @Header("Authorization") String authHeader);
}
