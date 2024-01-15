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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

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
                FragmentTransition.to(AccommodationListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationsPageViewModel = new ViewModelProvider(this).get(AccommodationsPageViewModel.class);

        binding = FragmentAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.prepareProductList();
        this.handleSearch(root);

        return root;
    }
    private void handleSearch(View rootView){
        Button search = rootView.findViewById(R.id.searchButton);
        EditText city = rootView.findViewById(R.id.adress);
        EditText peopleNum = rootView.findViewById(R.id.peopleNumber);
        this.calendar = rootView.findViewById(R.id.searchCalendar);
        List<CalendarDay> selectedDays = calendar.getSelectedDates();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accomodationsService.getSearchedAndFIltered(city.getText().toString(),
                        Helper.toDate(selectedDays.get(0)), Helper.toDate(selectedDays.get(selectedDays.size()-1)), Integer.valueOf(peopleNum.getText().toString()),
                        null, null, null, null);
                accomodationsService.getSearchedAccommodations().observe(getActivity(), new Observer<Collection<SearchAccommodationDTO>>() {
                    @Override
                    public void onChanged(Collection<SearchAccommodationDTO> objects) {
                        // Update your UI or perform any actions when LiveData changes

                        // Now, you can convert the LiveData to a List if needed
                        searchAccommodationDTOS = (ArrayList<SearchAccommodationDTO>) objects;
                        // Do something with the list
                        FragmentTransition.to(SearchedAccommodationListFragment.newInstance(searchAccommodationDTOS), getActivity(), false, R.id.scroll_products_list);

                    }
                });

            }
        });
    }
}