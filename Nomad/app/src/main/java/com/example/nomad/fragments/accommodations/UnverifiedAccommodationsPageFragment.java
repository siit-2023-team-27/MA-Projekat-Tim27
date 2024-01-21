package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentUnverifiedAccommodationsPageBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.AccommodationType;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.services.AccomodationsService;

import java.util.ArrayList;
import java.util.List;

public class UnverifiedAccommodationsPageFragment extends Fragment {

    AccomodationsService accomodationsService = new AccomodationsService();
    private ArrayList<AccommodationDTO> unverifiedAccommodations = new ArrayList<AccommodationDTO>();

    private FragmentUnverifiedAccommodationsPageBinding binding;


    public UnverifiedAccommodationsPageFragment() {
        // Required empty public constructor
    }


    public static UnverifiedAccommodationsPageFragment newInstance() {
        UnverifiedAccommodationsPageFragment fragment = new UnverifiedAccommodationsPageFragment();
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
        binding = FragmentUnverifiedAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareProductList();
        return root;
    }

    private void prepareProductList(){

        accomodationsService.getUnverifiedAccommodations();
        accomodationsService.getUnverified().observe(getActivity(), new Observer<List<AccommodationDTO>>() {
            @Override
            public void onChanged(List<AccommodationDTO> objects) {
                // Update your UI or perform any actions when LiveData changes

                // Now, you can convert the LiveData to a List if needed
                unverifiedAccommodations = (ArrayList<AccommodationDTO>) objects;
                // Do something with the list
                FragmentTransition.to(UnverifiedAccommodationsFragment.newInstance(unverifiedAccommodations), getActivity(), false, R.id.scroll_products_list);

            }
        });

//        unverifiedAccommodations.add(new AccommodationDTO(1L, 1, 4, "Vila", "opis", "adresa", new ArrayList<Amenity>(), new ArrayList<String>(), AccommodationStatus.PENDING,
//                ConfirmationType.AUTOMATIC, AccommodationType.HOUSE, PriceType.FOR_ACCOMMODATION, 100, 10, false));
//        FragmentTransition.to(UnverifiedAccommodationsFragment.newInstance(unverifiedAccommodations), getActivity(), false, R.id.scroll_products_list);
//
    }
}