package com.example.nomad.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ReservationDTO implements Parcelable {
    private long id;
    private long user;
    private long accommodation;
    private Date startDate;
    private Date finishDate;
    private int numGuests;
    private String status;
    public ReservationDTO( long user, long accommodation, Date startDate, Date finishDate, int numGuests) {
        this.user = user;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.numGuests = numGuests;
        this.status = "PENDING";
    }
    public ReservationDTO(long id, long user, long accommodation, Date startDate, Date finishDate, int numGuests, String status) {
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.numGuests = numGuests;
        this.status = status;
    }

    protected ReservationDTO(Parcel in) {
        id = in.readLong();
        user = in.readLong();
        accommodation = in.readLong();
        numGuests = in.readInt();
        status = in.readString();
    }

    public static final Creator<ReservationDTO> CREATOR = new Creator<ReservationDTO>() {
        @Override
        public ReservationDTO createFromParcel(Parcel in) {
            return new ReservationDTO(in);
        }

        @Override
        public ReservationDTO[] newArray(int size) {
            return new ReservationDTO[size];
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
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

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(user);
        dest.writeLong(accommodation);
        dest.writeInt(numGuests);
        dest.writeString(status);
    }
}
