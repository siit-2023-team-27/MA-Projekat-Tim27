package com.example.nomad.fragments.accommodations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nomad.R;
import com.example.nomad.adapters.CommentListAdapter;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.fragments.ReportCommentFragment;
import com.example.nomad.services.AccommodationService;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ICanRateListener;
import com.example.nomad.services.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationCommentFragment extends ListFragment implements ICanRateListener {

    private ArrayList<AccommodationRatingDTO> comments = new ArrayList<AccommodationRatingDTO>();
    private  CommentListViewModel commentListViewModel = new CommentListViewModel();
    private ListView list;
    private FloatingActionButton addCommentButton;
    private FloatingActionButton deleteCommentButton;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private Long accommodationId;
    private AccommodationDTO accommodationDTO;

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public AccommodationCommentFragment(Long accommodationId) {
        this.accommodationId = accommodationId;
        AccomodationsService.subscribeCanRate(this);

    }
    public AccommodationCommentFragment(Long accommodationId, AccommodationDTO accommodationDTO) {
        this.accommodationId = accommodationId;
        this.accommodationDTO = accommodationDTO;
        AccomodationsService.subscribeCanRate(this);

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
        commentListViewModel.getComments(accommodationId);
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
        deleteCommentButton = view.findViewById(R.id.deleteCommentButton);
        deleteCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showBottomDrawer();
                deleteComment();
            }
        });
        deleteCommentButton.setVisibility(View.GONE);
        setupScrolling();
        AccomodationsService.canRate(accommodationId, AuthService.id);
        AccomodationsService.getComment(accommodationId);

        addCommentButton.setEnabled(AccomodationsService.canRate);


        super.onViewCreated(view, savedInstanceState);
    }

    private void deleteComment() {
        commentListViewModel.deleteOwnComment(accommodationDTO);
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

    private void showBottomDrawer() {
        FragmentAddAccommodationComment bottomDrawerFragment = new FragmentAddAccommodationComment(commentListViewModel, accommodationId, accommodationDTO);

        bottomDrawerFragment.show(getChildFragmentManager(), bottomDrawerFragment.getTag());
    }
    public void showReportDrawer(Long commentId){
        ReportCommentFragment reportDrawerFragment = new ReportCommentFragment(commentId);
        reportDrawerFragment.show(getChildFragmentManager(), reportDrawerFragment.getTag());
    }


    @Override
    public void canRateChanged() {
        addCommentButton.setEnabled(AccomodationsService.canRate);
        if (AccomodationsService.ownCommentId == null){
            return;
        }
        if(AccomodationsService.ownCommentId != -1L){
            addCommentButton.setVisibility(View.GONE);
            deleteCommentButton.setVisibility(View.VISIBLE);

        }else{
            addCommentButton.setVisibility(View.VISIBLE);
            deleteCommentButton.setVisibility(View.GONE);
        }
        Log.d("canRateChanged: ", String.valueOf(AccomodationsService.canRate));
        Log.d("canRateChanged: ", String.valueOf(AccomodationsService.ownCommentId));
    }
}