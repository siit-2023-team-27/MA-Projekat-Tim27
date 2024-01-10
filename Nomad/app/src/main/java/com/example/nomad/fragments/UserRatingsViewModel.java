package com.example.nomad.fragments;

import android.media.Rating;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.services.AccommodationClient;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.UserClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRatingsViewModel {
    private MutableLiveData<ArrayList<DTO.RatingDTO>> comments = new MutableLiveData<>();
    public LiveData<ArrayList<DTO.RatingDTO>> getElements() {
        return comments;
    }
    public void getComments(Long userId){
        Call<Collection<DTO.RatingDTO>> call = UserClient.getInstance().getMyApi().getRatings(userId, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<DTO.RatingDTO>>() {
            @Override
            public void onResponse(Call<Collection<DTO.RatingDTO>> call, Response<Collection<DTO.RatingDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                comments.setValue(new ArrayList<>(response.body().stream().collect(Collectors.toList())));
                Log.d("onResponse: ", comments.toString());

            }

            @Override
            public void onFailure(Call<Collection<DTO.RatingDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void addRating(RatingCreationDTO accommodationRatingCreationDTO) {
        Call<RatingCreationDTO> call = UserClient.getInstance().getMyApi().create(accommodationRatingCreationDTO , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<RatingCreationDTO>() {
            @Override
            public void onResponse(Call<RatingCreationDTO> call, Response<RatingCreationDTO> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                getComments(accommodationRatingCreationDTO.getRatedId());
            }

            @Override
            public void onFailure(Call<RatingCreationDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }

}
