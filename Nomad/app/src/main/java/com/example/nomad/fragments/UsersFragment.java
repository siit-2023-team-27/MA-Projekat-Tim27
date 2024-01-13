package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nomad.R;
import com.example.nomad.adapters.UnverifiedAccommodationsListAdapter;
import com.example.nomad.adapters.UsersAdapter;
import com.example.nomad.databinding.FragmentUnverifiedAccommodationsBinding;
import com.example.nomad.databinding.FragmentUsersBinding;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.services.UserService;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends ListFragment {


    private ArrayList<UserDTO> users;

    private static final String ARG_PARAM = "param";


    private UsersAdapter adapter;

    private FragmentUsersBinding binding;

    public UsersFragment() {
        // Required empty public constructor
    }



    public static UsersFragment newInstance(ArrayList<UserDTO> users) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            users = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new UsersAdapter(getActivity(), users, getActivity());
            setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //ListView listView = root.findViewById(R.id.list_users);
        //listView.setAdapter(adapter);



        return root;
    }

}