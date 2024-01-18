package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.fragments.accommodations.CommentListViewModel;
import com.example.nomad.fragments.accommodations.FragmentAddAccommodationComment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUserReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserReviewFragment extends BottomSheetDialogFragment {

    EditText ratingText;
    Button ratingButton;
    RatingBar reviewRating;
    AccomodationsService accomodationsService = new AccomodationsService();
    UserRatingsViewModel userRatingsViewModel;
    private Long userId;
    public AddUserReviewFragment(UserRatingsViewModel userRatingsViewModel) {
        this.userRatingsViewModel = userRatingsViewModel;
    }
    public AddUserReviewFragment(){

    }

    public AddUserReviewFragment(UserRatingsViewModel userRatingsViewModel, Long userId) {
        this.userRatingsViewModel = userRatingsViewModel;
        this.userId = userId;
    }

    public static FragmentAddAccommodationComment newInstance(String param1, String param2) {
        FragmentAddAccommodationComment fragment = new FragmentAddAccommodationComment();

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
        return inflater.inflate(R.layout.fragment_add_accommodation_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ratingText = view.findViewById(R.id.commentTextEdit);
        ratingButton = view.findViewById(R.id.commentButton);
        reviewRating = view.findViewById(R.id.addCommentRatingBar);
        reviewRating.setStepSize(1.0F);

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
    private void addComment(){
        RatingCreationDTO commentDTO = new RatingCreationDTO();
        commentDTO.setRatedId(userId);
        commentDTO.setRating((int)Math.floor(reviewRating.getRating()));
        commentDTO.setText(ratingText.getText().toString());
        commentDTO.setUserId(AuthService.id);
        Log.d("addComment: ", String.valueOf(this.userRatingsViewModel.getElements().getValue().size()));
        userRatingsViewModel.addRating(commentDTO);
        Toast.makeText(this.getContext(), "Added comment", Toast.LENGTH_SHORT);
        Log.d("addComment: ", String.valueOf(this.userRatingsViewModel.getElements().getValue().size()));
        this.dismiss();
    }
}