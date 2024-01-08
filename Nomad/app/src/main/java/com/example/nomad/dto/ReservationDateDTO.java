package com.example.nomad.dto;

import java.util.Date;

public class ReservationDateDTO {
    private Date date;
    private boolean taken;
    private double price = -1;

    public ReservationDateDTO(Date date, boolean taken, double price) {
        this.date = date;
        this.taken = taken;
        this.price = price;
    }

    public ReservationDateDTO(Date date, boolean taken) {
        this.date = date;
        this.taken = taken;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
