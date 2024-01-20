package com.example.nomad.fragments.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.databinding.FragmentGuestReservationsBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;
import com.example.nomad.fragments.accommodations.AccommodationsPageFragment;
import com.example.nomad.fragments.accommodations.SearchedAccommodationListFragment;
import com.example.nomad.helper.Helper;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.ReservationService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestReservationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestReservationsFragment extends Fragment {
    public static ArrayList<ReservationResponseDTO> reservations;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private MaterialCalendarView calendar;
    BottomSheetDialog calendarDialog;
    BottomSheetDialog filterDialog;
    AccomodationsService accomodationsService = new AccomodationsService();

    View dialogView;
    View calendarView;
    private FragmentGuestReservationsBinding binding;
    ReservationService reservationService = new ReservationService();
    AuthService authService = new AuthService();

    public static GuestReservationsFragment newInstance() {
        return new GuestReservationsFragment();
    }

    public GuestReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_guest_reservations, container, false);
        this.loadReservations();
        reservationService.getRefreshReservations().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean objects) {
                Log.i("Reshresh", "refr: ");
                loadReservations();
                //testirati
            }
        });
        this.setCalendarDialog(root);
        this.setFilterDialog(root);
        this.handleSearch(root);

        return root;
    }
    private void loadReservations(){
        reservationService.loadReservations(authService.getId(), false);
        reservationService.getReservations().observe(getActivity(), new Observer<Collection<ReservationResponseDTO>>() {
            @Override
            public void onChanged(Collection<ReservationResponseDTO> objects) {
                reservations = new ArrayList<>();
                for (ReservationResponseDTO r: objects) {
                    if(!r.getStatus().equals("PENDING")){
                        reservations.add(r);
                    }
                }
                //FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations, false), getActivity(), false, R.id.scroll_guest_reservations);
                loadAccommodationsAndUsers();
            }
        });
    }
    private void setCalendarDialog(View rootView){
        Button search = rootView.findViewById(R.id.calendarResButton);
        search.setOnClickListener(v -> {
            Log.i("ShopApp", "CAlendar Sheet Dialog");
            this.calendarDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            this.calendarView = getLayoutInflater().inflate(R.layout.calendar_page, null);
            calendarDialog.setContentView(this.calendarView);
            calendarDialog.show();
        });
    }
    private void setFilterDialog(View root){
        Button search = root.findViewById(R.id.filterButton);
        search.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            this.filterDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            this.dialogView = getLayoutInflater().inflate(R.layout.filter_reservations, null);

            filterDialog.setContentView(dialogView);
            filterDialog.show();

        });
    }
    private void handleSearch(View rootView){
        Button search = rootView.findViewById(R.id.searchButton);
        EditText name = rootView.findViewById(R.id.nameReservation);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().equals("") || calendarView == null){
                    Toast.makeText(getContext(), "You haven't entered necessary data", Toast.LENGTH_SHORT).show();
                    return;
                }
                calendar = calendarView.findViewById(R.id.searchCalendar);

                if(calendar.getSelectedDates().size() < 2) {
                    Toast.makeText(getContext(), "You haven't selected dates", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CalendarDay> selectedDays = calendar.getSelectedDates();

                String type = null;
                if(dialogView != null){
                    RadioGroup radioGroup = dialogView.findViewById(R.id.radio);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    Log.i("search",String.valueOf(selectedId));

                    // find the radiobutton by returned id
                    RadioButton radioButton = dialogView.findViewById(selectedId);
                    if(radioButton != null){
                        type = radioButton.getText().toString();
                    }
                }
                reservationService.getSearchedAndFIltered(authService.getId(), name.getText().toString(),
                        Helper.toDate(selectedDays.get(0)), Helper.toDate(selectedDays.get(selectedDays.size()-1)), type, false);
                Log.i("search",name.getText().toString()+ Helper.toDate(selectedDays.get(0))+Helper.toDate(selectedDays.get(selectedDays.size()-1)));

                reservationService.getSearchedReservations().observe(getActivity(), new Observer<Collection<ReservationResponseDTO>>() {
                    @Override
                    public void onChanged(Collection<ReservationResponseDTO> objects) {
                        // Update your UI or perform any actions when LiveData changes
                        reservations = new ArrayList<>();
                        // Now, you can convert the LiveData to a List if needed
                        for (ReservationResponseDTO r: objects) {
                            if(!r.getStatus().equals("PENDING")){
                                reservations.add(r);
                            }
                        }                        // Do something with the list
                        FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations, false), getActivity(), false, R.id.scroll_guest_reservations);

                    }
                });

            }
        });
    }
    private void loadAccommodationsAndUsers(){
        final int[] counter = {0};
        for (ReservationResponseDTO r: reservations) {
            accomodationsService.loadAccommodation(r.getAccommodation());
            accomodationsService.getAccommodation().observe(getActivity(), new Observer<AccommodationDTO>() {
                @Override
                public void onChanged(AccommodationDTO objects) {
                    r.setAccommodationDetails(objects);
                    counter[0] +=1;
                    if(counter[0] == reservations.size()){
                        FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations, false), getActivity(), false, R.id.scroll_guest_reservations);
                    }
                }
            });

        }


    }
}