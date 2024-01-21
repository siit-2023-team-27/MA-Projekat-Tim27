package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.UserRatingsFragment;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.ReservationService;

import java.util.ArrayList;

public class HostReservationListAdapter extends ArrayAdapter<ReservationResponseDTO> {
    private ArrayList<ReservationResponseDTO> reservations;
    private FragmentActivity activity;
    ReservationService reservationService = new ReservationService();
    private boolean isRequestsPage;

    public HostReservationListAdapter(Context context, ArrayList<ReservationResponseDTO> accommodations, FragmentActivity activity){
        super(context, R.layout.host_reservations_card, accommodations);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.host_reservations_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView name = convertView.findViewById(R.id.guest_reservation_name);
        TextView status = convertView.findViewById(R.id.guest_reservation_status);
        TextView dateRange = convertView.findViewById(R.id.guest_reservation_date_range);
        TextView userCanceled = convertView.findViewById(R.id.guest_reservation_user_canceled);
        Button accept = convertView.findViewById(R.id.acceptREservation);
        Button reject = convertView.findViewById(R.id.rejectREservation);

        if(reservation != null){
            name.setText(reservation.getAccommodationDetails().getName());
            status.setText(reservation.getStatus());
            dateRange.setText(Helper.longToDateStirng(reservation.getStartDate())+"-"+Helper.longToDateStirng(reservation.getFinishDate()));
            userCanceled.setText("User canceled "+reservation.getUserDetails().getCancellationNumber()+" times");
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: id: " +
                        reservation.getId());
                Toast.makeText(getContext(), "Clicked: id: " + reservation.getId(), Toast.LENGTH_SHORT).show();
            });
            this.handleAccept(accept, reservation);
            this.handleReject(reject, reservation);
            if(reservation.getStatus().equals("ACCEPTED")){
                LinearLayout layout = convertView.findViewById(R.id.buttons);

                Button favorite2 = new Button(activity);
                favorite2.setText("Report guest");
                layout.addView(favorite2);
                favorite2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserRatingsFragment userRatingsFragment = new UserRatingsFragment(reservation.getUser());
                        FragmentTransition.to(userRatingsFragment, activity, true, R.id.base_accommodations);
                    }
                });
            }
        }

        return convertView;
    }

    private void handleAccept(Button delete, ReservationResponseDTO reservation){
        delete.setOnClickListener(v -> {
            if(reservation.getStatus().equals("PENDING")){
                reservationService.acceptReservation(reservation.getId());
                reservationService.getAcceptResponse().observe(activity, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean objects) {
                        // Update your UI or perform any actions when LiveData changes
                        Boolean responseSuccessful =  objects;
                        Log.i("Response reservation", "Selected: " + responseSuccessful.toString());

                        if(responseSuccessful){
                            Toast.makeText(getContext(), "Reservation accepted successfully", Toast.LENGTH_SHORT).show();
                            reservationService.setRefreshHostReservations(true);
                        }else{
                            Toast.makeText(getContext(), "Reservation not accepted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(), "Not possible to accept", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void handleReject(Button delete, ReservationResponseDTO reservation){
        delete.setOnClickListener(v -> {
            if(reservation.getStatus().equals("PENDING")){
                reservationService.rejectReservation(reservation.getId());
                reservationService.getRejectResponse().observe(activity, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean objects) {
                        // Update your UI or perform any actions when LiveData changes
                        Boolean responseSuccessful =  objects;
                        Log.i("Response reservation", "Selected: " + responseSuccessful.toString());

                        if(responseSuccessful){
                            Toast.makeText(getContext(), "Reservation rejected successfully", Toast.LENGTH_SHORT).show();
                            reservationService.setRefreshHostReservations(true);
                        }else{
                            Toast.makeText(getContext(), "Reservation not rejected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getContext(), "Not possible to reject", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
