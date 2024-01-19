package com.example.nomad.fragments.accommodations;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.services.AccommodationClient;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.NotificationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<AccommodationRatingDTO>> comments = new MutableLiveData<>();
    public LiveData<ArrayList<AccommodationRatingDTO>> getElements() {
        return comments;
    }
    public void getComments(Long accommodationId){
        Call<Collection<AccommodationRatingDTO>> call = AccommodationClient.getInstance().getMyApi().getComments(accommodationId, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<AccommodationRatingDTO>>() {
            @Override
            public void onResponse(Call<Collection<AccommodationRatingDTO>> call, Response<Collection<AccommodationRatingDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                comments.setValue(new ArrayList<>(response.body().stream().collect(Collectors.toList())));
                Log.d("onResponse: ", comments.toString());

            }

            @Override
            public void onFailure(Call<Collection<AccommodationRatingDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void addComment(AccommodationRatingCreationDTO accommodationRatingCreationDTO) {
        Call<AccommodationRatingCreationDTO> call = AccommodationClient.getInstance().getMyApi().addComment(accommodationRatingCreationDTO , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationRatingCreationDTO>() {
            @Override
            public void onResponse(Call<AccommodationRatingCreationDTO> call, Response<AccommodationRatingCreationDTO> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                getComments(accommodationRatingCreationDTO.getRatedId());
            }

            @Override
            public void onFailure(Call<AccommodationRatingCreationDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }

}
