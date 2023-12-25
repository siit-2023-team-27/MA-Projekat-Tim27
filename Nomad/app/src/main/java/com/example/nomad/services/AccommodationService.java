package com.example.nomad.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AccommodationService extends Service {
    URL accommodationsUrl = new URL("http://192.168.1.144:8080/api/accommodations");
    URL amenitiesUrl = new URL("http://192.168.1.144:8080/api/amenities");
    private Future<?> future;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    HttpURLConnection client = null;
    public AccommodationService() throws MalformedURLException {
    }
    public ArrayList<Amenity> getAmenities() throws ExecutionException, InterruptedException, TimeoutException {
        future = executor.submit(() -> {return _getAmenities();});
        ArrayList<Amenity> amenities = (ArrayList<Amenity>) future.get(1000, TimeUnit.SECONDS);
        Log.d("getAmenities: ", amenities.toString());
        return amenities;
    }
    private ArrayList<Amenity> _getAmenities(){
        try {
            this.client = (HttpURLConnection) amenitiesUrl.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            try{
                client.disconnect();
            }catch (Exception e){e.printStackTrace();}
            client.setRequestProperty("Content-Type", "application/json; charset=utf8");
//            client.setDoOutput(true);
            client.setRequestMethod("GET");
            client.setConnectTimeout(1000);
            if(client.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                response.insert(0, "{amenities:");
                response.append("}");

                in.close();
                Log.d("getAmenities: ", response.toString());
                ArrayList<Amenity> amenities = parseAmenities(response.toString());
                Log.d("PARSE AMENITIES", amenities.toString());
                return amenities;
                // print result


            }else{
                Log.d("FAILED", "FAILED");
                Log.d("FAILED", String.valueOf(client.getResponseCode()));
            }

        }catch(ProtocolException protocolException){
            Log.d("TAG", "Protocol");
            protocolException.printStackTrace();
            client.disconnect();
        } catch (IOException ioException){
            Log.d("TAG", "IO");
            ioException.printStackTrace();
            client.disconnect();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally{
            client.disconnect();
        }
        return new ArrayList<>();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private ArrayList<Amenity> parseAmenities(String amenitiesString) throws JSONException {
        ArrayList<Amenity> amenities = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(amenitiesString);
        JSONArray jsonArray = jsonObject.getJSONArray("amenities");

        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject amenityJson = jsonArray.getJSONObject(i);
            Amenity amenity = new Amenity(amenityJson.getString("name"), amenityJson.getString("icon"));
            amenities.add(amenity);
        }
        Log.d("PARSE AMENITIES", amenities.toString());
        return amenities;
    }
}