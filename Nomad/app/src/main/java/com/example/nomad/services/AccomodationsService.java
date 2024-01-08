package com.example.nomad.services;

import android.util.Log;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.UserRegistrationDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsService {
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

}
