package com.example.nomad.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.UserReportDetailsDTO;
import com.example.nomad.dto.UserReportDto;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ICanRateListener;
import com.example.nomad.services.UserClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReportsViewModel {
    private MutableLiveData<ArrayList<UserReportDetailsDTO>> reports = new MutableLiveData<>();
    public LiveData<ArrayList<UserReportDetailsDTO>> getElements() {
        return reports;
    }

    public void getUserReports(){
        Call<Collection<UserReportDetailsDTO>> call = UserClient.getInstance().getMyApi().getUserReportDetails("Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<UserReportDetailsDTO>>() {
            @Override
            public void onResponse(Call<Collection<UserReportDetailsDTO>> call, Response<Collection<UserReportDetailsDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                reports.setValue(new ArrayList<>(response.body().stream().collect(Collectors.toList())));
                Log.d("onResponse: ", reports.toString());

            }

            @Override
            public void onFailure(Call<Collection<UserReportDetailsDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void archiveUserReport(Long id){
        Call<UserReportDto> call = UserClient.getInstance().getMyApi().archiveReport(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<UserReportDto>() {
            @Override
            public void onResponse(Call<UserReportDto> call, Response<UserReportDto> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                getUserReports();

            }

            @Override
            public void onFailure(Call<UserReportDto> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void acceptUserReport(Long id){
        Call<UserReportDto> call = UserClient.getInstance().getMyApi().acceptReport(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<UserReportDto>() {
            @Override
            public void onResponse(Call<UserReportDto> call, Response<UserReportDto> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                getUserReports();

            }

            @Override
            public void onFailure(Call<UserReportDto> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }


}
