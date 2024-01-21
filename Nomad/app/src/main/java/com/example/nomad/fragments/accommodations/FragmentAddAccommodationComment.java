package com.example.nomad.fragments.accommodations;

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
import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRatingCreationDTO;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddAccommodationComment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddAccommodationComment extends BottomSheetDialogFragment {
    EditText commentText;
    Button commentButton;
    RatingBar commentRating;
    AccomodationsService accomodationsService = new AccomodationsService();
    Long accommodationId;
    AccommodationDTO accommodationDTO;
    CommentListViewModel commentListViewModel;
    public FragmentAddAccommodationComment(CommentListViewModel commentListViewModel, Long accommodationId, AccommodationDTO accommodationDTO) {
        this.commentListViewModel = commentListViewModel;
        this.accommodationId = accommodationId;
        this.accommodationDTO = accommodationDTO;
    }
    public FragmentAddAccommodationComment(){

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
        commentText = view.findViewById(R.id.commentTextEdit);
        commentButton = view.findViewById(R.id.commentButton);
        commentRating = view.findViewById(R.id.addCommentRatingBar);
        commentRating.setStepSize(1.0F);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
    private void addComment(){
        AccommodationRatingCreationDTO commentDTO = new AccommodationRatingCreationDTO();
        commentDTO.setRatedId(accommodationId);
        commentDTO.setRating((int)Math.floor(commentRating.getRating()));
        commentDTO.setText(commentText.getText().toString());
        commentDTO.setUserId(AuthService.id);
        Log.d("addComment: ", String.valueOf(this.commentListViewModel.getElements().getValue().size()));
        commentListViewModel.addComment(commentDTO);
        Toast.makeText(this.getContext(), "Added comment", Toast.LENGTH_SHORT);
        Log.d("addComment: ", String.valueOf(this.commentListViewModel.getElements().getValue().size()));

        HomeActivity.notificationService.sendNotification("New comment on your accommodation", "New Comment", "NEW_ACCOMMODATION_RATING", accommodationDTO.getHostId());
        this.dismiss();
    }
}