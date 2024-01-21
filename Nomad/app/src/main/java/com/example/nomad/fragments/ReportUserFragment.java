package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.RatingCreationDTO;
import com.example.nomad.dto.UserReportDto;
import com.example.nomad.fragments.accommodations.FragmentAddAccommodationComment;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.UserService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportUserFragment extends BottomSheetDialogFragment {

    EditText reportText;
    Button reportButton;
    UserService userService = new UserService();
    private Long userId;
//    UserRatingsViewModel userRatingsViewModel;

    public ReportUserFragment(Long userId){
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
        return inflater.inflate(R.layout.fragment_report_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        reportText = view.findViewById(R.id.userReportTextEdit);
        reportButton = view.findViewById(R.id.userReportButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReport();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
    private void addReport(){
        UserReportDto userReportDto = new UserReportDto();
        userReportDto.setReportedUser(userId);
        userReportDto.setReason(reportText.getText().toString());
        userReportDto.setReportingUser(AuthService.id);
        userService.reportUser(userReportDto);
//        Log.d("addComment: ", String.valueOf(this.userRatingsViewModel.getElements().getValue().size()));
//        userRatingsViewModel.addRating(userReportDto);
        Toast.makeText(this.getContext(), "Added report", Toast.LENGTH_SHORT);
//        Log.d("addComment: ", String.valueOf(this.userRatingsViewModel.getElements().getValue().size()));
        this.dismiss();
    }
}