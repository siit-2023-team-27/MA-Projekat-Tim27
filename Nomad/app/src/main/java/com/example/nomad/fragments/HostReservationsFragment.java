package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;

public class HostReservationsFragment extends Fragment {

    public HostReservationsFragment() {
        // Required empty public constructor
    }

    public static HostReservationsFragment newInstance(String param1, String param2) {
        HostReservationsFragment fragment = new HostReservationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_host_reservations, container, false);
    }
}