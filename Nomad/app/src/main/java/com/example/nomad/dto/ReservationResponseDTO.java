package com.example.nomad.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ReservationResponseDTO implements Parcelable {
    private long id;
    private long user;
    private long accommodation;
    private String startDate;
    private String finishDate;
    private int numGuests;
    private String status;
    private UserDTO userDetails;
    private AccommodationDTO accommodationDetails;

    public ReservationResponseDTO(long id, long user, long accommodation, String startDate, String finishDate, int numGuests, String status) {
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.numGuests = numGuests;
        this.status = status;
    }


    protected ReservationResponseDTO(Parcel in) {
        id = in.readLong();
        user = in.readLong();
        accommodation = in.readLong();
        startDate = in.readString();
        finishDate = in.readString();
        numGuests = in.readInt();
        status = in.readString();
        userDetails = in.readParcelable(UserDTO.class.getClassLoader());
        accommodationDetails = in.readParcelable(AccommodationDTO.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(user);
        dest.writeLong(accommodation);
        dest.writeString(startDate);
        dest.writeString(finishDate);
        dest.writeInt(numGuests);
        dest.writeString(status);
        dest.writeParcelable(userDetails, flags);
        dest.writeParcelable(accommodationDetails, flags);
    }

    public static final Creator<ReservationResponseDTO> CREATOR = new Creator<ReservationResponseDTO>() {
        @Override
        public ReservationResponseDTO createFromParcel(Parcel in) {
            return new ReservationResponseDTO(in);
        }

        @Override
        public ReservationResponseDTO[] newArray(int size) {
            return new ReservationResponseDTO[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(long accommodation) {
        this.accommodation = accommodation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDTO userDetails) {
        this.userDetails = userDetails;
    }

    public AccommodationDTO getAccommodationDetails() {
        return accommodationDetails;
    }

    public void setAccommodationDetails(AccommodationDTO accommodationDetails) {
        this.accommodationDetails = accommodationDetails;
    }
}
