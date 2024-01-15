package com.example.nomad.services;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRating;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.model.AccommodationRatingDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.AddCommentReportDTO;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.enums.ReportStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccomodationsService {
    private MutableLiveData<List<AccommodationDTO>> accommodations = new MutableLiveData<>();
    private MutableLiveData<List<Date>> takenDates = new MutableLiveData<>();

    public void loadAccommodations() {
        Call<ArrayList<AccommodationDTO>> call = AccommodationClient.getInstance().getMyApi().getAccommodations("Bearer" + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    ArrayList<AccommodationDTO> objects = response.body();
                    //accommodations = objects;
                    accommodations.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    // Handle unsuccessful response
                    // You may want to check response.errorBody() for more details
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
                // Handle failure (network error, etc.)
            }
        });
    }

    public LiveData<List<AccommodationDTO>> getAccommodations() {
        return accommodations;
    }
    public LiveData<List<Date>> getTakenDates() {
        return takenDates;
    }
    public void loadTakenDates(long accommodationId) {
        Call<List<Long>> call = AccommodationClient.getInstance().getMyApi().getTakenDates(accommodationId);

        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    List<Long> objects = response.body();
                    List<Date> dates = new ArrayList<>();
                    for (Long date: objects) {
                        dates.add(new Date(date));
                    }
                    takenDates.setValue(dates);
                    Log.d("onResponse takenDates: ", (new Date(objects.get(1)).toString()));
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    String errorMessage = null;

                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitError", errorMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                // Handle failure (network error, etc.)
                Log.e("RetrofitError", "Request failed: " + t.getMessage());

            }
        });
    }

    private ArrayList<AccommodationRatingDTO> comments = new ArrayList<>();

    private MutableLiveData<List<AccommodationDTO>> unverified = new MutableLiveData<>();

    private MutableLiveData<List<AccommodationDTO>> hostAccommodations = new MutableLiveData<>();


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

    public void getUnverifiedAccommodations() {
        Call<ArrayList<AccommodationDTO>> call = AccommodationClient.getInstance().getMyApi().getUnverified("Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    ArrayList<AccommodationDTO> objects = response.body();
                    //accommodations = objects;
                    unverified.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "NIJE USPEO");
                    // Handle unsuccessful response
                    // You may want to check response.errorBody() for more details
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("onResponse: ", t.getMessage());
            }
        });
    }

    public void verifyAccommodation(Long id) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().verifyAccommodation(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                Log.d("onResponse: ", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("Failure: ", t.getMessage());
            }
        });
    }

    public void deleteAccommodation(Long id) {
        Call<AccommodationDTO> call = AccommodationClient.getInstance().getMyApi().deleteAccommodation(id, "Bearer " + AuthService.token.toString());
        Log.d("TOKEN", AuthService.token.toString());
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                Log.d("onResponse: ", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("Failure: ", t.getMessage());
            }
        });
    }

    public void getAccommodationsForHost(Long id) {
        Call<ArrayList<AccommodationDTO>> call = AccommodationClient.getInstance().getMyApi().getAccommodationsForHost(id, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationDTO>> call, Response<ArrayList<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AccommodationDTO> objects = response.body();
                    hostAccommodations.setValue(objects);
                    Log.d("onResponse: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    Log.d("onResponse: ", "NIJE USPEO");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationDTO>> call, Throwable t) {
                Log.d("Failure: ", t.getMessage());
            }
        });
    }

    public MutableLiveData<List<AccommodationDTO>> getUnverified() {
        return unverified;
    }

    public ArrayList<AccommodationRatingDTO> getComments() {
        return comments;
    }

    public void setComments(ArrayList<AccommodationRatingDTO> comments) {
        this.comments = comments;
    }

    public MutableLiveData<List<AccommodationDTO>> getHostAccommodations() {
        return hostAccommodations;
    }
}
