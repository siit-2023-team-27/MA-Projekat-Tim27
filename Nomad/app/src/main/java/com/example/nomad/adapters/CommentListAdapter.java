package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationRatingDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationCommentFragment;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.fragments.accommodations.FragmentAddAccommodationComment;
import com.example.nomad.model.AccommodationDTO;
import com.example.nomad.services.AccomodationsService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CommentListAdapter extends ArrayAdapter<AccommodationRatingDTO> {
    private ArrayList<AccommodationRatingDTO> comments;
    private FragmentActivity activity;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private AccommodationCommentFragment commentFragment;

    public CommentListAdapter(Context context, ArrayList<AccommodationRatingDTO> comments, FragmentActivity activity, AccommodationCommentFragment commentFragment){
        super(context, R.layout.comment_card, comments);
        this.comments = comments;
        this.activity = activity;
        this.commentFragment = commentFragment;

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
    public AccommodationRatingDTO getItem(int position) {
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
        AccommodationRatingDTO comment = getItem(position);
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
                    commentFragment.showReportDrawer(comment.getId());
                }
            });
//            commentCard.setOnClickListener(v -> {
//                // Handle click on the item at 'position'
//                Log.i("ShopApp", "Clicked: " + comment.getName() + ", id: " +
//                        comment.getId());
//                Toast.makeText(getContext(), "Clicked: " + comment.getName()  +
//                        ", id: " + comment.getId(), Toast.LENGTH_SHORT).show();
//                FragmentTransition.to( AccommodationFragment.newInstance("Pera"), activity, false, R.id.base_accommodations);
//
//
//            });
        }

        return convertView;
    }
}
