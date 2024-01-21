package com.example.nomad.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.Amenity;
import com.example.nomad.dto.NotificationDTO;
import com.example.nomad.enums.NotificationType;
import com.example.nomad.helper.Consts;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class NotificationService {
    private HomeActivity activity;
    private NotificationDTO notification;
    private static StompClient stompClient;
    public NotificationService(HomeActivity activity){
        this.activity = activity;
    }
    public NotificationService(){
    }
    @SuppressLint("CheckResult")
    public void setUpWebSocket(){

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Consts.WEB_SOCKET_BASE_URL + "/socket");
        stompClient.connect();
        stompClient.topic("/socket-publisher/" + String.valueOf(AuthService.id)).subscribe(topicMessage -> {
            Log.d("MESSAGE", topicMessage.getPayload());
            parseNotification(topicMessage.getPayload());
            showNotification();
        });
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d("OPENED", "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e("ERROR", "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d("CLOSED", "Stomp connection closed");
                    break;
            }
        });
    }
    private void parseNotification(String notificationStr) throws JSONException {
        notification = new NotificationDTO();
        JSONObject jsonObject = new JSONObject(notificationStr);
        notification.setText(jsonObject.getString("text"));
        notification.setTitle(jsonObject.getString("title"));
        notification.setNotificationType(NotificationType.valueOf(jsonObject.getString("notificationType")));
//        notification.setNotificationType(jsonObject.getString("notificationType"));
    }
    private void showNotification() {
        if(notification == null){
            return;
        }
        String CHANNEL_ID = "1";
        int NOTIFICATION_ID = 0;
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android Oreo and above)
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Default Android info icon
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getText())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    public void sendNotification(String text, String title, String type, Long targetUser){
        NotificationDTO notificationToSend = new NotificationDTO();
        notificationToSend.setNotificationType(NotificationType.valueOf(type));
        notificationToSend.setText(text);
        notificationToSend.setTitle(title);
        notificationToSend.setDate(new Date().getTime());
        notificationToSend.setTargetAppUser(targetUser);
        String jsonNotification =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create().toJson(notificationToSend);
        stompClient.send("/socket-subscriber/send/message", jsonNotification).subscribe();
    }

    private MutableLiveData<List<NotificationDTO>> notifications = new MutableLiveData<>();
    public MutableLiveData<List<NotificationDTO>> getNotifications() {
        return this.notifications;
    }

    public void getAllNotificationsForUser(Long userId) {
        Call<ArrayList<NotificationDTO>> call = NotificationClient.getInstance().getMyApi().getNotificationsForUser(userId, "Bearer " + AuthService.token.toString());
        call.enqueue(new Callback<ArrayList<NotificationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationDTO>> call, Response<ArrayList<NotificationDTO>> response) {
                if(response.isSuccessful()){
                    ArrayList<NotificationDTO> objects = response.body();
                    notifications.setValue(objects);
                    Log.i("onResponse", "SUCCESS");
                    Log.d("SIZE: ", String.valueOf(objects.size()));
                }else{
                    Log.e("onResponse", "FAIL");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationDTO>> call, Throwable t) {
                Log.e("OnFailure", t.toString());
                Log.e("OnFailure", t.getLocalizedMessage());

            }
        });
    }
}
