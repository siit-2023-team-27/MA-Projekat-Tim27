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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nomad.R;
import com.example.nomad.databinding.FragmentAccommodationsPageBinding;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationsPageFragment extends Fragment {
    public static ArrayList<AccommodationDTO> accommodations;
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

        prepareProductList();

        SearchView searchView = binding.searchText;
        accommodationsPageViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

//        Button btnFilters = binding.btnFilters;
//        btnFilters.setOnClickListener(v -> {
//            Log.i("ShopApp", "Bottom Sheet Dialog");
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
//            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
//            bottomSheetDialog.setContentView(dialogView);
//            bottomSheetDialog.show();
//        });
//
//        Spinner spinner = binding.btnSort;
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.sort_array));
//        // Specify the layout to use when the list of choices appears
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(arrayAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setMessage("Change the sort option?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Log.v("ShopApp", (String) parent.getItemAtPosition(position));
//                                ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = dialog.create();
//                alert.show();
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });

        return root;
    }
}