package com.example.nomad.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.nomad.activities.HomeActivity;
import com.example.nomad.dto.NotificationDTO;
import com.example.nomad.helper.Consts;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class NotificationService {
    private HomeActivity activity;
    private NotificationDTO notification;
    private static StompClient stompClient;
    public NotificationService(HomeActivity activity){
        this.activity = activity;
    }
    @SuppressLint("CheckResult")
    public void setUpWebSocket(){




        // ...

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
        notification.setNotificationType(jsonObject.getString("notificationType"));
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
    private void sendNotification(String text, String title, String type, Long targetUser){
        NotificationDTO notificationToSend = new NotificationDTO();
        notificationToSend.setNotificationType(type);
        notificationToSend.setText(text);
        notificationToSend.setTitle(title);
        notificationToSend.setDate(new Date());
        notificationToSend.setTargetAppUser(targetUser.toString());
        String jsonNotification =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create().toJson(notificationToSend);
        stompClient.send("/socket-subscriber/send/message", jsonNotification).subscribe();
    }
}
