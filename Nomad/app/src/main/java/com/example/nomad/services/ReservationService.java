package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationDateDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationService {

    private MutableLiveData<List<ReservationDTO>> guestReservations = new MutableLiveData<>();

    private MutableLiveData<List<ReservationDTO>> hostReservations = new MutableLiveData<>();

    public MutableLiveData<List<ReservationDTO>> getGuestReservations() {
        return guestReservations;
    }

    public MutableLiveData<List<ReservationDTO>> getHostReservations() {
        return hostReservations;
    }

    public void getReservationsForGuest(Long id) {
        Call<ArrayList<ReservationDTO>> call = ReservationClient.getInstance().getMyApi().getReservationsForGuest(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
           @Override
           public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
               if (response.isSuccessful()) {
                   ArrayList<ReservationDTO> objects = response.body();
                   guestReservations.setValue(objects);
                   Log.d("onResponse: ", "Success reading guest reservations, " + String.valueOf(objects.size()));
               } else {
                   Log.d("onResponse: ", "FAIL");
               }
           }

           @Override
           public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
               Log.d("FAILURE: ", t.getMessage());
           }
       });
    }

    public void getReservationsForHost(Long id) {
        Call<ArrayList<ReservationDTO>> call = ReservationClient.getInstance().getMyApi().getReservationsForHost(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ReservationDTO> objects = response.body();
                    hostReservations.setValue(objects);
                    Log.d("onResponse: ", "Success reading host reservations, " + String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("FAILURE: ", t.getMessage());
            }
        });
    }
}
