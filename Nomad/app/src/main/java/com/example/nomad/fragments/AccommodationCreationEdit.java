package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationCreationHostBinding;
import com.example.nomad.dto.AccommodationDTO;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AccommodationCreationEdit extends Fragment {

    private static final String ACCOMMODATION = "accommodation";

    private AccommodationDTO accommodation;
    private FragmentAccommodationCreationHostBinding binding;


    public AccommodationCreationEdit() {
        // Required empty public constructor
    }

    public static AccommodationCreationEdit newInstance(AccommodationDTO accommodation) {
        Log.e("BUG", "new instance of Creation sa parametarima");
        AccommodationCreationEdit fragment = new AccommodationCreationEdit();
        Bundle args = new Bundle();
        args.putParcelable(ACCOMMODATION, accommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodation = getArguments().getParcelable(ACCOMMODATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccommodationCreationHostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            try {
                if (accommodation != null) {
                    Log.e("BUG", "Calling CreateAccommodationFragment with arguments");
                    FragmentTransition.to(CreateAccommodationFragment.newInstance(accommodation), getActivity(), false, R.id.accommodationCreationHostView);
                }

            } catch (MalformedURLException | ExecutionException | InterruptedException |
                     TimeoutException e) {
                throw new RuntimeException(e);
            }

    }
}


