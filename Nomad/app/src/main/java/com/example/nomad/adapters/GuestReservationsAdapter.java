package com.example.nomad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.services.AccommodationService;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.ReservationClient;

import java.util.ArrayList;

public class GuestReservationsAdapter extends ArrayAdapter<ReservationDTO> {
    ArrayList<ReservationDTO> reservations;

    private FragmentActivity activity;

    private AccomodationsService accommodationService = new AccomodationsService();

    public GuestReservationsAdapter(@NonNull Context context, ArrayList<ReservationDTO> reservations, FragmentActivity activity) {
        super(context, R.layout.guest_reservation_card, reservations);
        this.reservations = reservations;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Nullable
    @Override
    public ReservationDTO getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationDTO reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_reservation_card,
                    parent, false);
        }
        
        ImageView imageView = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.accommodation_name);
        TextView address = convertView.findViewById(R.id.accommodation_address);
        TextView status = convertView.findViewById(R.id.reservation_status);
        TextView dates = convertView.findViewById(R.id.reservation_dates);


        if (reservation != null) {
            accommodationService.getOneAccommodation(reservation.getAccommodation());
            accommodationService.getAccommodation().observe(activity, new Observer<AccommodationDTO>() {
                @Override
                public void onChanged(AccommodationDTO accommodationDTO) {
                    title.setText(accommodationDTO.getName());
                    address.setText(accommodationDTO.getAddress());
                    status.setText(reservation.getStatus().toString());
                    dates.setText(reservation.getStartDate().toString() + ", " + reservation.getFinishDate().toString());
                }
            });
        }

        return convertView;
    }
}
