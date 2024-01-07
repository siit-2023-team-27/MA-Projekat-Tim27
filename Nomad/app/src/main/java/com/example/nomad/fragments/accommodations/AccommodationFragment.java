package com.example.nomad.fragments.accommodations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nomad.R;
import com.example.nomad.activities.SliderAdapter;
import com.example.nomad.activities.SliderData;
import com.example.nomad.model.AccommodationDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class AccommodationFragment extends Fragment {
    MapView mapView;
    private static final String ARG_PARAM2 = "name";
    private String title;

    public static AccommodationFragment newInstance(String name) {
        AccommodationFragment fragment = new AccommodationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, name);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ShopApp", "SecondFragment onCreate()");

        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accommodation, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            LatLng marker = new LatLng(0, 0);
            googleMap.addMarker(new MarkerOptions().position(marker).title("Markeeer"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        });
        TextView name = rootView.findViewById(R.id.textView4);
        name.setText(title);


        // Create an array list for storing image URLs.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<SliderData>();

        // Initialize the slider view.
        SliderView sliderView = rootView.findViewById(R.id.slider);

        // Add image resources to the array list.
        sliderDataArrayList.add(new SliderData(R.drawable.img1));
        sliderDataArrayList.add(new SliderData(R.drawable.img2));
        sliderDataArrayList.add(new SliderData(R.drawable.img3));
        sliderDataArrayList.add(new SliderData(R.drawable.img4));

        // Set rating
        RatingBar simpleRatingBar = rootView.findViewById(R.id.ratingBar);
        simpleRatingBar.setRating((float) 4.5);

        // Create and set the adapter for the slider view.
        SliderAdapter adapter = new SliderAdapter(requireContext(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        LinearLayout layout = rootView.findViewById(R.id.image_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(requireContext());
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
            checkBox.setClickable(false);
            textView.setPadding(60, 0, 60, 0);
            textView.setText("Internet");
            textView.setLayoutParams(layoutParams);
            checkBox.setLayoutParams(layoutParams);
            layout.addView(checkBox);
            layout.addView(textView);
        }

        LinearLayout layoutComments = rootView.findViewById(R.id.comments_container);
        LinearLayout.LayoutParams layoutParamsComments = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams pictureLayoutParams = new LinearLayout.LayoutParams(100, 100);

        for (int i = 0; i < 2; i++) {
            LinearLayout linearLayout = new LinearLayout(requireContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView imageView = new ImageView(requireContext());
            imageView.setImageResource(R.drawable.user);
            imageView.setLayoutParams(pictureLayoutParams);

            TextView textView = new TextView(requireContext());
            textView.setPadding(20, 0, 0, 0);
            textView.setText("Jane Doe");
            textView.setLayoutParams(layoutParamsComments);

            linearLayout.setPadding(0, 40, 0, 0);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            TextView description = new TextView(requireContext());
            description.setPadding(0, 20, 0, 0);
            description.setText("Well... we were not very happy with this place..");
            description.setLayoutParams(layoutParamsComments);

            layoutComments.addView(linearLayout);
            layoutComments.addView(description);
        }

        return rootView;
    }
}
