package com.example.nomad.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.adapters.AccommodationListAdapter;
import com.example.nomad.adapters.HostAccommodationsAdapter;
import com.example.nomad.adapters.NotificationAdapter;
import com.example.nomad.databinding.FragmentAccommodationListBinding;
import com.example.nomad.databinding.FragmentNotificationsBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.NotificationDTO;
import com.example.nomad.enums.NotificationType;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.NotificationService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsFragment extends ListFragment {

    private ArrayList<NotificationDTO> notifications;
    private NotificationAdapter adapter;

    private FragmentNotificationsBinding binding;

    private NotificationService notificationService = new NotificationService();

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareData();
        this.calendarDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
        this.calendarView = getLayoutInflater().inflate(R.layout.calendar_page, null);
        calendarDialog.setContentView(this.calendarView);
        setCalendarDialog();
        handleSearch();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void prepareData() {
//        notifications = new ArrayList<>();
//        notifications.add(new NotificationDTO("title", "text", 3L, new Date(2024, 1, 20), NotificationType.NEW_RATING));
//        notifications.add(new NotificationDTO("title", "text", 3L, new Date(2024, 1, 21), NotificationType.NEW_RATING));
//        notifications.add(new NotificationDTO("title", "text", 3L, new Date(2024, 1, 22), NotificationType.NEW_RATING));
//        adapter = new NotificationAdapter(getActivity(), notifications, getActivity());
//                setListAdapter(adapter);
        notificationService.getAllNotificationsForUser(AuthService.id);
        notificationService.getNotifications().observe(getActivity(), new Observer<List<NotificationDTO>>() {
            @Override
            public void onChanged(List<NotificationDTO> notificationDTOS) {
                notifications = (ArrayList<NotificationDTO>) notificationDTOS;

                adapter = new NotificationAdapter(getActivity(), notifications, getActivity());
                setListAdapter(adapter);
            }
        });
    }

    private MaterialCalendarView calendar;
    private BottomSheetDialog calendarDialog;
    private View calendarView;
    private void setCalendarDialog(){
        Button btnFilters = binding.showCalendarButton;
        btnFilters.setOnClickListener(v -> {
            calendarDialog.show();
        });
    }

    private void handleSearch() {
        calendar = calendarView.findViewById(R.id.searchCalendar);
        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                if(calendar.getSelectedDates().size() == 1) {
                    CalendarDay start = dates.get(0);
                    int year = start.getYear();
                    int month = start.getMonth();
                    int day = start.getDay();
                    Date dateStart = new Date(year, month, day);
                    adapter.filterNotifications(dateStart, dateStart);
                }
                if(calendar.getSelectedDates().size() >= 2) {
                    CalendarDay start = dates.get(0);
                    CalendarDay end = dates.get(dates.size()-1);

                    int year = start.getYear();
                    int month = start.getMonth();
                    int day = start.getDay();

                    Date dateStart = new Date(year, month, day);

                    year = end.getYear();
                    month = end.getMonth();
                    day = end.getDay();

                    Date endDate = new Date(year, month, day);

                    adapter.filterNotifications(dateStart, endDate);
                }

            }
        });

    }
}