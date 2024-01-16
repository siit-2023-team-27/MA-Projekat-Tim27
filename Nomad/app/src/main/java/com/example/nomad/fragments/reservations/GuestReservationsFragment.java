package com.example.nomad.fragments.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestReservationsFragment extends Fragment {
    public static ArrayList<ReservationResponseDTO> reservations;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ReservationService reservationService = new ReservationService();
    AuthService authService = new AuthService();

    public GuestReservationsFragment() {
        // Required empty public constructor
    }

    public static GuestReservationsFragment newInstance(String param1, String param2) {
        GuestReservationsFragment fragment = new GuestReservationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_guest_reservations, container, false);
        this.prepareProductList();
        reservationService.getRefresh().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean objects) {
                Log.i("Reshresh", "refr: ");
                prepareProductList();
                //testirati
            }
        });

        return root;
    }
    private void prepareProductList(){
        reservationService.loadReservations(authService.getId());
        reservationService.getReservations().observe(getActivity(), new Observer<Collection<ReservationResponseDTO>>() {
            @Override
            public void onChanged(Collection<ReservationResponseDTO> objects) {
                // Update your UI or perform any actions when LiveData changes

                // Now, you can convert the LiveData to a List if needed
                reservations = (ArrayList<ReservationResponseDTO>) objects;
                // Do something with the list
                FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations), getActivity(), false, R.id.scroll_guest_reservations);
            }
        });


    }
}