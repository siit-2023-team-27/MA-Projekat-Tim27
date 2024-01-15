package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nomad.R;
import com.example.nomad.adapters.CommentListAdapter;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.fragments.ReportCommentFragment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationCommentFragment extends ListFragment {

    private ArrayList<AccommodationRatingDTO> comments = new ArrayList<AccommodationRatingDTO>();
    private  CommentListViewModel commentListViewModel = new CommentListViewModel();
    private ListView list;
    private FloatingActionButton addCommentButton;

    public AccommodationCommentFragment(Long AccommodationId) {

    }
    public AccommodationCommentFragment(){

    }


    public static AccommodationCommentFragment newInstance(Long accommodationId) {
        AccommodationCommentFragment fragment = new AccommodationCommentFragment(accommodationId);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        commentListViewModel.getElements().observe(this, elements -> {
            comments = elements;
            CommentListAdapter adapter = new CommentListAdapter(getActivity(), comments, getActivity(), this);
//            adapter.setReportText(FragmentAddAccommodationComment.getReportText());
            setListAdapter(adapter);
        });
        commentListViewModel.getComments(1L);
        CommentListAdapter adapter = new CommentListAdapter(getActivity(), comments, getActivity(), this);
        setListAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accommodation_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addCommentButton = view.findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDrawer();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void showBottomDrawer() {
        FragmentAddAccommodationComment bottomDrawerFragment = new FragmentAddAccommodationComment(commentListViewModel);

        bottomDrawerFragment.show(getChildFragmentManager(), bottomDrawerFragment.getTag());
    }
    public void showReportDrawer(Long commentId){
        ReportCommentFragment reportDrawerFragment = new ReportCommentFragment(commentId);
        reportDrawerFragment.show(getChildFragmentManager(), reportDrawerFragment.getTag());

    }

}