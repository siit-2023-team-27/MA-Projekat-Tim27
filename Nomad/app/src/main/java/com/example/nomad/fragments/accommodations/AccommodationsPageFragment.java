package com.example.nomad.fragments.accommodations;

import static android.content.Context.SENSOR_SERVICE;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.mapsforge.map.rendertheme.renderinstruction.Line;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationsPageFragment extends Fragment implements SensorEventListener {
    public static ArrayList<AccommodationDTO> accommodations;
    public static ArrayList<Amenity> amenities;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog calendarDialog;
    View dialogView;
    View calendarView;
    boolean areAmenitiesLoaded;
    private SensorManager sensorMgr;
    public static ArrayList<SearchAccommodationDTO> searchAccommodationDTOS;
    private MaterialCalendarView calendar;
    private AccommodationsPageViewModel accommodationsPageViewModel;
    private FragmentAccommodationsPageBinding binding;
    private long lastUpdate = 0;
    private float last_x = 0;
    private float last_gyro_x = 0;
    private float last_y = 0;
    private float last_z = 0;
    private float SHAKE_THRESHOLD = 50;
    AccomodationsService accomodationsService = new AccomodationsService();
    public static AccommodationsPageFragment newInstance() {
        return new AccommodationsPageFragment();
    }

    public AccommodationsPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void prepareProductList(){
        accomodationsService.loadAccommodations();
        accomodationsService.getAccommodations().observe(getActivity(), new Observer<List<AccommodationDTO>>() {
            @Override
            public void onChanged(List<AccommodationDTO> objects) {
                // Update your UI or perform any actions when LiveData changes

                // Now, you can convert the LiveData to a List if needed
                accommodations = (ArrayList<AccommodationDTO>) objects;
                // Do something with the list
                FragmentTransition.to(AccommodationListFragment.newInstance(accommodations, false), getActivity(), true, R.id.scroll_products_list);
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationsPageViewModel = new ViewModelProvider(this).get(AccommodationsPageViewModel.class);

        binding = FragmentAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        areAmenitiesLoaded = false;

        this.prepareProductList();
        this.setFilterDialog(root);
        this.setCalendarDialog();
        this.handleSearch(root);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.sensorMgr  = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        Sensor mAccelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mGyro = sensorMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        sensorMgr.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorMgr.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setCalendarDialog(){
        Button btnFilters = binding.calendarButton;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "CAlendar Sheet Dialog");
            this.calendarDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            this.calendarView = getLayoutInflater().inflate(R.layout.calendar_page, null);
            calendarDialog.setContentView(this.calendarView);
            calendarDialog.show();
        });
    }

    private void setFilterDialog(View root){
        Button btnFilters = binding.filterButton;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            this.bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            this.dialogView = getLayoutInflater().inflate(R.layout.filter_page, null);
            LinearLayout linaerLayout = dialogView.findViewById(R.id.amenitiesCheckboxes);
            accomodationsService.loadAmenities();
                accomodationsService.getAmenities().observe(getActivity(), new Observer<Collection<Amenity>>() {
                    @Override
                    public void onChanged(Collection<Amenity> objects) {
                        linaerLayout.removeAllViews();
                        areAmenitiesLoaded = true;
                        amenities = (ArrayList<Amenity>) objects;
                        for (int i = 0; i < amenities.size(); i++) {
                            CheckBox cb = new CheckBox(getContext());
                            cb.setText(amenities.get(i).getName());
                            cb.setId(Math.toIntExact(amenities.get(i).getId()));
                            linaerLayout.addView(cb);
                        }
                        bottomSheetDialog.setContentView(dialogView);
                        bottomSheetDialog.show();
                    }
                });
        });
    }
    private List<Long> getSelectedCheckboxes() {
        List<Long> selectedCheckboxes = new ArrayList<>();

        // Get the layout where you added checkboxes
        LinearLayout checkboxContainer = dialogView.findViewById(R.id.amenitiesCheckboxes);

        // Iterate through child views to check which checkboxes are selected
        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            View view = checkboxContainer.getChildAt(i);

            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;

                // Check if the checkbox is selected
                if (checkBox.isChecked()) {
                    // Add the text or any identifier associated with the checkbox to the list
                    selectedCheckboxes.add(Long.valueOf(checkBox.getId()));
                }
            }
        }

        return selectedCheckboxes;
    }
    private void handleSearch(View rootView){
        Button search = rootView.findViewById(R.id.searchButton);
        EditText city = rootView.findViewById(R.id.adress);
        EditText peopleNum = rootView.findViewById(R.id.peopleNumber);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(peopleNum.getText().toString().equals("") || city.getText().toString().equals("") || calendarView == null){
                    Toast.makeText(getContext(), "You haven't entered necessary data", Toast.LENGTH_SHORT).show();
                    return;
                }
                calendar = calendarView.findViewById(R.id.searchCalendar);

                if(calendar.getSelectedDates().size() < 2) {
                    Toast.makeText(getContext(), "You haven't selected dates", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CalendarDay> selectedDays = calendar.getSelectedDates();

                Double max = null;
                Double min = null;
                List<Long> amenities = null;
                String type = null;
                if(dialogView != null){
                    EditText minEdit = dialogView.findViewById(R.id.minPrice);
                    EditText maxEdit = dialogView.findViewById(R.id.maxPrice);

                    if(!minEdit.getText().toString().isEmpty()) {
                        min = Double.valueOf(minEdit.getText().toString());
                    }
                    if(!maxEdit.getText().toString().isEmpty()){
                        max = Double.valueOf(maxEdit.getText().toString());
                    }
                    RadioGroup radioGroup = dialogView.findViewById(R.id.radio);
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton radioButton = dialogView.findViewById(selectedId);
                    if(radioButton != null){
                        type = radioButton.getText().toString();
                    }
                    amenities = getSelectedCheckboxes();
                    if(amenities.size() == 0){
                        amenities = null;
                    }
                }
                Log.i("search",peopleNum.getText().toString()+city.getText().toString()+ Helper.toDate(selectedDays.get(0))+Helper.toDate(selectedDays.get(selectedDays.size()-1)));

                accomodationsService.getSearchedAndFIltered(city.getText().toString(),
                        Helper.toDate(selectedDays.get(0)), Helper.toDate(selectedDays.get(selectedDays.size()-1)), Integer.valueOf(peopleNum.getText().toString()),
                        min, max, amenities, type);
                accomodationsService.getSearchedAccommodations().observe(getActivity(), new Observer<Collection<SearchAccommodationDTO>>() {
                    @Override
                    public void onChanged(Collection<SearchAccommodationDTO> objects) {
                        // Update your UI or perform any actions when LiveData changes

                        // Now, you can convert the LiveData to a List if needed
                        searchAccommodationDTOS = (ArrayList<SearchAccommodationDTO>) objects;
                        // Do something with the list
                        FragmentTransition.to(SearchedAccommodationListFragment.newInstance(searchAccommodationDTOS), getActivity(), true, R.id.scroll_products_list);

                    }
                });

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d("sensor", "changeDetected");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 1000) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    Toast.makeText(this.getActivity().getApplicationContext(), "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    Log.d("sensor", "changeDetected");
                    Log.d("sensor", String.valueOf(event.values[0]));

            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 1000) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[0];

                float speed = Math.abs(x - last_gyro_x);

                if (speed > 1.1) {
                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    Toast.makeText(this.getActivity().getApplicationContext(), "gyro detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                    this.calendarDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
                    this.calendarView = getLayoutInflater().inflate(R.layout.calendar_page, null);
                    calendarDialog.setContentView(this.calendarView);
                    calendarDialog.show();
                }
                last_gyro_x = x;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}