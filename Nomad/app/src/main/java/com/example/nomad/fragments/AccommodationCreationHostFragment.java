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


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccommodationCreationHostFragment() {
        // Required empty public constructor


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccommodationCreationHostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccommodationCreationHostFragment newInstance(String param1, String param2) {
        AccommodationCreationHostFragment fragment = new AccommodationCreationHostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
