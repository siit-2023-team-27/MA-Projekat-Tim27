package com.example.nomad.dto;

import android.os.Parcel;

import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.AccommodationType;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;

import java.util.List;

public class SearchAccommodationDTO extends AccommodationDTO{
    Double totalPrice;
    Double pricePerNight;
    double averageRating;

    public SearchAccommodationDTO(Double totalPrice, Double pricePerNight, double averageRating) {
        this.totalPrice = totalPrice;
        this.pricePerNight = pricePerNight;
        this.averageRating = averageRating;
    }

    public SearchAccommodationDTO(long id, int minGuests, int maxGuests, String name, String description, String address, List<Amenity> amenities, List<String> images, List<AccommodationRating> ratings, AccommodationStatus status, ConfirmationType confirmationType, AccommodationType accommodationType, PriceType priceType, double defaultPrice, int deadlineForCancellation, boolean verified, Double totalPrice, Double pricePerNight, double averageRating) {
        super(id, minGuests, maxGuests, name, description, address, amenities, images, ratings, status, confirmationType, accommodationType, priceType, defaultPrice, deadlineForCancellation, verified);
        this.totalPrice = totalPrice;
        this.pricePerNight = pricePerNight;
        this.averageRating = averageRating;
    }

    public SearchAccommodationDTO(Parcel in, Double totalPrice, Double pricePerNight, double averageRating) {
        super(in);
        this.totalPrice = totalPrice;
        this.pricePerNight = pricePerNight;
        this.averageRating = averageRating;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
