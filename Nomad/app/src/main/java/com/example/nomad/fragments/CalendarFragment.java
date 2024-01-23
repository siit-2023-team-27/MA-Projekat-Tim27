package com.example.nomad.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.DateRange;
import com.example.nomad.dto.ReservationDateDTO;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.ImageService;
import com.example.nomad.services.LocationService;
import com.example.nomad.utils.CustomDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText priceBox;
    private EditText defaultPriceBox;
    private Button setUnavailableButton;
    private Button setPriceButton;
    private Button createButton;
    private MaterialCalendarView calendar;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private HashMap<Date, ReservationDateDTO> dates = new HashMap<Date, ReservationDateDTO>();
    private ArrayList<DateRange> dateRanges = new ArrayList<DateRange>();
    private HashMap<DateRange, Double> prices = new HashMap<DateRange, Double>();

    private AccommodationDTO accommodation;
    private boolean isEdit;
    public void setIsEdit(boolean isEdit) {this.isEdit = isEdit;}

    public CalendarFragment() {
        // Required empty public constructor
    }
    public CalendarFragment(AccommodationDTO accommodationDTO){
    }

    public HashMap<Date, ReservationDateDTO> getDates() {
        return dates;
    }

    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationDTO accommodation) {
        Log.d("setAccommodation: ", accommodation.toString());
        this.accommodation = accommodation;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initElements(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean setUnavailable() {
        if(calendar.getSelectedDates() == null){
            return false;
        }
        List<CalendarDay> dates = calendar.getSelectedDates();
        for (CalendarDay date : dates){
            Date d = new Date(date.getYear(), date.getMonth(), date.getDay());
            new Date();
            if(this.dates.containsKey(d)){
                this.dates.get(d).setTaken(true);
            }else{
                this.dates.put(d, new ReservationDateDTO(d, true));
            }
        }
        DateRange range = new DateRange(new Date(dates.get(0).getYear()-1900, dates.get(0).getMonth()-1, dates.get(0).getDay()),
                new Date(dates.get(dates.size()-1).getYear()-1900, dates.get(dates.size()-1).getMonth()-1, dates.get(dates.size()-1).getDay()));
        Log.d("SET UNAVAILABLE", range.toString());
        Log.d("SET UNAVAILABLE", accommodation.toString());
        dateRanges.add(range);
        CalendarFragment self = this;
        calendar.addDecorators(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                Date dayDate = new Date(day.getYear(), day.getMonth(), day.getDay());
                return self.getDates().values().stream().filter(x -> x.isTaken()).map(x -> x.getDate()).collect(Collectors.toList()).contains(dayDate);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(self.getResources().getDrawable(R.drawable.gradientsplash));
            }
        });
        return true;
    }
    private boolean setPrice(){
        if(calendar.getSelectedDates() == null || this.priceBox.getText().toString() == ""){
            return false;
        }
        List<CalendarDay> dates = calendar.getSelectedDates();
        for (CalendarDay date : dates){
            Date d = new Date(date.getYear(), date.getMonth(), date.getDay());
            double price = Double.valueOf(this.priceBox.getText().toString());
            calendar.addDecorators(new CustomDecorator(date, String.valueOf(price), Color.RED));

            if(this.dates.containsKey(d)){
                this.dates.get(d).setPrice(price);
            }else{
                this.dates.put(d, new ReservationDateDTO(d, false, price));
            }

        }
        DateRange range = new DateRange(new Date(dates.get(0).getYear()-1900, dates.get(0).getMonth()-1, dates.get(0).getDay()),
                new Date(dates.get(dates.size()-1).getYear()-1900, dates.get(dates.size()-1).getMonth()-1, dates.get(dates.size()-1).getDay()));
        prices.put(range,  Double.valueOf(this.priceBox.getText().toString()));
        return true;
    }
    private void initElements(View view){
        calendar = view.findViewById(R.id.materialCalendarView);
//        setUnavailableButton = view.findViewById(R.id.SetUnavailableButton);
//        setPriceButton = view.findViewById(R.id.SetPriceButton);
        createButton = view.findViewById(R.id.CreateAccommodationButton);
        setPriceButton = view.findViewById(R.id.setSpecialPriceButton);
        setUnavailableButton = view.findViewById(R.id.setUnavailableRangeButton);
        priceBox = view.findViewById(R.id.setSpecialPriceBox);
        defaultPriceBox = view.findViewById(R.id.setDefaultPriceBox);

        setUnavailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!setUnavailable()){
                    Toast.makeText(v.getContext(), "Please select dates", Toast.LENGTH_SHORT);
                }
            }
        });
        setPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!setPrice()){
                    Toast.makeText(v.getContext(), "Please select dates", Toast.LENGTH_SHORT);
                }
            }
        });
        FragmentManager fm = getActivity()
                .getSupportFragmentManager();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accommodation.setImages(ImageService.paths);
                accommodation.setVerified(false);
                accommodation.setDefaultPrice(Double.valueOf(defaultPriceBox.getText().toString()));
                accommodation.setAddress(LocationService.address);

                if(isEdit) {
                    accomodationsService.update(accommodation);
                }else{
                    accomodationsService.create(accommodation, dateRanges, prices);
                }

                ImageService.paths = new ArrayList<>();
                fm.popBackStack();

            }
        });

        defaultPriceBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(validate());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        setPriceButton.setEnabled(false);
        priceBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                setPriceButton.setEnabled(validateSetPrice());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                setPriceButton.setEnabled(validateSetPrice());
            }
        });

        createButton.setEnabled(false);
        if(isEdit) {
            defaultPriceBox.setText(Double.toString(accommodation.getDefaultPrice()));
            createButton.setEnabled(true);
            createButton.setText("Update");
        }

    }

    private boolean validateSetPrice() {
        return !priceBox.getText().toString().isEmpty() && !calendar.getSelectedDates().isEmpty();
    }

    private boolean validate(){
        return !defaultPriceBox.getText().toString().isEmpty();
    }
}