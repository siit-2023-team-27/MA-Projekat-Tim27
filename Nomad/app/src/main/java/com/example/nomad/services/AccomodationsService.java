package com.example.nomad.services;

import android.util.Log;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.enums.ReportStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsService {
    private ArrayList<AccommodationRatingDTO> comments = new ArrayList<>();

    public ArrayList<AccommodationRatingDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<AccommodationRatingDTO> comments) {
        this.comments = comments;
    }

    public void create(AccommodationDTO accommodationDTO, ArrayList<DateRange> dateRanges, HashMap<DateRange, Double> prices) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().create(accommodationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.toString());
                AccommodationDTO accommodation = response.body();
                Log.d("onResponse: ", accommodation.toString());

                postUnavailable(accommodation.getId(), dateRanges);
                postPrices(accommodation.getId(), prices);

            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", "ERROR");
            }

        });
    }
    public void makeUnavailable(Long accommodationId, DateRange dateRange) {
        Call<String> call = AccommodationClient.getInstance().getMyApi().makeUnavailable(accommodationId,dateRange , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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
            }

            @Override
            public void onFailure(Call<AccommodationRatingCreationDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void getComments(Long accommodationId) {
        Call<Collection<AccommodationRatingDTO>> call = AccommodationClient.getInstance().getMyApi().getComments(accommodationId, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Collection<AccommodationRatingDTO>>() {
            @Override
            public void onResponse(Call<Collection<AccommodationRatingDTO>> call, Response<Collection<AccommodationRatingDTO>> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
                Log.d("onResponse: ", response.body().toString());
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());
                comments = new ArrayList<>(response.body().stream().collect(Collectors.toList()));
                Log.d("onResponse: ", comments.toString());

            }

            @Override
            public void onFailure(Call<Collection<AccommodationRatingDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void setPrice(Long accommodationId, DateRange range){
        Call<String> call = AccommodationClient.getInstance().getMyApi().setPrice(accommodationId,range , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }

    public void reportComment(Long id, String reason) {
        AddCommentReportDTO addCommentReportDTO = new AddCommentReportDTO();
        addCommentReportDTO.setReportedComment(id);
        addCommentReportDTO.setReason(reason);
        addCommentReportDTO.setReportingAppUser(AuthService.id);
        addCommentReportDTO.setReportStatus(ReportStatus.PENDING);
        Call<AddCommentReportDTO> call = AccommodationClient.getInstance().getMyApi().reportComment(addCommentReportDTO , "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AddCommentReportDTO>() {
            @Override
            public void onResponse(Call<AddCommentReportDTO> call, Response<AddCommentReportDTO> response) {

                Log.d("onResponse: ", String.valueOf(response.code()));
//                Log.d("onResponse: ", response.message());
//                Log.d("onResponse: ", response.body());

            }

            @Override
            public void onFailure(Call<AddCommentReportDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }

        });
    }
    public void postUnavailable(Long accommodationId, ArrayList<DateRange> dateRanges){
        for (DateRange range : dateRanges){
            makeUnavailable(accommodationId, range);
        }
    }
    public void postPrices(Long accommodationId, HashMap<DateRange, Double> prices){
        for (DateRange range : prices.keySet()){
            range.setPrice(prices.get(range));
            setPrice(accommodationId, range);
        }
    }

}
