package com.example.nomad.fragments;

import android.media.Rating;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.RatingDTO;
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

public class UserRatingsViewModel {
    private MutableLiveData<ArrayList<RatingDTO>> comments = new MutableLiveData<>();
    public LiveData<ArrayList<RatingDTO>> getElements() {
        return comments;
    }
    private ArrayList<ICanRateListener> listeners = new ArrayList<>();
    public static boolean canRate = true;
    public void subscribeCanRate(ICanRateListener canRateListener){
        listeners.add(canRateListener);
    }
    private void emmitCanRate(){
        for (ICanRateListener canRateListener: listeners){
            canRateListener.canRateChanged();
        }
    }
    public void getComments(Long userId){
        Call<Collection<RatingDTO>> call = UserClient.getInstance().getMyApi().getRatings(userId, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<RatingDTO>>() {
            @Override
            public void onResponse(Call<Collection<RatingDTO>> call, Response<Collection<RatingDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                comments.setValue(new ArrayList<>(response.body().stream().collect(Collectors.toList())));
                Log.d("onResponse: ", comments.toString());

            }

            @Override
            public void onFailure(Call<Collection<RatingDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void canRate(Long hostId, Long userId) {
        Call<Boolean> call = UserClient.getInstance().getMyApi().canRate(hostId,userId, "Bearer " + AuthService.token.toString());
        Log.d("TOKEN", AuthService.token.toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("canRateResponse: ", String.valueOf(response.code()));
                if(response.code() == 401){
                    canRate = false;
                }else{
                    canRate = response.body();
                    Log.d("canRateResponse: ", String.valueOf(canRate));

                }
                emmitCanRate();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("canRateFailure: ", t.getMessage());
                Log.d("Failure: ", t.getMessage());
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
