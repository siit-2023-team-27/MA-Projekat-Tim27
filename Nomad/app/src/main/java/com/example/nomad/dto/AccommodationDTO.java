package com.example.nomad.dto;


import android.media.Rating;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.nomad.enums.AccommodationStatus;
import com.example.nomad.enums.AccommodationType;
import com.example.nomad.enums.ConfirmationType;
import com.example.nomad.enums.PriceType;
import com.example.nomad.services.AuthService;


import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class AccommodationDTO implements Parcelable {
    private long id;
    private int minGuests;
    private int maxGuests;
    private String name;
    private String description;
    private String address;
    private List<Amenity> amenities;
    private List<String> images;
    private AccommodationStatus status;
    private ConfirmationType confirmationType;
    private AccommodationType accommodationType;
    private PriceType priceType;
    private double defaultPrice;
    private int deadlineForCancellation;
    private boolean verified;
    private long hostId;
    public AccommodationDTO(){}

    public AccommodationDTO(long id, int minGuests, int maxGuests, String name, String description, String address, List<Amenity> amenities, List<String> images, AccommodationStatus status, ConfirmationType confirmationType, AccommodationType accommodationType, PriceType priceType, double defaultPrice, int deadlineForCancellation, boolean verified) {
        this.id = id;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.name = name;
        this.description = description;
        this.address = address;
        this.amenities = amenities;
        this.images = images;
        this.status = status;
        this.confirmationType = confirmationType;
        this.accommodationType = accommodationType;
        this.priceType = priceType;
        this.defaultPrice = defaultPrice;
        this.deadlineForCancellation = deadlineForCancellation;
        this.verified = verified;
    }




    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }
    public long getHostId() {
        return hostId;
    }

    public void setHostId(long host) {
        this.hostId = host;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public AccommodationStatus getStatus() {
        return status;
    }


    public ConfirmationType getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(ConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }

    public int getDeadlineForCancellation() {
        return deadlineForCancellation;
    }

    public void setDeadlineForCancellation(int deadlineForCancellation) {
        this.deadlineForCancellation = deadlineForCancellation;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }




    public void addImage(String image){
        this.images.add(image);
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", amenities=" + amenities +
                ", images=" + images +
                ", status=" + status +
                ", confirmationType=" + confirmationType +
                ", deadlineForCancellation=" + deadlineForCancellation +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
    }
}
