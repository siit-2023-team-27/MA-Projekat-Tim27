package com.example.nomad.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class AccommodationRating implements Parcelable {
    private String userName;
    private String text;
    private int rating;

    public AccommodationRating(String userName, String text, int rating) {
        this.userName = userName;
        this.text = text;
        this.rating = rating;
    }

    protected AccommodationRating(Parcel in) {
        userName = in.readString();
        text = in.readString();
        rating = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(text);
        dest.writeInt(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccommodationRating> CREATOR = new Creator<AccommodationRating>() {
        @Override
        public AccommodationRating createFromParcel(Parcel in) {
            return new AccommodationRating(in);
        }

        @Override
        public AccommodationRating[] newArray(int size) {
            return new AccommodationRating[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
