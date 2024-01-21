package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nomad.R;
import com.example.nomad.adapters.UserRatingListAdapter;
import com.example.nomad.adapters.UserReportListAdapter;
import com.example.nomad.dto.UserReportDetailsDTO;
import com.example.nomad.dto.UserReportDto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRatingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserReportsFragment extends ListFragment {

    private ArrayList<UserReportDetailsDTO> reports = new ArrayList<UserReportDetailsDTO>();
    private UserReportsViewModel userReportsViewModel = new UserReportsViewModel();
    private ListView list;


    public UserReportsFragment() {
    }



    public static UserRatingsFragment newInstance() {
        UserRatingsFragment fragment = new UserRatingsFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userReportsViewModel.getElements().observe(this, elements -> {
            reports = elements;
            UserReportListAdapter adapter = new UserReportListAdapter(getActivity(), reports, getActivity(), this);
//            adapter.setReportText(FragmentAddAccommodationComment.getReportText());
            setListAdapter(adapter);
        });
        userReportsViewModel.getUserReports();
        UserReportListAdapter adapter = new UserReportListAdapter(getActivity(), reports, getActivity(), this);
        setListAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public void archive(Long id) {
        userReportsViewModel.archiveUserReport(id);
    }

    public void accept(Long id) {
        userReportsViewModel.acceptUserReport(id);
    }
}