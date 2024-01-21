package com.example.nomad.fragments.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FavouritesFragment;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestReservationsBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestReservationsBaseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static GuestReservationsBaseFragment newInstance() {
        return new GuestReservationsBaseFragment();
    }

    public GuestReservationsBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_guest_reservations_base, container, false);
        FragmentTransition.to(GuestReservationsFragment.newInstance(), getActivity(), true, R.id.base_accommodations);

        return root;
    }
}