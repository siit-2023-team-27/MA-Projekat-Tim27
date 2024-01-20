package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.nomad.dto.ReservationDateDTO;
import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;
import com.example.nomad.services.AuthService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.dto.DateRange;
import com.example.nomad.enums.AccommodationType;
import com.example.nomad.services.AccommodationService;
import com.puskal.multiselectspinner.MultiSelectSpinnerView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private static final String ACCOMMODATION = "accommodation";

    private EditText name;
    private EditText description;
    private EditText minGuests;
    private EditText maxGuests;
    private MultiSelectSpinnerView multiSpinner;
    private AccommodationService accommodationService;
    private ArrayList<Amenity> amenities;
    private ArrayList<Amenity> selectedAmenities = new ArrayList<>();
    private AccommodationDTO accommodation;
    ArrayAdapter<CharSequence> adapterAccommodationType;
    private Spinner spinner;
//    private Button setUnavailableButton;
    private MaterialCalendarView calendar;
//    private Button setPriceButton;
    private Button createButton;
    private HashMap<Date, ReservationDateDTO> dates = new HashMap<Date, ReservationDateDTO>();
    private ArrayList<DateRange> dateRanges = new ArrayList<DateRange>();
    private HashMap<DateRange, Double> prices = new HashMap<DateRange, Double>();
    private Switch reservationAcceptanceSwitch;

//    private MaterialCalendarView calendarView;
    public CreateAccommodationFragment() throws MalformedURLException, ExecutionException, InterruptedException, TimeoutException {
        this.accommodationService = new AccommodationService();

    }

    public static CreateAccommodationFragment newInstance() throws MalformedURLException, ExecutionException, InterruptedException, TimeoutException {
        Log.d("BUG", "new instance od Create bez parametara");
        CreateAccommodationFragment fragment = new CreateAccommodationFragment();
        return fragment;
    }

    public static CreateAccommodationFragment newInstance(AccommodationDTO accommodation) throws MalformedURLException, ExecutionException, InterruptedException, TimeoutException {
        CreateAccommodationFragment fragment = new CreateAccommodationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ACCOMMODATION, accommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodation = getArguments().getParcelable(ACCOMMODATION);
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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAccommodation(v);
                ImageFragment imageFragment = ImageFragment.newInstance();
                imageFragment.setAccommodation(accommodation);
                FragmentTransition.to(imageFragment, getActivity(), true, R.id.accommodationCreationHostView);

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

    public void setupSpinner(View view){
        spinner = view.findViewById(R.id.spinner);

        adapterAccommodationType = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.accommodation_types_array,
                android.R.layout.simple_spinner_item
        );
        adapterAccommodationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterAccommodationType);
    }
    public void generateAccommodation(View view){
        this.accommodation = new AccommodationDTO();
        this.accommodation.setStatus(AccommodationStatus.PENDING);
        this.accommodation.setAccommodationType(AccommodationType.valueOf((String)spinner.getSelectedItem()));

        this.accommodation.setMinGuests(Integer.valueOf(minGuests.getText().toString()));
        this.accommodation.setMaxGuests(Integer.valueOf(maxGuests.getText().toString()));
        this.accommodation.setName(name.getText().toString());
        this.accommodation.setDescription(description.getText().toString());
        this.accommodation.setAmenities(selectedAmenities);
        this.accommodation.setVerified(true);
        this.accommodation.setPriceType(PriceType.FOR_ACCOMMODATION);
        this.accommodation.setHostId(AuthService.id);


        //TODO: Try without after connecting image and location fragments
        this.accommodation.setImages(new ArrayList<String>());
        this.accommodation.setAddress("AAAAAAAAAAAA");
        this.accommodation.setPriceType(PriceType.FOR_ACCOMMODATION);

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
//        setUnavailableButton = view.findViewById(R.id.SetUnavailableButton);
//        setPriceButton = view.findViewById(R.id.SetPriceButton);
        createButton = view.findViewById(R.id.nextButton);
        reservationAcceptanceSwitch = view.findViewById(R.id.reservationAcceptanceTypeSwitch);
        setUpValidation();

        setuUpAccommodation();
    }

    private void setuUpAccommodation() {
        if(accommodation != null ) {
            name.setText(accommodation.getName());
            description.setText(accommodation.getDescription());
            minGuests.setText(accommodation.getMinGuests()+"");
            maxGuests.setText(accommodation.getMaxGuests()+"");
            if(accommodation.getConfirmationType() == ConfirmationType.AUTOMATIC) { reservationAcceptanceSwitch.setChecked(true); }
            spinner.setSelection(adapterAccommodationType.getPosition("STUDIO"));
            Log.d("accommodation type", accommodation.getAccommodationType().toString());
            Log.d("accommodation type", accommodation.getAccommodationType() + "");
        }
    }
    private void setUpValidation(){
        createButton.setEnabled(validate());

        minGuests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(validate());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        maxGuests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                createButton.setEnabled(validate());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                createButton.setEnabled(validate());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                createButton.setEnabled(validate());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private boolean validate(){
        return !description.getText().toString().isEmpty() && !name.getText().toString().isEmpty()
                && !minGuests.getText().toString().isEmpty() && !maxGuests.getText().toString().isEmpty()
                && (Integer.valueOf(minGuests.getText().toString())) <= Integer.valueOf(maxGuests.getText().toString()) ;
    }

}