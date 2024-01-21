package com.example.nomad.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.CommentReportDetailsDTO;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.UserReportDetailsDTO;
import com.example.nomad.dto.UserReportDto;
import com.example.nomad.services.AccommodationClient;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ICanRateListener;
import com.example.nomad.services.UserClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentReportViewModel {
    private MutableLiveData<ArrayList<CommentReportDetailsDTO>> reports = new MutableLiveData<>();
    public LiveData<ArrayList<CommentReportDetailsDTO>> getElements() {
        return reports;
    }

    public void getUserReports(){
        Call<Collection<CommentReportDetailsDTO>> call = AccommodationClient.getInstance().getMyApi().getCommentReports("Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<CommentReportDetailsDTO>>() {
            @Override
            public void onResponse(Call<Collection<CommentReportDetailsDTO>> call, Response<Collection<CommentReportDetailsDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                reports.setValue(new ArrayList<>(response.body().stream().collect(Collectors.toList())));

            }

            @Override
            public void onFailure(Call<Collection<CommentReportDetailsDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void archiveCommentReport(Long id){
        Call<CommentReportDetailsDTO> call = AccommodationClient.getInstance().getMyApi().archiveCommentReport(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<CommentReportDetailsDTO>() {
            @Override
            public void onResponse(Call<CommentReportDetailsDTO> call, Response<CommentReportDetailsDTO> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                getUserReports();

            }

            @Override
            public void onFailure(Call<CommentReportDetailsDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void acceptCommentReport(Long id){
        Call<CommentReportDetailsDTO> call = AccommodationClient.getInstance().getMyApi().acceptCommentReport(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<CommentReportDetailsDTO>() {
            @Override
            public void onResponse(Call<CommentReportDetailsDTO> call, Response<CommentReportDetailsDTO> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                getUserReports();

            }

            @Override
            public void onFailure(Call<CommentReportDetailsDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }


}
