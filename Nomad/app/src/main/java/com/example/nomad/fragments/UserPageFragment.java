package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentUserPageBinding;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.fragments.accommodations.UnverifiedAccommodationsFragment;
import com.example.nomad.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPageFragment extends Fragment {

    private UserService userService = new UserService();

    private ArrayList<UserDTO> users;

    private FragmentUserPageBinding binding;

    public UserPageFragment() {
        // Required empty public constructor
    }

    public static UserPageFragment newInstance(String param1, String param2) {
        UserPageFragment fragment = new UserPageFragment();
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
        binding = FragmentUserPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareData();
        return root;
    }

    public void prepareData() {
        userService.getAllUsers();
        userService.getUsers().observe(getActivity(), new Observer<List<UserDTO>>() {
            @Override
            public void onChanged(List<UserDTO> appUsers) {
                users = (ArrayList<UserDTO>) appUsers;
                Log.d("UsersFragment", "Received " + users.size() + " users");
                FragmentTransition.to(UsersFragment.newInstance(users), getActivity(), false, R.id.scroll_users_list);
            }
        });
    }
}