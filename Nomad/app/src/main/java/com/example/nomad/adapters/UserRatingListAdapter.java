package com.example.nomad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.fragments.UserRatingsFragment;
import com.example.nomad.fragments.accommodations.AccommodationCommentFragment;
import com.example.nomad.services.AccomodationsService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserRatingListAdapter extends ArrayAdapter<DTO.RatingDTO> {
    private ArrayList<DTO.RatingDTO> comments;
    private FragmentActivity activity;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private UserRatingsFragment ratingsFragment;

    public UserRatingListAdapter(Context context, ArrayList<DTO.RatingDTO> comments, FragmentActivity activity, UserRatingsFragment ratingsFragment){
        super(context, R.layout.comment_card, comments);
        this.comments = comments;
        this.activity = activity;
        this.ratingsFragment = ratingsFragment;

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
        return comments.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public DTO.RatingDTO getItem(int position) {
        return comments.get(position);
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
        DTO.RatingDTO comment = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_card,
                    parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.nameView);
        TextView commentView = convertView.findViewById(R.id.commentView);
        RatingBar rating = convertView.findViewById(R.id.commentRatingBar);
        FloatingActionButton commentReportButton = convertView.findViewById(R.id.commentReportButton);
//        FragmentAddAccommodationComment fragmentAddAccommodationComment = ().getBottomDrawerFragment();

        if(comment != null){
            //imageView.setImageResource(product.getImage());
            nameView.setText(comment.getUserName());
            commentView.setText(comment.getText());
            rating.setRating(comment.getRating());
            rating.setIsIndicator(true);
            commentReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ratingsFragment.showReportDrawer(comment.getId());
                }
            });
        }

        return convertView;
    }
}
