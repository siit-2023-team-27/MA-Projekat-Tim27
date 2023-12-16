package com.example.nomad.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginService extends Service {
    URL url = new URL("http://192.168.1.144:8080/auth/login");
    private Future<?> future;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    HttpURLConnection client = null;
    public LoginService() throws MalformedURLException {

    }


    private boolean _login(String username, String password){
        try {

            this.client = (HttpURLConnection) url.openConnection();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            try{
                client.disconnect();
            }catch (Exception e){e.printStackTrace();}
            client.setDoOutput(true);
            client.setRequestMethod("POST");
            client.setConnectTimeout(1000);
            client.addRequestProperty("Content-Type", "application/json");

            Log.d("TAG", "{\"username\": \""  + username + "\", \"password\": \"" + password + "\"}");
            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());


            OutputStreamWriter osw = new OutputStreamWriter(outputPost, "UTF-8");
            osw.write("{\"username\": \""  + username.trim() + "\", \"password\": \"" + password + "\"}");

            osw.flush();
            osw.close();
            outputPost.close();

            return client.getResponseCode() == 200;

        }catch(ProtocolException protocolException){
            Log.d("TAG", "Protocol");
            protocolException.printStackTrace();
            client.disconnect();
        } catch (IOException ioException){
            Log.d("TAG", "IO");
            ioException.printStackTrace();
            client.disconnect();
        }finally{
            client.disconnect();
        }
        return false;
    }
    public boolean login(String username, String password) throws ExecutionException, InterruptedException, TimeoutException {
        if( future == null|| future.isDone()){
            future = executor.submit(() -> {return _login(username, password);});
        }
        return (boolean)future.get(100, TimeUnit.SECONDS);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
}