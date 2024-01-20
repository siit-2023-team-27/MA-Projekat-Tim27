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
import com.example.nomad.adapters.SearchedAccommodationListAdapter;
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.databinding.FragmentSearchedAccommodationListBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.SearchAccommodationDTO;

import java.util.ArrayList;

public class SearchedAccommodationListFragment extends ListFragment {

    private SearchedAccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<SearchAccommodationDTO> accommodations;
    private FragmentSearchedAccommodationListBinding binding;

    public static SearchedAccommodationListFragment newInstance(ArrayList<SearchAccommodationDTO> products){
        SearchedAccommodationListFragment fragment = new SearchedAccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, products);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ShopApp", "onCreateView Products List Fragment");
        binding = FragmentSearchedAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new SearchedAccommodationListAdapter(getActivity(), accommodations, getActivity());
            setListAdapter(adapter);
        }
    }
}