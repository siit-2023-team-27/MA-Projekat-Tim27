package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.NotificationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.services.NotificationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationDTO> {

    private ArrayList<NotificationDTO> notifications;
    private FragmentActivity activity;

    public NotificationAdapter(Context context, ArrayList<NotificationDTO> notifications, FragmentActivity activity){
        super(context, R.layout.accommodation_card, notifications);
        this.notifications = notifications;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Nullable
    @Override
    public NotificationDTO getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NotificationDTO notification = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card,
                    parent, false);
        }

        TextView title = convertView.findViewById(R.id.notification_title);
        TextView text = convertView.findViewById(R.id.notification_text);
        TextView date = convertView.findViewById(R.id.notification_date);

        if(notification != null) {
            title.setText(notification.getTitle());
            text.setText(notification.getText());
            date.setText(notification.getDate().toString());
        }

        return convertView;
    }

    public void filterNotifications(Date start, Date finish) {
        ArrayList<NotificationDTO> filteredNotifications = new ArrayList<>();

        // Iterate through the existing notifications
        for (NotificationDTO notification : notifications) {
            Date notificationDate = new Date(notification.getDate());

            // Check if the notification date is within the specified date range
            if (notificationDate != null && !notificationDate.before(start) && !notificationDate.after(finish)) {
                // Add the notification to the filtered list if it's within the range
                filteredNotifications.add(notification);
            }
        }

        // Replace the original 'notifications' list with the filtered list
        notifications = filteredNotifications;
        notifyDataSetChanged();

    }

}
