package com.example.nomad.services;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.ReportDTO;
import com.example.nomad.dto.UserDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportService {

    private MutableLiveData<List<ReportDTO>> monthlyReports = new MutableLiveData<>();
    private MutableLiveData<List<ReportDTO>> dateRangeReports = new MutableLiveData<>();

    public MutableLiveData<List<ReportDTO>> getMonthlyReports() {
        return monthlyReports;
    }

    public MutableLiveData<List<ReportDTO>> getDateRangeReports() {
        return dateRangeReports;
    }

    public void getReportForAccommodation(Long hostId, Long accommodationId, int year) {
        Call<ArrayList<ReportDTO>> call = ReportClient.getInstance().getMyApi().getReportForAccommodation(hostId, accommodationId, year, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<ReportDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReportDTO>> call, Response<ArrayList<ReportDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ReportDTO> objects = response.body();
                    monthlyReports.setValue(objects);
                    Log.d("onResponse: ", "SUCCESS");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReportDTO>> call, Throwable t) {
                Log.d("onFailure: ", t.getMessage());
            }
        });
    }

    public void getReportForDateRange (Long hostId, String fromDate, String toDate) {
        Call<ArrayList<ReportDTO>> call = ReportClient.getInstance().getMyApi().getReportForDateRange(hostId, fromDate, toDate, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<ReportDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReportDTO>> call, Response<ArrayList<ReportDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ReportDTO> objects = response.body();
                    dateRangeReports.setValue(objects);
                    Log.d("onResponse: ", "SUCCESS");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReportDTO>> call, Throwable t) {
                Log.d("onFailure: ", t.getMessage());
            }
        });
    }



}
