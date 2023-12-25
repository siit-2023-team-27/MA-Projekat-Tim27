package com.example.nomad.services;

import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.nomad.activities.LoginActivity;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.dto.UserTokenState;
import com.example.nomad.enums.UserType;
import com.example.nomad.services.apis.AuthApi;
import com.example.nomad.utils.PropertyUtil;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {
    private static UserType role;
    public static JWT token;
    public static Long id;
    private LoginActivity activity;
    public AuthService(LoginActivity activity){
        this.activity = activity;
    }
    public AuthService(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public JWT getToken() {
        return token;
    }

    public void setToken(String tokenString) {
        this.token = new JWT(tokenString);
        role = UserType.valueOf((String)token.getClaim("role").asObject(Collection.class).toArray()[0]);
        id = token.getClaim("id").asObject(Long.class);
    }

    public void register(UserRegistrationDTO userRegistrationDTO) {
        Call<AppUser> call = RetrofitClient.getInstance().getMyApi().register(userRegistrationDTO);
        call.enqueue(new Callback<AppUser>() {
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                AppUser user = response.body();
                Log.d("onResponse: ", user.toString());
            }

            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", "ERROR");
            }

        });
    }
    public void login(LoginDTO loginDTO){
        Call<UserTokenState> call = RetrofitClient.getInstance().getMyApi().login(loginDTO);
        call.enqueue(new Callback<UserTokenState>() {
            @Override
            public void onResponse(Call<UserTokenState> call, Response<UserTokenState> response) {
//                AppUser user = response.body();
                if(response.code() == 401){
                    return;
                }
                Log.d("onResponse: ", response.toString());
                setToken(response.body().getAccessToken());
//                response.body().
                Log.d("OK", "onResponse: ");
                activity.loginSuccess();
            }

            @Override
            public void onFailure(Call<UserTokenState> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", "ERROR");
                activity.loginFail();
            }

        });
    }
}
