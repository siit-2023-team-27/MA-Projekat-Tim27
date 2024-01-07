package com.example.nomad.fragments.accommodations;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.model.AccommodationDTO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationsPageFragment extends Fragment {
    public static ArrayList<AccommodationDTO> accommodations = new ArrayList<AccommodationDTO>();
    private AccommodationsPageViewModel accommodationsPageViewModel;
    private FragmentAccommodationsPageBinding binding;
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
    private void prepareProductList(ArrayList<AccommodationDTO> products){
        products.add(new AccommodationDTO(1L, 1, 3,"prvi","Samsung S23 Ultra White", "Description 1"));
        products.add(new AccommodationDTO(2L, 1, 3,"drugi","Samsung S23 Ultra White", "Description 1"));
        products.add(new AccommodationDTO(3L, 1, 3,"treci","Samsung S23 Ultra White", "Description 1"));
        products.add(new AccommodationDTO(4L, 1, 3,"cetvrti","Samsung S23 Ultra White", "Description 1"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accommodationsPageViewModel = new ViewModelProvider(this).get(AccommodationsPageViewModel.class);

        binding = FragmentAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(accommodations);

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


        FragmentTransition.to(AccommodationListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_products_list);

        return root;
    }
}