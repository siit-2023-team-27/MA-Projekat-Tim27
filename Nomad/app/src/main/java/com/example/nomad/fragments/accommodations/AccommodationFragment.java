package com.example.nomad.fragments.accommodations;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.activities.SliderAdapter;
import com.example.nomad.activities.SliderData;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.AccommodationRating;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.UserRatingsFragment;
import com.example.nomad.helper.EventDecorator;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ReservationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private MaterialCalendarView calendar;
    AccomodationsService accomodationsService = new AccomodationsService();
    ReservationService reservationService = new ReservationService();
    AuthService authService = new AuthService();
    FloatingActionButton hostActionButton;

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
//        mapView = rootView.findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(googleMap -> {
//            LatLng marker = new LatLng(0, 0);
//            googleMap.addMarker(new MarkerOptions().position(marker).title("Markeeer"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
//        });
        TextView name = rootView.findViewById(R.id.textView4);
        name.setText(accommodation.getName());
        TextView description = rootView.findViewById(R.id.description);
        description.setText(accommodation.getDescription());
        // Set rating
        RatingBar simpleRatingBar = rootView.findViewById(R.id.ratingBar);
        simpleRatingBar.setRating((float) 4.5);


        AccommodationCommentFragment fragment = new AccommodationCommentFragment(accommodation.getId(), accommodation);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // on below line replacing the fragment in child container with child fragment.
        transaction.replace(R.id.comments_container, fragment).commit();

        this.setCalendar(rootView);
        this.handleReservation(rootView);
        this.setImages(rootView);
        this.setAmenities(rootView);
//        this.setComments(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NestedScrollView v = view.findViewById(R.id.nestedScrollView);
        hostActionButton = view.findViewById(R.id.hostActionButton);
        hostActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRatingsFragment userRatingsFragment = new UserRatingsFragment(accommodation.getHostId());
                FragmentTransition.to(userRatingsFragment, getActivity(), true, R.id.base_accommodations);

            }
        });
        v.setNestedScrollingEnabled(true);
        
    }

    public void setAmenities(View rootView){
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
    }

    public void setCalendar(View rootView){

        this.calendar = rootView.findViewById(R.id.calendarAccommodationDetails);
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
    }

    private void handleReservation(View rootView){
        Button reserve = rootView.findViewById(R.id.reserve);
        EditText peopleNum = rootView.findViewById(R.id.peopleNumber);
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
    }

    private void setImages(View rootView){
        // Create an array list for storing image URLs.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<SliderData>();

        // Initialize the slider view.
        SliderView sliderView = rootView.findViewById(R.id.imageSlider);

        // Add image resources to the array list.
        sliderDataArrayList.add(new SliderData(R.drawable.img1));
        sliderDataArrayList.add(new SliderData(R.drawable.img2));
        sliderDataArrayList.add(new SliderData(R.drawable.img3));
        sliderDataArrayList.add(new SliderData(R.drawable.img4));

        // Create and set the adapter for the slider view.
        SliderAdapter adapter = new SliderAdapter(requireContext(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }
    private void setComments(View rootView){
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
    }
    private void sendNotification(String message) {
        String CHANNEL_ID = "1";
        int NOTIFICATION_ID = 0;
        Log.d("sendNotification: ", CHANNEL_ID);
        NotificationManager notificationManager = (NotificationManager) this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android Oreo and above)
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity().getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Default Android info icon
                .setContentTitle("My App")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setChannelId(CHANNEL_ID).setShowWhen(true);

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
