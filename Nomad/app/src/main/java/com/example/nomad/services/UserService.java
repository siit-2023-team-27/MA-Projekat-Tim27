package com.example.nomad.services;

import android.util.Log;

import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.UserReportDto;
import com.example.nomad.enums.ReportStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    public void reportUser(UserReportDto userReportDto) {
        userReportDto.setReportStatus(0);
        Call<UserReportDto> call = UserClient.getInstance().getMyApi().report(userReportDto , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<UserReportDto>() {
            @Override
            public void onResponse(Call<UserReportDto> call, Response<UserReportDto> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());

            }

            @Override
            public void onFailure(Call<UserReportDto> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
}
