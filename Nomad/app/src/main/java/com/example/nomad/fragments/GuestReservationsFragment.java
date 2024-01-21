//package com.example.nomad.fragments;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.ListFragment;
//import androidx.lifecycle.Observer;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.nomad.R;
//import com.example.nomad.adapters.GuestReservationsAdapter;
//import com.example.nomad.adapters.HostAccommodationsAdapter;
//import com.example.nomad.databinding.FragmentGuestReservationsBinding;
//import com.example.nomad.databinding.FragmentHostListingBinding;
//import com.example.nomad.dto.AccommodationDTO;
//import com.example.nomad.dto.ReservationDTO;
//import com.example.nomad.services.AuthService;
//import com.example.nomad.services.ReservationService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GuestReservationsFragment extends ListFragment {
//
//    ReservationService reservationService = new ReservationService();
//
//    ArrayList<ReservationDTO> reservations = new ArrayList<ReservationDTO>();
//
//    GuestReservationsAdapter adapter;
//
//    FragmentGuestReservationsBinding binding;
//
//    public GuestReservationsFragment() {
//        // Required empty public constructor
//    }
//
//    public static GuestReservationsFragment newInstance(String param1, String param2) {
//        GuestReservationsFragment fragment = new GuestReservationsFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = FragmentGuestReservationsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        prepareData();
//
//        return root;
//    }
//
//    public void prepareData() {
//        reservationService.getReservationsForGuest(AuthService.id);
//        reservationService.getGuestReservations().observe(getActivity(), new Observer<List<ReservationDTO>>() {
//            @Override
//            public void onChanged(List<ReservationDTO> objects) {
//                reservations = (ArrayList<ReservationDTO>) objects;
//                adapter = new GuestReservationsAdapter(getActivity(), reservations, getActivity());
//                setListAdapter(adapter);
//            }
//        });
//    }
//}