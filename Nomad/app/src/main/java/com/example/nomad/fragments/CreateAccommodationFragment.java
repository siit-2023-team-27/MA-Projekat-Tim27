package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;
import com.example.nomad.services.AccommodationClient;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
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
import com.puskal.multiselectspinner.MultiSelectSpinnerView;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
//    private Button createButton;
//    private AccomodationsService accomodationsService = new AccomodationsService();

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
//        setUnavailableButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setUnavailable();
//            }
//        });
        Fragment self = this;


//        calendarView = view.findViewById(R.id.calendarView);
//        createButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                generateAccommodation(v);
//                accommodation.setImages(new ArrayList<String>());
//                accommodation.setAddress("AAAAAAAAAAAA");
//                accommodation.setPriceType(PriceType.FOR_ACCOMMODATION);
//                accommodation.setDefaultPrice(10);
//                accommodation.setDeadlineForCancellation(10);
//                accommodation.setConfirmationType(ConfirmationType.AUTOMATIC);
//                accommodation.setHostId(AuthService.id);
//                accommodation.setStatus(AccommodationStatus.APPROVED);
//                Log.d("BBBBB", accommodation.toString());
//                Log.d("BBBBB", String.valueOf(AuthService.id));
//                Log.d("BBBBB", AuthService.token.toString());
//                accomodationsService.create(accommodation);
//            }
//        });
    }

    private void setUnavailable() {
        List<CalendarDay> dates = calendar.getSelectedDates();

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
        this.accommodation.setId(1l);
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
//        createButton = view.findViewById(R.id.CreateAccommodationButton);
    }
}