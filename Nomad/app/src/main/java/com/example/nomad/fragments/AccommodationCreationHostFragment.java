package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nomad.databinding.FragmentAccommodationCreationHostBinding;
import com.example.nomad.databinding.FragmentBaseAccommodationBinding;
import com.example.nomad.fragments.FragmentTransition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.fragments.accommodations.AccommodationsPageFragment;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationCreationHostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationCreationHostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAccommodationCreationHostBinding binding;


    public AccommodationCreationHostFragment() {
        // Required empty public constructor
    }

    public static AccommodationCreationHostFragment newInstance() {
        AccommodationCreationHostFragment fragment = new AccommodationCreationHostFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentAccommodationCreationHostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            FragmentTransition.to(CreateAccommodationFragment.newInstance("t", "t"), getActivity(), false, R.id.accommodationCreationHostView);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
//        return inflater.inflate(R.layout.fragment_accommodation_creation_host, container, false);
        return root;
    }


}
