package com.example.nomad.services;

import android.util.Log;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.DateRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageService {
    public static ArrayList<String> paths = new ArrayList<String>();



    public void upload(MultipartBody.Part images) {
        Call<List<String>> call = ImageClient.getInstance().getMyApi().upload(images, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> images = response.body();

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.toString());
                Log.d("onResponse: ", images.toString());
                paths.add(images.get(0));
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", "ERROR");
                Log.d("onResponse: ", t.getMessage().toString());
                Log.d("onResponse: ", t.getCause().toString());
            }

        });
    }
}
