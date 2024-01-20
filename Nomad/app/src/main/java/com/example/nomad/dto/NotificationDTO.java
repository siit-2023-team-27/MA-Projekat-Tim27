package com.example.nomad.dto;


import java.util.Date;

public class NotificationDTO {
    private String text;
    private String title;
    private String targetAppUser;
    private Date date;
    private String notificationType;

    public NotificationDTO(String text, String title, String targetAppUser, Date date, String notificationType) {
        this.text = text;
        this.title = title;
        this.targetAppUser = targetAppUser;
        this.date = date;
        this.notificationType = notificationType;
    }
    public NotificationDTO(){}
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
