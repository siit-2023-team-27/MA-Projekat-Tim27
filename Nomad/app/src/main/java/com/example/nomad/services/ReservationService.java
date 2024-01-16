package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationService {
    private MutableLiveData<Collection<ReservationResponseDTO>> reservations = new MutableLiveData<>();
    public LiveData<Collection<ReservationResponseDTO>> getReservations() {
        return reservations;
    }
    private MutableLiveData<Boolean> reservationSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletionSuccessful = new MutableLiveData<>();

    public LiveData<Boolean> getResponse() {
        return reservationSuccessful;
    }
    private static MutableLiveData<Boolean> refresh = new MutableLiveData<>();

    public LiveData<Boolean> getRefresh() {
        return refresh;
    }

    public LiveData<Boolean> getDeleteResponse() {
        return deletionSuccessful;
    }
    public void setRefresh(Boolean bo) {
         refresh.setValue(bo);
    }

    public void deleteReservation(Long id) {
        Call<Long> call = ReservationClient.getInstance().getMyApi().deleteReservation(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Long objects = response.body();
                    //accommodations = objects;
                    deletionSuccessful.setValue(true);
                    Log.d("onResponseGuestDeletion: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestResDeletion", errorMessage);
                            deletionSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("RetrofitErrorGuestResDeletion: ", "ERROR");
                deletionSuccessful.setValue(false);

            }
        });
    }
    public void loadReservations(Long id) {
        Call<Collection<ReservationResponseDTO>> call = ReservationClient.getInstance().getMyApi().getReservationsForGuest(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<ReservationResponseDTO>>() {
            @Override
            public void onResponse(Call<Collection<ReservationResponseDTO>> call, Response<Collection<ReservationResponseDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Collection<ReservationResponseDTO> objects = response.body();
                    //accommodations = objects;
                    reservations.setValue(objects);
                    Log.d("onResponseGuestReservation: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestReservations", errorMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Collection<ReservationResponseDTO>> call, Throwable t) {
                Log.d("RetrofitErrorGuestReservations: ", "ERROR");
            }
        });
    }
    public void create(ReservationDTO reservationDTO) {
        Call<ReservationResponseDTO> call = ReservationClient.getInstance().getMyApi().createReservationRequest(reservationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ReservationResponseDTO>() {
            @Override
            public void onResponse(Call<ReservationResponseDTO> call, Response<ReservationResponseDTO> response) {
                if (response.isSuccessful()) {
                    ReservationResponseDTO accommodation = response.body();
                    Log.d("onResponse: ", String.valueOf(response.code()));
                    Log.d("onResponse: ", response.toString());
                    Log.d("onResponse: ", accommodation.toString());
                    reservationSuccessful.setValue(true);
                }else{
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitError", errorMessage);
                            reservationSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReservationResponseDTO> call, Throwable t) {
                Log.d("onResponse: ", "ERROR");
                reservationSuccessful.setValue(false);

            }

        });
    }
}
