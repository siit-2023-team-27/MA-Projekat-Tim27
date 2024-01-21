package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;
import com.example.nomad.fragments.reservations.GuestReservationsFragment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ArrayList<AccommodationDTO> accommodations;

    AccomodationsService accomodationsService = new AccomodationsService();
    AuthService authService = new AuthService();
    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    public FavouritesFragment() {
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
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        this.prepareProductList();
        return root;
    }
    private void prepareProductList(){
        accomodationsService.loadFavourites(authService.getId());
        accomodationsService.getFavourites().observe(getActivity(), new Observer<Collection<AccommodationDTO>>() {
            @Override
            public void onChanged(Collection<AccommodationDTO> objects) {
                // Update your UI or perform any actions when LiveData changes

                // Now, you can convert the LiveData to a List if needed
                accommodations = (ArrayList<AccommodationDTO>) objects;
                // Do something with the list
                FragmentTransition.to(AccommodationListFragment.newInstance(accommodations, true), getActivity(), true, R.id.scroll_products_list);
            }
        });

    }
}