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
import com.example.nomad.adapters.CommentListAdapter;
import com.example.nomad.adapters.UserRatingListAdapter;
import com.example.nomad.fragments.accommodations.FragmentAddAccommodationComment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRatingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRatingsFragment extends ListFragment {

    private ArrayList<DTO.RatingDTO> ratings = new ArrayList<DTO.RatingDTO>();
    private UserRatingsViewModel userRatingsViewModel = new UserRatingsViewModel();
    private ListView list;
    private FloatingActionButton addRatingButton;
    private FloatingActionButton reportButton;

    public UserRatingsFragment(Long userId) {

    }
    public UserRatingsFragment(){

    }


    public static UserRatingsFragment newInstance(Long userId) {
        UserRatingsFragment fragment = new UserRatingsFragment(userId);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userRatingsViewModel.getElements().observe(this, elements -> {
            ratings = elements;
            UserRatingListAdapter adapter = new UserRatingListAdapter(getActivity(), ratings, getActivity(), this);
//            adapter.setReportText(FragmentAddAccommodationComment.getReportText());
            setListAdapter(adapter);
        });
        userRatingsViewModel.getComments(1L);
        UserRatingListAdapter adapter = new UserRatingListAdapter(getActivity(), ratings, getActivity(), this);
        setListAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_ratings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addRatingButton = view.findViewById(R.id.addUserRatingButton);
        reportButton = view.findViewById(R.id.reportUserButton);
        addRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDrawer();
            }
        });
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDrawer();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void showBottomDrawer() {
        AddUserReviewFragment bottomDrawerFragment = new AddUserReviewFragment(userRatingsViewModel);

        bottomDrawerFragment.show(getChildFragmentManager(), bottomDrawerFragment.getTag());
    }
    private void showReportDrawer() {
        ReportUserFragment reportUserFragment = new ReportUserFragment();

        reportUserFragment.show(getChildFragmentManager(), reportUserFragment.getTag());
    }


}