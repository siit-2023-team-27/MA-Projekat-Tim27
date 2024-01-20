package com.example.nomad.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.helper.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationService {
    private MutableLiveData<Collection<ReservationResponseDTO>> reservations = new MutableLiveData<>();
    public LiveData<Collection<ReservationResponseDTO>> getReservations() {
        return reservations;
    }
    private MutableLiveData<Boolean> reservationSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletionSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> cancelSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> acceptSuccessful = new MutableLiveData<>();
    private MutableLiveData<Boolean> rejectSuccessful = new MutableLiveData<>();
    private static MutableLiveData<Boolean> refresh = new MutableLiveData<>();
    private static MutableLiveData<Boolean> refreshReservations = new MutableLiveData<>();
    private static MutableLiveData<Boolean> refreshHostReservations = new MutableLiveData<>();
    public LiveData<Boolean> getResponse() {
        return reservationSuccessful;
    }
    public LiveData<Boolean> getAcceptResponse() {
        return acceptSuccessful;
    }
    public LiveData<Boolean> getRejectResponse() {
        return rejectSuccessful;
    }
    public LiveData<Boolean> getRefresh() {
        return refresh;
    }
    public LiveData<Boolean> getRefreshReservations() {
        return refreshReservations;
    }
    public LiveData<Boolean> getRefreshHostReservations() {
        return refreshHostReservations;
    }
    public LiveData<Boolean> getDeleteResponse() {
        return deletionSuccessful;
    }
    public LiveData<Boolean> getCancelResponse() {
        return cancelSuccessful;
    }
    public void setRefresh(Boolean bo) {
         refresh.setValue(bo);
    }
    public void setRefreshHostReservations(Boolean bo) {
        refreshHostReservations.setValue(bo);
    }

    public void setRefreshReservations(Boolean bo) {
        refreshReservations.setValue(bo);
    }
    private MutableLiveData<Collection<ReservationResponseDTO>> searchedreservations = new MutableLiveData<>();
    public LiveData<Collection<ReservationResponseDTO>> getSearchedReservations() {
        return searchedreservations;
    }
    public void getSearchedAndFIltered(Long id, String name, Date from, Date to, String type, Boolean isHost) {
        Call<Collection<ReservationResponseDTO>> call;
        if(isHost){
            call = ReservationClient.getInstance().getMyApi().getSearchedANdFIlteredHost(id,name, Helper.dateToString(from), Helper.dateToString(to),
                    type, "Bearer " + AuthService.token.toString());
        }else{
            call = ReservationClient.getInstance().getMyApi().getSearchedANdFIlteredGuest(id,name, Helper.dateToString(from), Helper.dateToString(to),
                    type, "Bearer " + AuthService.token.toString());
        }
        call.enqueue(new Callback<Collection<ReservationResponseDTO>>() {
            @Override
            public void onResponse(Call<Collection<ReservationResponseDTO>> call, Response<Collection<ReservationResponseDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Collection<ReservationResponseDTO> objects = response.body();
                    //accommodations = objects;
                    searchedreservations.setValue(objects);
                    Log.d("onResponseSearched: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;

                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("searchedError", errorMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Collection<ReservationResponseDTO>> call, Throwable t) {
                // Handle failure (network error, etc.)
                Log.e("searchedErrror", "Request failed: " + t.getMessage());

            }
        });
    }
    public void acceptReservation(Long id) {
        Call<Long> call = ReservationClient.getInstance().getMyApi().acceptReservation(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Long objects = response.body();
                    //accommodations = objects;
                    acceptSuccessful.setValue(true);
                    Log.d("onResponseGuestAcception: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestResAcception", errorMessage);
                            acceptSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("RetrofitErrorGuestResAcception: ", "ERROR");
                cancelSuccessful.setValue(false);

            }
        });
    }
    public void rejectReservation(Long id) {
        Call<Long> call = ReservationClient.getInstance().getMyApi().rejectReservation(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Long objects = response.body();
                    //accommodations = objects;
                    rejectSuccessful.setValue(true);
                    Log.d("onResponseGuestReject: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestResReject", errorMessage);
                            rejectSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("RetrofitErrorGuestResReject: ", "ERROR");
                cancelSuccessful.setValue(false);

            }
        });
    }
    public void cancelReservation(Long id) {
        Call<Long> call = ReservationClient.getInstance().getMyApi().cancelReservation(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Long objects = response.body();
                    //accommodations = objects;
                    cancelSuccessful.setValue(true);
                    Log.d("onResponseGuestCancellation: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestResCancellation", errorMessage);
                            cancelSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("RetrofitErrorGuestResCancellation: ", "ERROR");
                cancelSuccessful.setValue(false);

            }
        });
    }
    public void deleteReservation(Long id) {
        Call<Long> call = ReservationClient.getInstance().getMyApi().deleteReservation(id,"Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Long objects = response.body();
                    //accommodations = objects;
                    deletionSuccessful.setValue(true);
                    Log.d("onResponseGuestDeletion: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGuestResDeletion", errorMessage);
                            deletionSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.d("RetrofitErrorGuestResDeletion: ", "ERROR");
                deletionSuccessful.setValue(false);

            }
        });
    }
    public void loadReservations(Long id, Boolean isForHost) {
        Log.i("HOST", String.valueOf(id) + isForHost);

        Call<Collection<ReservationResponseDTO>> call ;
        if(isForHost){
            call = ReservationClient.getInstance().getMyApi().getReservationsForHost(id,"Bearer " + AuthService.token.toString());
        }else{
            call = ReservationClient.getInstance().getMyApi().getReservationsForGuest(id,"Bearer " + AuthService.token.toString());
        }
        call.enqueue(new Callback<Collection<ReservationResponseDTO>>() {
            @Override
            public void onResponse(Call<Collection<ReservationResponseDTO>> call, Response<Collection<ReservationResponseDTO>> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response
                    Collection<ReservationResponseDTO> objects = response.body();
                    if(objects == null){
                        reservations.setValue(new ArrayList<>());

                    }else{
                        reservations.setValue(objects);
                    }
                    //accommodations = objects;
                    Log.d("onResponseGetReservations: ", "USPEO");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                } else {
                    // Handle unsuccessful response
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitErrorGetReservations", String.valueOf(response.code()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                }
            }

            @Override
            public void onFailure(Call<Collection<ReservationResponseDTO>> call, Throwable t) {
                Log.d("RetrofitErrorGetReservations: ", "ERROR");
            }
        });
    }
    public void create(ReservationDTO reservationDTO) {
        Call<ReservationResponseDTO> call = ReservationClient.getInstance().getMyApi().createReservationRequest(reservationDTO, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ReservationResponseDTO>() {
            @Override
            public void onResponse(Call<ReservationResponseDTO> call, Response<ReservationResponseDTO> response) {
                if (response.isSuccessful()) {
                    ReservationResponseDTO accommodation = response.body();
                    Log.d("onResponse: ", String.valueOf(response.code()));
                    Log.d("onResponse: ", response.toString());
                    Log.d("onResponse: ", accommodation.toString());
                    reservationSuccessful.setValue(true);
                }else{
                    String errorMessage = null;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            // Print or log the error message
                            Log.e("RetrofitError", errorMessage);
                            reservationSuccessful.setValue(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReservationResponseDTO> call, Throwable t) {
                Log.d("onResponse: ", "ERROR");
                reservationSuccessful.setValue(false);

            }

        });
    }
}
