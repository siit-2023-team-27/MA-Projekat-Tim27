package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nomad.R;
import com.example.nomad.adapters.CommentListAdapter;
import com.example.nomad.adapters.CommentReportListAdapter;
import com.example.nomad.dto.CommentReportDetailsDTO;
import com.example.nomad.fragments.CommentReportViewModel;
import com.example.nomad.fragments.ReportCommentFragment;
import com.example.nomad.fragments.accommodations.AccommodationCommentFragment;
import com.example.nomad.fragments.accommodations.FragmentAddAccommodationComment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ICanRateListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentReportFragment extends ListFragment{

    private ArrayList<CommentReportDetailsDTO> reports = new ArrayList<CommentReportDetailsDTO>();
    private CommentReportViewModel commentReportViewModel = new CommentReportViewModel();
    private ListView list;
    private FloatingActionButton addCommentButton;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private Long accommodationId;

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public CommentReportFragment(Long accommodationId) {
        this.accommodationId = accommodationId;

    }
    public CommentReportFragment(){

    }


    public static AccommodationCommentFragment newInstance(Long accommodationId) {
        AccommodationCommentFragment fragment = new AccommodationCommentFragment(accommodationId);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commentReportViewModel.getElements().observe(this, elements -> {
            reports = elements;
            CommentReportListAdapter adapter = new CommentReportListAdapter(getActivity(), reports, getActivity(), this);

//            adapter.setReportText(FragmentAddAccommodationComment.getReportText());
            setListAdapter(adapter);
        });
        commentReportViewModel.getUserReports();
        CommentReportListAdapter adapter = new CommentReportListAdapter(getActivity(), reports, getActivity(), this);
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

        setupScrolling();
        super.onViewCreated(view, savedInstanceState);
    }
    private void setupScrolling(){
        getListView().setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });
    }


    public void accept(Long id) {
        commentReportViewModel.acceptCommentReport(id);
    }

    public void archive(Long id) {
        commentReportViewModel.archiveCommentReport(id);
    }
}