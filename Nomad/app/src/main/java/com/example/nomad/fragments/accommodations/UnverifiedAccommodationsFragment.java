package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.adapters.AccommodationListAdapter;
import com.example.nomad.adapters.UnverifiedAccommodationsListAdapter;
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.databinding.FragmentUnverifiedAccommodationsBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.services.AccomodationsService;

import java.util.ArrayList;
import java.util.List;

public class UnverifiedAccommodationsFragment extends ListFragment {

    private static final String ARG_PARAM = "param";
    private UnverifiedAccommodationsListAdapter adapter;
    private ArrayList<AccommodationDTO> unverifiedAccommodations;

    private FragmentUnverifiedAccommodationsBinding binding;


    public UnverifiedAccommodationsFragment() {
        // Required empty public constructor
    }



    public static UnverifiedAccommodationsFragment newInstance(ArrayList<AccommodationDTO> accommodations) {
        UnverifiedAccommodationsFragment fragment = new UnverifiedAccommodationsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        binding = FragmentUnverifiedAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unverifiedAccommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new UnverifiedAccommodationsListAdapter(getActivity(), unverifiedAccommodations, getActivity());
            setListAdapter(adapter);
        }
    }


}