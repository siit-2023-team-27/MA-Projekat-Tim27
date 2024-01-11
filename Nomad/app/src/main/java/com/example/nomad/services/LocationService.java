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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationService {
    static String address = "";
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

        return address;
    }
    private static String addressToString(Address address){
        String str = "";
        str += address.getAddressLine(0).toString() + " ";
        str += address.getAddressLine(1).toString() + " ";
        str += address.getAddressLine(2).toString() + " ";
        str += address.getAddressLine(3).toString() + " ";
        return str;
    }
}
