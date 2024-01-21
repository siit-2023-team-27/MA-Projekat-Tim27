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
import com.example.nomad.dto.CommentReportDetailsDTO;
import com.example.nomad.fragments.CommentReportFragment;
import com.example.nomad.services.AccomodationsService;

import java.util.ArrayList;

public class CommentReportListAdapter extends ArrayAdapter<CommentReportDetailsDTO> {
    private ArrayList<CommentReportDetailsDTO> reports;
    private FragmentActivity activity;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private CommentReportFragment commentReportFragment;

    public CommentReportListAdapter(Context context, ArrayList<CommentReportDetailsDTO> comments, FragmentActivity activity, CommentReportFragment commentReportFragment){
        super(context, R.layout.comment_card, comments);
        this.reports = comments;
        this.activity = activity;
        this.commentReportFragment = commentReportFragment;

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
    public CommentReportDetailsDTO getItem(int position) {
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
        CommentReportDetailsDTO report = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_report_card,
                    parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.reportingNameView);
        TextView commentView = convertView.findViewById(R.id.reportedNameView);
        TextView reasonView = convertView.findViewById(R.id.reasonView);
        TextView statusView = convertView.findViewById(R.id.statusView);
        RatingBar rating = convertView.findViewById(R.id.commentRatingBar);
        Button acceptButton = convertView.findViewById(R.id.acceptCommentReportButton);
        Button archiveButton = convertView.findViewById(R.id.archiveCommentReportButton);
//        FragmentAddAccommodationComment fragmentAddAccommodationComment = ().getBottomDrawerFragment();

        if(report != null){
//            imageView.setImageResource(product.getImage());
            nameView.setText(report.getReportedUserName());
            statusView.setText(report.getReportStatus());
            reasonView.setText(report.getReason());
            commentView.setText(report.getReportedCommentText());
            rating.setRating(report.getReportedCommentRating());
            rating.setIsIndicator(true);
            acceptButton.setVisibility(View.VISIBLE);
            archiveButton.setVisibility(View.VISIBLE);
            acceptButton.setEnabled(true);
            archiveButton.setEnabled(true);

            if(!report.getReportStatus().contains("PENDING")){
                acceptButton.setVisibility(View.GONE);
                archiveButton.setVisibility(View.GONE);

                acceptButton.setEnabled(false);
                archiveButton.setEnabled(false);
            }else {
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentReportFragment.accept(report.getId());
                    }
                });
                archiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentReportFragment.archive(report.getId());
                    }
                });
            }
        }

        return convertView;
    }
}
