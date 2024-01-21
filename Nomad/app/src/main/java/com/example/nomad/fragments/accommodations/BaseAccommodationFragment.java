package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.databinding.FragmentBaseAccommodationBinding;
import com.example.nomad.fragments.FragmentTransition;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseAccommodationFragment extends Fragment {
    private FragmentBaseAccommodationBinding binding;
    public BaseAccommodationFragment() {
        // Required empty public constructor
    }
    public static AccommodationsPageFragment newInstance() {
        return new AccommodationsPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_base_accommodation, container, false);
        binding = FragmentBaseAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(AccommodationsPageFragment.newInstance(), getActivity(), false, R.id.base_accommodations);

        return root;
    }
}