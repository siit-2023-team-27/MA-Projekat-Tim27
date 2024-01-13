package com.example.nomad.services;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.UserRegistrationDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsService {

    private MutableLiveData<List<AccommodationDTO>> unverified = new MutableLiveData<>();
    public void create(AccommodationDTO accommodationDTO, ArrayList<DateRange> dateRanges, HashMap<DateRange, Double> prices) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().create(accommodationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                AccommodationDTO accommodation = response.body();
                postUnavailable(accommodation.getId(), dateRanges);
                postPrices(accommodation.getId(), prices);
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
        Call<String> call = AccommodationClient.getInstance().getMyApi().makeUnavailable(accommodationId,dateRange , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void setPrice(Long accommodationId, DateRange range){
        Call<String> call = AccommodationClient.getInstance().getMyApi().setPrice(accommodationId,range , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void postUnavailable(Long accommodationId, ArrayList<DateRange> dateRanges){
        for (DateRange range : dateRanges){
            makeUnavailable(accommodationId, range);
        }
    }
    public void postPrices(Long accommodationId, HashMap<DateRange, Double> prices){
        for (DateRange range : prices.keySet()){
            range.setPrice(prices.get(range));
            setPrice(accommodationId, range);
        }
    }

    public void getUnverifiedAccommodations() {
        Call<ArrayList<AccommodationDTO>> call = AccommodationClient.getInstance().getMyApi().getUnverified("Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    ArrayList<AccommodationDTO> objects = response.body();
                    //accommodations = objects;
                    unverified.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "NIJE USPEO");
                    // Handle unsuccessful response
                    // You may want to check response.errorBody() for more details
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }
        });
    }

    public void verifyAccommodation(Long id) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().verifyAccommodation(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                Log.d("onResponse: ", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("Failure: ", t.getMessage());
            }
        });
    }

    public void deleteAccommodation(Long id) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().deleteAccommodation(id, "Bearer " + AuthService.token.toString());
        Log.d("TOKEN", AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                Log.d("onResponse: ", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("Failure: ", t.getMessage());
            }
        });
    }

    public MutableLiveData<List<AccommodationDTO>> getUnverified() {
        return unverified;
    }
}
