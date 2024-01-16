package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.helper.Helper;

import java.util.ArrayList;
import java.util.Date;

public class GuestReservationListAdapter extends ArrayAdapter<ReservationResponseDTO> {
    private ArrayList<ReservationResponseDTO> reservations;
    private FragmentActivity activity;

    public GuestReservationListAdapter(Context context, ArrayList<ReservationResponseDTO> accommodations, FragmentActivity activity){
        super(context, R.layout.guest_reservations_card, accommodations);
        this.reservations = accommodations;
        this.activity = activity;
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

        if(reservation != null){
            //name.setText(reservation.getAccommodation());
            status.setText(reservation.getStatus());
            dateRange.setText(new Date(Long.valueOf(reservation.getStartDate()))+"-"+new Date(Long.valueOf(reservation.getFinishDate())));

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: id: " +
                        reservation.getId());
                Toast.makeText(getContext(), "Clicked: id: " + reservation.getId(), Toast.LENGTH_SHORT).show();


            });
        }

        return convertView;
    }
}