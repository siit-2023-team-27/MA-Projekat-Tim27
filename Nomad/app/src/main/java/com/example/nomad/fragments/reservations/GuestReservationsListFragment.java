package com.example.nomad.fragments.reservations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.adapters.AccommodationListAdapter;
import com.example.nomad.adapters.GuestReservationListAdapter;
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.databinding.FragmentGuestReservationsListBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestReservationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestReservationsListFragment extends ListFragment {

    private GuestReservationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<ReservationResponseDTO> reservations;
    private FragmentGuestReservationsListBinding binding;

    public static GuestReservationsListFragment newInstance(ArrayList<ReservationResponseDTO> products){
        GuestReservationsListFragment fragment = new GuestReservationsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentGuestReservationsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate guest reservations List Fragment");
        if (getArguments() != null) {
            reservations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new GuestReservationListAdapter(getActivity(), reservations, getActivity());
            setListAdapter(adapter);
        }
    }

}