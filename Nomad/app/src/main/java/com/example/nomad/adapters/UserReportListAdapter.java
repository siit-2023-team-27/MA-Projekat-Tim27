package com.example.nomad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.UserReportDetailsDTO;
import com.example.nomad.fragments.UserReportsFragment;
import com.example.nomad.services.AccomodationsService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserReportListAdapter extends ArrayAdapter<UserReportDetailsDTO> {
    private ArrayList<UserReportDetailsDTO> reports;
    private FragmentActivity activity;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private UserReportsFragment userReportsFragment;

    public UserReportListAdapter(Context context, ArrayList<UserReportDetailsDTO> reports, FragmentActivity activity, UserReportsFragment userReportsFragment){
        super(context, R.layout.comment_card, reports);
        this.reports = reports;
        this.activity = activity;
        this.userReportsFragment = userReportsFragment;

    }

    public EditText getReportText() {
        return reportText;
    }

    public void setReportText(EditText reportText) {
        this.reportText = reportText;
    }

    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return reports.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public UserReportDetailsDTO getItem(int position) {
        return reports.get(position);
    }
    private EditText reportText;
    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserReportDetailsDTO report = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_report_card,
                    parent, false);
        }

        TextView reportedNameView = convertView.findViewById(R.id.reportedNameView);
        TextView reportingNameView = convertView.findViewById(R.id.reportingNameView);
        TextView reasonView = convertView.findViewById(R.id.reasonTextView);
        TextView statusView = convertView.findViewById(R.id.statusTextView);
        Button archiveButton = convertView.findViewById(R.id.archiveUserReportButton);
        Button acceptButton = convertView.findViewById(R.id.acceptUserReportButton);
//        FragmentAddAccommodationComment fragmentAddAccommodationComment = ().getBottomDrawerFragment();
        acceptButton.setVisibility(View.VISIBLE);
        archiveButton.setVisibility(View.VISIBLE);
        if(report != null){
            reportedNameView.setText(report.getReportedUserName());
            reportingNameView.setText(report.getReportingUserName());
            reasonView.setText(report.getReason());
            statusView.setText(report.getReportStatus());
            if(!report.getReportStatus().contains("PENDING")){
                acceptButton.setVisibility(View.GONE);
                archiveButton.setVisibility(View.GONE);

                acceptButton.setEnabled(false);
                archiveButton.setEnabled(false);
            }else{
                archiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userReportsFragment.archive(report.getId());
                    }
                });
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userReportsFragment.accept(report.getId());
                    }
                });
            }


        }

        return convertView;
    }
}
