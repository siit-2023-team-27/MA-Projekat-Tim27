package com.example.nomad.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nomad.dto.ReservationDateDTO;
import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;
import com.example.nomad.services.AccommodationClient;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.utils.AddTextToDates;
import com.example.nomad.utils.CustomDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.example.nomad.R;
import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.dto.DateRange;
import com.example.nomad.enums.AccommodationType;
import com.example.nomad.services.AccommodationService;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.CalendarWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.puskal.multiselectspinner.MultiSelectSpinnerView;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import kotlin.Unit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccommodationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText name;
    private EditText description;
    private EditText minGuests;
    private EditText maxGuests;
    private String mParam1;
    private String mParam2;
    private MultiSelectSpinnerView multiSpinner;
    private AccommodationService accommodationService;
    private ArrayList<Amenity> amenities;
    private ArrayList<Amenity> selectedAmenities = new ArrayList<>();
    private AccommodationDTO accommodation;
    private Spinner spinner;
//    private Button setUnavailableButton;
    private MaterialCalendarView calendar;
//    private Button setPriceButton;
    private Button createButton;
    private Button setPriceButton;
    private EditText priceBox;
    private Button setUnavailableButton;
    private AccomodationsService accomodationsService = new AccomodationsService();
    private HashMap<Date, ReservationDateDTO> dates = new HashMap<Date, ReservationDateDTO>();
    private ArrayList<DateRange> dateRanges = new ArrayList<DateRange>();
    private HashMap<DateRange, Double> prices = new HashMap<DateRange, Double>();
    private Switch reservationAcceptanceSwitch;

//    private MaterialCalendarView calendarView;
    public CreateAccommodationFragment() throws MalformedURLException, ExecutionException, InterruptedException, TimeoutException {
        this.accommodationService = new AccommodationService();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccommodationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccommodationFragment newInstance(String param1, String param2) throws MalformedURLException, ExecutionException, InterruptedException, TimeoutException {
        CreateAccommodationFragment fragment = new CreateAccommodationFragment();
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

    public HashMap<Date, ReservationDateDTO> getDates() {
        return dates;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            this.amenities = this.accommodationService.getAmenities();

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        multiSpinner = view.findViewById(R.id.multiSelectSpinner);
        spinner = view.findViewById(R.id.spinner);
        setupSpinner(view);
        initComponents(view);
        ArrayList<String> amenityNames = new ArrayList<>(this.amenities.stream().map(amenity -> {return  amenity.getName();}).collect(Collectors.toList()));
        multiSpinner.buildCheckedSpinner(amenityNames, (selectedPos, s) -> {
            selectedAmenities.clear();
            for (int i: selectedPos){
                selectedAmenities.add(amenities.get(i));
            }
            return Unit.INSTANCE;
        });

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
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(AccommodationLocationFragment.newInstance("t", "t"), getActivity(), true, R.id.accommodationCreationHostView);

//                generateAccommodation(v);
//                accommodation.setImages(new ArrayList<String>());
//                accommodation.setAddress("AAAAAAAAAAAA");
//                accommodation.setPriceType(PriceType.FOR_ACCOMMODATION);
//                accommodation.setDefaultPrice(10);
//                accommodation.setDeadlineForCancellation(10);
//                accommodation.setHostId(AuthService.id);
//                accommodation.setStatus(AccommodationStatus.APPROVED);
//                Log.d("BBBBB", accommodation.toString());
//                Log.d("BBBBB", String.valueOf(AuthService.id));
//                Log.d("BBBBB", AuthService.token.toString());
//                accomodationsService.create(accommodation, dateRanges, prices);
            }
        });
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
        dateRanges.add(range);
        CreateAccommodationFragment self = this;
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
    public void setupSpinner(View view){
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.accommodation_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void generateAccommodation(View view){
        this.accommodation = new AccommodationDTO();
        this.accommodation.setAccommodationType(AccommodationType.valueOf((String)spinner.getSelectedItem()));

        this.accommodation.setMinGuests(Integer.valueOf(minGuests.getText().toString()));
        this.accommodation.setMaxGuests(Integer.valueOf(maxGuests.getText().toString()));
        this.accommodation.setName(name.getText().toString());
        this.accommodation.setDescription(description.getText().toString());
        this.accommodation.setAmenities(selectedAmenities);
        this.accommodation.setVerified(true);
        if(reservationAcceptanceSwitch.isChecked()){
            this.accommodation.setConfirmationType(ConfirmationType.AUTOMATIC);
        }else{
            this.accommodation.setConfirmationType(ConfirmationType.MANUAL);

        }

        Log.d("generateAccommodation: ", String.valueOf(multiSpinner.getSelectedItemPosition()));
        Log.d("generateAccommodation: ", String.valueOf(multiSpinner.getSelectedItemPosition()));
        Log.d("generateAccommodation: ", accommodation.toString());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_accommodation, container, false);
    }

    private void initComponents(View view){
        name = view.findViewById(R.id.editTextAccommodationName);
        description = view.findViewById(R.id.editTextAccommodationDescription);
        minGuests = view.findViewById(R.id.editTextNumberMin);
        maxGuests = view.findViewById(R.id.editTextNumberMax);
        calendar = view.findViewById(R.id.calendarView);
//        setUnavailableButton = view.findViewById(R.id.SetUnavailableButton);
//        setPriceButton = view.findViewById(R.id.SetPriceButton);
        createButton = view.findViewById(R.id.CreateAccommodationButton);
        setPriceButton = view.findViewById(R.id.SetSpecialPriceButton);
        setUnavailableButton = view.findViewById(R.id.setUnavailableButton);
        priceBox = view.findViewById(R.id.specialPriceBox);
        reservationAcceptanceSwitch = view.findViewById(R.id.reservationAcceptanceTypeSwitch);
    }

}