package com.example.nomad.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nomad.R;
import java.util.ArrayList;

import com.example.nomad.activities.SliderAdapter;
import com.example.nomad.activities.SliderData;
import com.example.nomad.fragments.FavouritesFragment;
import com.smarteist.autoimageslider.SliderView;
public class AccommodationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AccommodationFragment() {
        // Required empty public constructor
    }
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_accommodation);

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = onCreateView(inflater, container, savedInstanceState).findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(R.drawable.img1));
        sliderDataArrayList.add(new SliderData(R.drawable.img2));
        sliderDataArrayList.add(new SliderData(R.drawable.img3));
        sliderDataArrayList.add(new SliderData(R.drawable.img4));

        //set rating
        RatingBar simpleRatingBar = (RatingBar) onCreateView(inflater, container, savedInstanceState).findViewById(R.id.ratingBar); // initiate a rating bar
        simpleRatingBar.setRating((float) 4.5);

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        LinearLayout layout = (LinearLayout) onCreateView(inflater, container, savedInstanceState).findViewById(R.id.image_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(getActivity());
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setChecked(true);

            // Disable the checkbox to prevent user interaction
            checkBox.setEnabled(false);

            // Make the checkbox non-clickable
            checkBox.setClickable(false);
            textView.setPadding(60, 0, 60, 0);
            textView.setText("Internet");
            textView.setLayoutParams(layoutParams);
            checkBox.setLayoutParams(layoutParams);
            layout.addView(checkBox);
            layout.addView(textView);

        }

        LinearLayout layoutComments = (LinearLayout) onCreateView(inflater, container, savedInstanceState).findViewById(R.id.comments_container);
        LinearLayout.LayoutParams layoutParamsComments = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams pictureLayoutParams = new LinearLayout.LayoutParams(100, 100);


        for (int i = 0; i <2; i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.user);
            imageView.setLayoutParams(pictureLayoutParams);

            TextView textView = new TextView(getActivity());
            textView.setPadding(20, 0, 0, 0);
            textView.setText("Jane Doe");
            textView.setLayoutParams(layoutParamsComments);

            linearLayout.setPadding(0, 40, 0, 0);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            TextView description = new TextView(getActivity());
            description.setPadding(0, 20, 0, 0);
            description.setText("Well... we were not very happy with this place..");
            description.setLayoutParams(layoutParamsComments);

            layoutComments.addView(linearLayout);
            layoutComments.addView(description);
        }


        return inflater.inflate(R.layout.fragment_accommodation, container, false);
    }
}