package com.example.nomad.fragments;

import android.content.Context;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.caverock.androidsvg.BuildConfig;
import com.example.nomad.R;
import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.services.LocationService;


import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


//import org.osmdroid.api.IGeoPoint;
//import org.osmdroid.api.IMapController;
//import org.osmdroid.config.Configuration;
//import org.osmdroid.events.MapEventsReceiver;
//import org.osmdroid.events.MapListener;
//import org.osmdroid.events.ScrollEvent;
//import org.osmdroid.events.ZoomEvent;
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;
//import org.osmdroid.views.overlay.MapEventsOverlay;
//import org.osmdroid.views.overlay.Marker;
//import org.osmdroid.views.overlay.Overlay;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationLocationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MapView map;
    private AccommodationDTO accommodation;
    private Button nextButton;
    private EditText selectedLocation;

    List<Marker> markers = new ArrayList<>();




    public AccommodationLocationFragment(AccommodationDTO accommodation) {
        super(R.layout.fragment_accommodation_location);
        this.accommodation = accommodation;

    }

    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationDTO accommodation) {
        this.accommodation = accommodation;
        Log.i("ADRESA", "set Ac: " +  accommodation.getAddress());
    }

    public AccommodationLocationFragment() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccommodationLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccommodationLocationFragment newInstance(String param1, String param2) {
        AccommodationLocationFragment fragment = new AccommodationLocationFragment();
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

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accommodation_location, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //inflate and create the map

//        getActivity().setContentView(R.layout.fragment_accommodation_location);
//        getActivity().setContentView(R.layout.activity_main);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        nextButton = (Button)view.findViewById(R.id.NextButton);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarFragment calendarFragment = CalendarFragment.newInstance("t", "t");
                calendarFragment.setAccommodation(accommodation);
                FragmentTransition.to(calendarFragment, getActivity(), true, R.id.accommodationCreationHostView);
            }
        });
        selectedLocation = view.findViewById(R.id.editTextSelectedLocation);

        map = view.findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.BASE_OVERLAY_NL);
        map.setBuiltInZoomControls(true);
//        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(51496994, -134733);
        mapController.setCenter(startPoint);
//        map.setMinZoomLevel(2d);


        setLocation();

        MapEventsReceiver mReceive = new MapEventsReceiver() {

            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                removeMarkers();
                Marker startMarker = new Marker(map);
                startMarker.setPosition(p);
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                if(map.getOverlays().size() > 1){
//                    map.getOverlays().remove(1);
//                }else{
//                    nextButton.setEnabled(true);
//                }
                map.getOverlays().add(startMarker);
                markers.add(startMarker);
                selectedLocation.setText(LocationService.getAddress(p));
                nextButton.setEnabled(true);

                Log.d( "singleTapConfirmedHelper: ", "singleTapConfirmedHelper: ");
                Log.d( "singleTapConfirmedHelper: ", String.valueOf(map.getOverlays().size()));
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                Log.d( "singleTapConfirmedHelper: ", "singleTapConfirmedHelper: ");

                return false;
            }


        };

        MapEventsOverlay overlay = new MapEventsOverlay(getContext(), mReceive);
        map.getOverlays().add(overlay);

    }
    public void click(View view){
        map.invalidate();
        Log.d("TAG", map.getMapCenter(new GeoPoint(20, 20)).toString());
        Log.d("TAG", "MAPCLICK");
        map.setExpectedCenter(new GeoPoint(20, 20));
        GeoPoint point = new GeoPoint(map.getMapCenter());
        point.setCoords(map.getMapCenter().getLatitude(), map.getMapCenter().getLongitude());
        Log.d("TAG", point.toString());

        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        removeMarkers();
        map.getOverlays().add(startMarker);
        markers.add(startMarker);
        map.invalidate();
        //selectedLocation.setText();
    }
    public void nextFragment(){

        CalendarFragment calendarFragment = CalendarFragment.newInstance("", "");
        calendarFragment.setAccommodation(accommodation);
        FragmentTransition.to(calendarFragment, getActivity(), true, R.id.accommodationCreationHostView);

    }

    public void setLocation() {
        Log.i("ADRESA", "set location: " +  accommodation.getAddress());
        if (accommodation != null ){
            selectedLocation.setText(accommodation.getAddress());
            nextButton.setEnabled(true);
            List<Address> addresses = LocationService.getAddressesFromLocationName(accommodation.getAddress(), 1);
            if(addresses != null) {
                if(addresses.size() > 0 ){
                    Log.e("LOCATION", addresses.get(0).getCountryName());
                    Log.e("LOCATION", addresses.get(0).getLatitude() + " " + addresses.get(0).getLongitude());
                    GeoPoint point = new GeoPoint(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    map.getController().setZoom(15);
                    map.getController().setCenter(point);
                    removeMarkers();
                    Marker startMarker = new Marker(map);
                    startMarker.setPosition(point);
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    map.getOverlays().add(startMarker);
                    markers.add(startMarker);
                    map.invalidate();
                }
            }

        }
    }

    private void removeMarkers() {
        for (Marker marker : markers) {
            map.getOverlays().remove(marker);
        }
        markers.clear();
    }
}