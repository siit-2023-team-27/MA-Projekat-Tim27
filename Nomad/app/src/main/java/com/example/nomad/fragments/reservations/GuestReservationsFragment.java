package com.example.nomad.fragments.reservations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.databinding.FragmentGuestReservationsBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.ReservationDTO;
import com.example.nomad.dto.ReservationResponseDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;
import com.example.nomad.fragments.accommodations.AccommodationsPageFragment;
import com.example.nomad.fragments.accommodations.SearchedAccommodationListFragment;
import com.example.nomad.helper.Helper;
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
        this.prepareProductList();
        reservationService.getRefresh().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean objects) {
                Log.i("Reshresh", "refr: ");
                prepareProductList();
                //testirati
            }
        });
        this.setCalendarDialog(root);
        this.handleSearch(root);

        return root;
    }
    private void prepareProductList(){
        reservationService.loadReservations(authService.getId());
        reservationService.getReservations().observe(getActivity(), new Observer<Collection<ReservationResponseDTO>>() {
            @Override
            public void onChanged(Collection<ReservationResponseDTO> objects) {
                // Update your UI or perform any actions when LiveData changes

                // Now, you can convert the LiveData to a List if needed
                reservations = (ArrayList<ReservationResponseDTO>) objects;
                // Do something with the list
                FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations), getActivity(), false, R.id.scroll_guest_reservations);
            }
        });
    }
    private void setCalendarDialog(View rootView){
        Button search = rootView.findViewById(R.id.calendarResButton);
        //ovde prijavljuje null vrv je neka glupost
        search.setOnClickListener(v -> {
            Log.i("ShopApp", "CAlendar Sheet Dialog");
            this.calendarDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            this.calendarView = getLayoutInflater().inflate(R.layout.calendar_page, null);
            calendarDialog.setContentView(this.calendarView);
            calendarDialog.show();
        });
    }
    private void handleSearch(View rootView){
        Button search = rootView.findViewById(R.id.searchButton);
        EditText name = rootView.findViewById(R.id.nameReservation);
//dodati proveru da li su uneti datumi
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = calendarView.findViewById(R.id.searchCalendar);
                List<CalendarDay> selectedDays = calendar.getSelectedDates();

                String type = null;
//                if(dialogView != null){
//                    RadioGroup radioGroup = dialogView.findViewById(R.id.radio);
//                    int selectedId = radioGroup.getCheckedRadioButtonId();
//
//                    // find the radiobutton by returned id
//                    RadioButton radioButton = dialogView.findViewById(selectedId);
//                    if(radioButton != null){
//                        type = radioButton.getText().toString();
//                    }
//                }
                reservationService.getSearchedAndFIlteredGuest(authService.getId(), name.getText().toString(),
                        Helper.toDate(selectedDays.get(0)), Helper.toDate(selectedDays.get(selectedDays.size()-1)), type);
                reservationService.getSearchedReservations().observe(getActivity(), new Observer<Collection<ReservationResponseDTO>>() {
                    @Override
                    public void onChanged(Collection<ReservationResponseDTO> objects) {
                        // Update your UI or perform any actions when LiveData changes

                        // Now, you can convert the LiveData to a List if needed
                        reservations = (ArrayList<ReservationResponseDTO>) objects;
                        // Do something with the list
                        FragmentTransition.to(GuestReservationsListFragment.newInstance(reservations), getActivity(), false, R.id.scroll_products_list);

                    }
                });

            }
        });
    }
}