package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.fragments.accommodations.SearchedAccommodationListFragment;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.ReservationService;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class GuestReservationListAdapter extends ArrayAdapter<ReservationResponseDTO> {
    private ArrayList<ReservationResponseDTO> reservations;
    private FragmentActivity activity;
    ReservationService reservationService = new ReservationService();
    private boolean isRequestsPage;

    public GuestReservationListAdapter(Context context, ArrayList<ReservationResponseDTO> accommodations, FragmentActivity activity, boolean isRequestsPage){
        super(context, R.layout.guest_reservations_card, accommodations);
        this.reservations = accommodations;
        this.activity = activity;
        this.isRequestsPage = isRequestsPage;
    }
    @Override
    public int getCount() {
        return reservations.size();
    }

    @Nullable
    @Override
    public ReservationResponseDTO getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationResponseDTO reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_reservations_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView name = convertView.findViewById(R.id.guest_reservation_name);
        TextView status = convertView.findViewById(R.id.guest_reservation_status);
        TextView dateRange = convertView.findViewById(R.id.guest_reservation_date_range);
        Button delete = convertView.findViewById(R.id.deleteREservation);
        if(reservation != null){
            //name.setText(reservation.getAccommodation());
            status.setText(reservation.getStatus());
            dateRange.setText(Helper.longToDateStirng(reservation.getStartDate())+"-"+Helper.longToDateStirng(reservation.getFinishDate()));

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: id: " +
                        reservation.getId());
                Toast.makeText(getContext(), "Clicked: id: " + reservation.getId(), Toast.LENGTH_SHORT).show();
            });

            if(isRequestsPage){
                handleDelete(delete, reservation);
            }else{
                handleCancel(delete, reservation);
            }

        }

        return convertView;
    }

    private void handleCancel(Button delete, ReservationResponseDTO reservation){
        delete.setText("Cancel");
        delete.setOnClickListener(v -> {
            if(reservation.getStatus().equals("ACCEPTED")){
                reservationService.cancelReservation(reservation.getId());
                reservationService.getCancelResponse().observe(activity, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean objects) {
                        // Update your UI or perform any actions when LiveData changes
                        Boolean responseSuccessful =  objects;
                        Log.i("Response reservation", "Selected: " + responseSuccessful.toString());

                        if(responseSuccessful){
                            Toast.makeText(getContext(), "Reservation cancelled successfully", Toast.LENGTH_SHORT).show();
                            reservationService.setRefreshReservations(true);
                        }else{
                            Toast.makeText(getContext(), "Reservation not cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(), "Not possible to cancel", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void handleDelete(Button delete, ReservationResponseDTO reservation){

        delete.setOnClickListener(v -> {
            if(reservation.getStatus().equals("PENDING")){
                reservationService.deleteReservation(reservation.getId());
                reservationService.getDeleteResponse().observe(activity, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean objects) {
                        // Update your UI or perform any actions when LiveData changes
                        Boolean responseSuccessful =  objects;
                        Log.i("Response reservation", "Selected: " + responseSuccessful.toString());

                        if(responseSuccessful){
                            Toast.makeText(getContext(), "Reservation deleted successfully", Toast.LENGTH_SHORT).show();
                            reservationService.setRefresh(true);
                        }else{
                            Toast.makeText(getContext(), "Reservation not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(), "Not possible to delete", Toast.LENGTH_SHORT).show();

            }
        });
    }
}