package com.example.nomad.fragments.accommodations;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.activities.SliderAdapter;
import com.example.nomad.activities.SliderData;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRating;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.helper.EventDecorator;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ReservationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.smarteist.autoimageslider.SliderView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccommodationFragment extends Fragment {
    MapView mapView;
    private static final String ARG_PARAM = "accommodation";
    private AccommodationDTO accommodation;
    AccomodationsService accomodationsService = new AccomodationsService();
    ReservationService reservationService = new ReservationService();
    AuthService authService = new AuthService();

    public static AccommodationFragment newInstance(AccommodationDTO accommodation) {
        AccommodationFragment fragment = new AccommodationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, accommodation);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ShopApp", "SecondFragment onCreate()");

        if (getArguments() != null) {
            //title = getArguments().getString(ARG_PARAM2);
            accommodation = getArguments().getParcelable(ARG_PARAM);
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
        name.setText(accommodation.getName());
        TextView description = rootView.findViewById(R.id.description);
        description.setText(accommodation.getDescription());

        MaterialCalendarView calendar = rootView.findViewById(R.id.calendarAccommodationDetails);
        accomodationsService.loadTakenDates(this.accommodation.getId());
        accomodationsService.getTakenDates().observe(getActivity(), new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> objects) {
                // Update your UI or perform any actions when LiveData changes
                List<Date> dates = (ArrayList<Date>) objects;
                List<CalendarDay> calendarDates = new ArrayList<>();
                for (Date date: dates) {

                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    CalendarDay calendarDay = CalendarDay.from(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
                    calendarDates.add(calendarDay);
                }

                calendar.addDecorator(new EventDecorator(Color.RED,calendarDates ));
                calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
            }
        });
        EditText peopleNum = rootView.findViewById(R.id.peopleNumber);


        Button reserve = rootView.findViewById(R.id.reserve);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CalendarDay> selectedDates = calendar.getSelectedDates();
                for (CalendarDay date : selectedDates){
                    Log.i("ShopApp", "Selected: " + date.toString());
                }
                reservationService.create(new ReservationDTO(authService.getId(),
                        accommodation.getId(), Helper.toDate(selectedDates.get(0)),
                        Helper.toDate(selectedDates.get(selectedDates.size()-1)),
                        Integer.valueOf(peopleNum.getText().toString())));

            }
        });
        reservationService.getResponse().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean objects) {
                // Update your UI or perform any actions when LiveData changes
                Boolean responseSuccessful =  objects;
                Log.i("Response reservation", "Selected: " + responseSuccessful.toString());

                if(responseSuccessful){
                    Toast.makeText(getContext(), "Reservation made successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Reservation forbidden", Toast.LENGTH_SHORT).show();

                }

            }
        });

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

        for (int i = 0; i < accommodation.getAmenities().size(); i++) {
            TextView textView = new TextView(requireContext());
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
            checkBox.setClickable(false);
            textView.setPadding(60, 0, 60, 0);
            textView.setText(accommodation.getAmenities().get(i).getName());
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

            TextView description2 = new TextView(requireContext());
            description2.setPadding(0, 20, 0, 0);
            description2.setText("Well... we were not very happy with this place..");
            description2.setLayoutParams(layoutParamsComments);

            layoutComments.addView(linearLayout);
            layoutComments.addView(description2);
        }

        return rootView;
    }


}
