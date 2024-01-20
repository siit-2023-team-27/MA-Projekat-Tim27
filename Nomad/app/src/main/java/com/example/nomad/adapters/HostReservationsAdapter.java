package com.example.nomad.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.ReservationDTO;

import java.util.ArrayList;

public class HostReservationsAdapter extends ArrayAdapter<ReservationDTO> {

    ArrayList<ReservationDTO> reservations;

    private FragmentActivity activity;

    public HostReservationsAdapter(@NonNull Context context, ArrayList<ReservationDTO> reservations, FragmentActivity activity) {
        super(context, R.layout.host_reservation_card, reservations);
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
}
