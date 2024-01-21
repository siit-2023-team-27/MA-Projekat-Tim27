package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.nomad.databinding.FragmentAccommodationCreationHostBinding;
import com.example.nomad.databinding.FragmentBaseAccommodationBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.fragments.accommodations.AccommodationsPageFragment;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AccommodationCreationHostFragment extends Fragment {
    private static final String ACCOMMODATION = "accommodation";
    private boolean transitionExecuted = false;

    private AccommodationDTO accommodation;
    private FragmentAccommodationCreationHostBinding binding;


    public AccommodationCreationHostFragment() {
        // Required empty public constructor
    }

    public static AccommodationCreationHostFragment newInstance() {
        Log.e("BUG", "new instance of Creation bez parametara");
        AccommodationCreationHostFragment fragment = new AccommodationCreationHostFragment();
        return fragment;
    }

    public static AccommodationCreationHostFragment newInstance(AccommodationDTO accommodation) {
        Log.e("BUG", "new instance of Creation sa parametarima");
        AccommodationCreationHostFragment fragment = new AccommodationCreationHostFragment();
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
//        try {
//            Log.e("BUG", "Calling CreateAccommodationFragment without arguments");
//            FragmentTransition.to(CreateAccommodationFragment.newInstance(), getActivity(), false, R.id.accommodationCreationHostView);
//
//        } catch (MalformedURLException | ExecutionException | InterruptedException | TimeoutException e) {
//            throw new RuntimeException(e);
//        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!transitionExecuted) {
            try {
                if (accommodation == null) {
                    Log.e("BUG", "Calling CreateAccommodationFragment without arguments");
                    FragmentTransition.to(CreateAccommodationFragment.newInstance(), getActivity(), false, R.id.accommodationCreationHostView);
                } else {
                    Log.e("BUG", "Calling CreateAccommodationFragment with arguments");
                    FragmentTransition.to(CreateAccommodationFragment.newInstance(accommodation), getActivity(), false, R.id.accommodationCreationHostView);
                }
                transitionExecuted = true;

            } catch (MalformedURLException | ExecutionException | InterruptedException |
                     TimeoutException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
