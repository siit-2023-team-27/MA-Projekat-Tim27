package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private MutableLiveData<List<UserDTO>> users = new MutableLiveData<>();

    public MutableLiveData<List<UserDTO>> getUsers () {return users;}

    public void getAllUsers() {
        Call<ArrayList<UserDTO>> call = UserClient.getInstance().getMyApi().getAllUsers("Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<UserDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDTO>> call, Response<ArrayList<UserDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<UserDTO> objects = response.body();
                    users.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "NIJE USPEO");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDTO>> call, Throwable t) {
                Log.d("onResponse: ", t.getMessage());
            }
        });
    }

    public void suspendUSer(Long id) {
        Call<UserDTO> call = UserClient.getInstance().getMyApi().suspendUser(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("onResponse: ", "SUCCESS");
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("onResponse: ", "FAIL");
            }
        });

    }

    public void unSuspendUSer(Long id) {
        Call<UserDTO> call = UserClient.getInstance().getMyApi().unSuspendUser(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("onResponse: ", "SUCCESS");
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("onResponse: ", "FAIL");
            }
        });
    }
}
