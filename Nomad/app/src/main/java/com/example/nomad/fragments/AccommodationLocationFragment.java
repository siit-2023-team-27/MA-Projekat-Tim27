package com.example.nomad.fragments;

import android.content.Context;
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

import com.example.nomad.R;
import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.AccommodationDTO;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

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
    private Button pinButton;
    private Button nextButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccommodationLocationFragment(AccommodationDTO accommodation) {
        super(R.layout.fragment_accommodation_location);
        this.accommodation = accommodation;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        //Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        // Configuration.getInstance().setUserAgentValue(getContext().getPackageName());

        Context ctx = getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map

        getActivity().setContentView(R.layout.fragment_accommodation_location);
//        getActivity().setContentView(R.layout.activity_main);




        pinButton = (Button)view.findViewById(R.id.pin_button);
        nextButton = (Button)view.findViewById(R.id.NextButton);
        AccommodationLocationFragment self = this;

        map = view.findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.BASE_OVERLAY_NL);
        map.setBuiltInZoomControls(true);
//        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint startPoint = new GeoPoint(51496994, -134733);
        mapController.setCenter(startPoint);
        mapController.setZoom(1);
//        map.setMinZoomLevel(2d);
        MapEventsReceiver mReceive = new MapEventsReceiver() {

            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Log.d( "singleTapConfirmedHelper: ", "singleTapConfirmedHelper: ");
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                Log.d( "singleTapConfirmedHelper: ", "singleTapConfirmedHelper: ");

                return false;
            }


        };
        MapEventsOverlay overlay = new MapEventsOverlay(getContext(), mReceive);

//        map.getOverlays().add(overlay);
        pinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAA", "onClick: ");
            }
        });


//        map.setMinimumWidth(500);
//        map.setMinimumHeight(500);
//
//        map = (MapView) getView().findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.MAPNIK);
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
        map.getOverlays().add(startMarker);
        map.invalidate();
    }
    public void nextFragment(){
        CalendarFragment calendarFragment = new CalendarFragment(accommodation);
//
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        FragmentTransaction ftt = this.getParentFragmentManager().beginTransaction();
//        ftt.addToBackStack("");
////                ft.replace(self.getParentFragment().getId(), new AccommodationLocationFragment(accommodation));
//        ftt.remove(this);
//        Log.d("A", "onClick: ");
//        ft.add(calendarFragment, "");
//        ft.commit();
//        ftt.commit();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, calendarFragment)
//                .commit();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, calendarFragment)
                .commit();
        fragmentManager.beginTransaction().remove(this);

    }
}