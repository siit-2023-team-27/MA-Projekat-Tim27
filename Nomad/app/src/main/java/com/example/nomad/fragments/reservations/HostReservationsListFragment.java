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
import com.example.nomad.adapters.GuestReservationListAdapter;
import com.example.nomad.adapters.HostReservationListAdapter;
import com.example.nomad.databinding.FragmentGuestReservationsListBinding;
import com.example.nomad.databinding.FragmentHostReservationsListBinding;
import com.example.nomad.dto.ReservationResponseDTO;

import java.util.ArrayList;

public class HostReservationsListFragment extends ListFragment {

    private HostReservationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ReservationResponseDTO> reservations;
    private FragmentHostReservationsListBinding binding;
    private boolean isRequestPage ;

    public static HostReservationsListFragment newInstance(ArrayList<ReservationResponseDTO> products){
        HostReservationsListFragment fragment = new HostReservationsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentHostReservationsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate guest reservations List Fragment");
        if (getArguments() != null) {
            reservations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new HostReservationListAdapter(getActivity(), reservations, getActivity());
            setListAdapter(adapter);
        }
    }

}