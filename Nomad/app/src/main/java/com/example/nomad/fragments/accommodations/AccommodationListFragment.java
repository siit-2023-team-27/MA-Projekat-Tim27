package com.example.nomad.fragments.accommodations;

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
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.dto.AccommodationDTO;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationListFragment extends ListFragment {

    private AccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<AccommodationDTO> accommodations;
    private Boolean isFavourites;
    private FragmentAccommodationListBinding binding;

    public static AccommodationListFragment newInstance(ArrayList<AccommodationDTO> products, Boolean isFavourites){
        AccommodationListFragment fragment = new AccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        args.putBoolean(ARG_PARAM2, isFavourites);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            isFavourites = getArguments().getBoolean(ARG_PARAM2);
            Log.d("ADAPTER", String.valueOf(accommodations.size()));
            adapter = new AccommodationListAdapter(getActivity(), accommodations, getActivity(), isFavourites);
            setListAdapter(adapter);
        }
    }

}