package com.example.nomad.dto;

import java.util.Date;

public class ReservationResponseDTO {
    private long id;
    private long user;
    private long accommodation;
    private String startDate;
    private String finishDate;
    private int numGuests;
    private String status;

    public ReservationResponseDTO(long id, long user, long accommodation, String startDate, String finishDate, int numGuests, String status) {
        this.id = id;
        this.user = user;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.numGuests = numGuests;
        this.status = status;
    }

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
}
