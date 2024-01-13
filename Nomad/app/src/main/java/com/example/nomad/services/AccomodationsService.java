package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRating;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.model.AccommodationRatingDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsService {
    private MutableLiveData<List<AccommodationDTO>> accommodations = new MutableLiveData<>();
    private MutableLiveData<List<Date>> takenDates = new MutableLiveData<>();

    public void loadAccommodations() {
        Call<ArrayList<AccommodationDTO>> call = AccommodationClient.getInstance().getMyApi().getAccommodations("Bearer" + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    ArrayList<AccommodationDTO> objects = response.body();
                    //accommodations = objects;
                    accommodations.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    // Handle unsuccessful response
                    // You may want to check response.errorBody() for more details
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
                // Handle failure (network error, etc.)
            }
        });
    }

    public LiveData<List<AccommodationDTO>> getAccommodations() {
        return accommodations;
    }
    public LiveData<List<Date>> getTakenDates() {
        return takenDates;
    }
    public void loadTakenDates(long accommodationId) {
        Call<List<Long>> call = AccommodationClient.getInstance().getMyApi().getTakenDates(accommodationId);

        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    List<Long> objects = response.body();
                    List<Date> dates = new ArrayList<>();
                    for (Long date: objects) {
                        dates.add(new Date(date));
                    }
                    takenDates.setValue(dates);
                    Log.d("onResponse takenDates: ", (new Date(objects.get(1)).toString()));
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    String errorMessage = null;

                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitError", errorMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                // Handle failure (network error, etc.)
                Log.e("RetrofitError", "Request failed: " + t.getMessage());

            }
        });
    }

    public void create(AccommodationDTO accommodationDTO) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().create(accommodationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                AccommodationDTO accommodation = response.body();
                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.toString());
                Log.d("onResponse: ", accommodation.toString());
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", "ERROR");
            }

        });
    }
    public void makeUnavailable(Long accommodationId, DateRange dateRange) {
        Call<String> call = AccommodationClient.getInstance().getMyApi().makeUnavailable(accommodationId,dateRange , "Bearer" + AuthService.token.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("onResponse: ", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onResponse: ", "ERROR");
            }

        });
    }
}
