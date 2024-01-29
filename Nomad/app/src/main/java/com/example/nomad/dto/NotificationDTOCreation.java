package com.example.nomad.dto;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.nomad.enums.NotificationType;

import java.util.Date;

public class NotificationDTOCreation {

    private String text;
    private String title;
    private String targetAppUser;
    private Long date;
    private Date dateD;
    private NotificationType notificationType;

    public NotificationDTOCreation(String text, String title, String targetAppUser, Long date, NotificationType notificationType) {
        this.text = text;
        this.title = title;
        this.targetAppUser = targetAppUser;
        this.date = date;
        this.notificationType = notificationType;
    }
    public NotificationDTOCreation(){}

    protected NotificationDTOCreation(Parcel in) {
        text = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            targetAppUser = null;
        } else {
            targetAppUser = in.readString();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetAppUser() {
        return targetAppUser;
    }

    public void setTargetAppUser(String targetAppUser) {
        this.targetAppUser = targetAppUser;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

}
