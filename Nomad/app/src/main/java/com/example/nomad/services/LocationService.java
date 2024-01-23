package com.example.nomad.services;

import android.location.Address;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationService {
    public static String address = "";
    public static String getAddress(GeoPoint p){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                GeocoderNominatim geocoder = new GeocoderNominatim(Configuration.getInstance().getNormalizedUserAgent());
                try {
                    address = addressToString(geocoder.getFromLocation(p.getLatitude(), p.getLongitude(), 1).get(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Log.d("singleTapConfirmedHelper: ", address);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                    }
                });
            }
        });

        // Wait for the background task to complete
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait
        }

        return address;
    }
    private static String addressToString(Address address){
        String str = "";
        for (int i=0; i<=address.getMaxAddressLineIndex(); i++) {
            str += address.getAddressLine(i).toString() ;
            if(i != address.getMaxAddressLineIndex()){
                str += " ";
            }
        }

        return str;
    }


    public static List<Address> getAddressesFromLocationName(String locationName, int maxResults) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        address = locationName;

        // Define a list to store the resulting addresses
        final List<Address>[] addressList = new List[]{null};
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                GeocoderNominatim geocoder = new GeocoderNominatim(Configuration.getInstance().getNormalizedUserAgent());
                try {
                    // Use the getFromLocationName method to retrieve addresses
                    addressList[0] = geocoder.getFromLocationName(locationName, maxResults);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI Thread work here
                    }
                });
            }
        });

        // Wait for the background task to complete
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait
        }

        return addressList[0];
    }
}
