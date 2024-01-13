package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationService {
    private MutableLiveData<Boolean> reservationSuccessful = new MutableLiveData<>();
    public LiveData<Boolean> getResponse() {
        return reservationSuccessful;
    }
    public void create(ReservationDTO reservationDTO) {
        Call<ReservationResponseDTO> call = ReservationClient.getInstance().getMyApi().createReservationRequest(reservationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ReservationResponseDTO>() {
            @Override
            public void onResponse(Call<ReservationResponseDTO> call, Response<ReservationResponseDTO> response) {
                ReservationResponseDTO accommodation = response.body();
                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.toString());
                Log.d("onResponse: ", accommodation.toString());
                reservationSuccessful.setValue(true);
            }

            @Override
            public void onFailure(Call<ReservationResponseDTO> call, Throwable t) {
                Log.d("onResponse: ", "ERROR");
                reservationSuccessful.setValue(false);

            }

        });
    }
}
