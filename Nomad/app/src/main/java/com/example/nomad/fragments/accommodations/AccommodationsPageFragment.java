package com.example.nomad.fragments.accommodations;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
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
public class AccommodationsPageFragment extends Fragment {
    public static ArrayList<AccommodationDTO> accommodations;
    public static ArrayList<Amenity> amenities;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog calendarDialog;
    View dialogView;
    View calendarView;
    boolean areAmenitiesLoaded;
    public static ArrayList<SearchAccommodationDTO> searchAccommodationDTOS;
    private MaterialCalendarView calendar;
    private AccommodationsPageViewModel accommodationsPageViewModel;
    private FragmentAccommodationsPageBinding binding;
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
                FragmentTransition.to(AccommodationListFragment.newInstance(accommodations), getActivity(), true, R.id.scroll_products_list);

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
            if(!areAmenitiesLoaded){
                accomodationsService.getAmenities().observe(getActivity(), new Observer<Collection<Amenity>>() {
                    @Override
                    public void onChanged(Collection<Amenity> objects) {
                        // Update your UI or perform any actions when LiveData changes
                        // Now, you can convert the LiveData to a List if needed
                        areAmenitiesLoaded = true;
                        amenities = (ArrayList<Amenity>) objects;
                        for (int i = 0; i < amenities.size(); i++) {
                            CheckBox cb = new CheckBox(getContext());
                            cb.setText(amenities.get(i).getName());
                            linaerLayout.addView(cb);
                        }
                        bottomSheetDialog.setContentView(dialogView);
                        bottomSheetDialog.show();

                    }
                });
            }else{
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
            }


        });
    }
    private void handleSearch(View rootView){
        Button search = rootView.findViewById(R.id.searchButton);
        EditText city = rootView.findViewById(R.id.adress);
        EditText peopleNum = rootView.findViewById(R.id.peopleNumber);
//dodati proveru da li su uneti datumi
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = calendarView.findViewById(R.id.searchCalendar);
                List<CalendarDay> selectedDays = calendar.getSelectedDates();

                Double max = null;
                Double min = null;
                List<Long> amenities = null;
                String type = null;
                if(dialogView != null){
                    EditText minEdit = dialogView.findViewById(R.id.minPrice);
                    EditText maxEdit = dialogView.findViewById(R.id.maxPrice);

                    if(!minEdit.getText().toString().isEmpty() && !maxEdit.getText().toString().isEmpty()) {
                        max = Double.valueOf(maxEdit.getText().toString());
                        min = Double.valueOf(minEdit.getText().toString());
                    }
                    RadioGroup radioGroup = dialogView.findViewById(R.id.radio);
                    int selectedId = radioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton radioButton = dialogView.findViewById(selectedId);
                    if(radioButton != null){
                        type = radioButton.getText().toString();
                    }
                }
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
}