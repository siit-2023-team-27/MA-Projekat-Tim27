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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationDTO> {

    private ArrayList<NotificationDTO> notifications;
    private ArrayList<NotificationDTO> allNotifications;
    private FragmentActivity activity;

    public NotificationAdapter(Context context, ArrayList<NotificationDTO> notifications, FragmentActivity activity){
        super(context, R.layout.accommodation_card, notifications);
        this.notifications = notifications;
        this.allNotifications = notifications;
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            String notificationDate = format.format(notification.getDate());
            date.setText(notificationDate);
        }

        return convertView;
    }

    public void filterNotifications(Date start, Date finish) {
        ArrayList<NotificationDTO> filteredNotifications = new ArrayList<>();


        for (NotificationDTO notification : allNotifications) {
            Date notificationDate = new Date(notification.getDate());

            if (!notificationDate.before(start) && !notificationDate.after(finish)) {
                filteredNotifications.add(notification);
            }
        }

        // Replace the original 'notifications' list with the filtered list
        notifications = filteredNotifications;
        notifyDataSetChanged();

    }

}
