package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nomad.R;
import com.example.nomad.adapters.HostAccommodationsAdapter;
import com.example.nomad.databinding.FragmentHostListingBinding;
import com.example.nomad.databinding.FragmentUnverifiedAccommodationsPageBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.AccommodationCreationHostFragment;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.services.AccommodationService;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;

import java.util.ArrayList;
import java.util.List;


public class HostListing extends ListFragment {

    HostAccommodationsAdapter adapter;

    AccomodationsService accomodationsService = new AccomodationsService();

    private ArrayList<AccommodationDTO> accommodations;
    FragmentHostListingBinding binding;


    public HostListing() {
        // Required empty public constructor
    }

    public static HostListing newInstance(String param1, String param2) {
        HostListing fragment = new HostListing();
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
        binding = FragmentHostListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareData();

        Button buttonAddAccommodation = binding.buttonAddAccommodation;
        buttonAddAccommodation.setOnClickListener(v -> {
            //FragmentTransition.to(AccommodationCreationHostFragment.newInstance(), getActivity(), false, R.id.root_linear_host_listing);
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_listing_to_nav_create_accommodation);
        });

        return root;
    }

    public void prepareData() {
        accomodationsService.getAccommodationsForHost(AuthService.id);
        accomodationsService.getHostAccommodations().observe(getActivity(), new Observer<List<AccommodationDTO>>() {
            @Override
            public void onChanged(List<AccommodationDTO> objects) {
                accommodations = (ArrayList<AccommodationDTO>) objects;
                adapter = new HostAccommodationsAdapter(getActivity(), accommodations, getActivity());
                setListAdapter(adapter);
            }
        });
    }
}