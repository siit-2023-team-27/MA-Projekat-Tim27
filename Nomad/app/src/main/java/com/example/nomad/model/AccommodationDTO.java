package com.example.nomad.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;
public class AccommodationDTO implements Parcelable {
    private long id;
    private int minGuests;
    private int maxGuests;
    private String name;
    private String description;
    private String address;
    private List<String> images;
    private List<Integer> ratings;
    private String priceType;
    private double defaultPrice;
    private long hostId;

    public AccommodationDTO(long id, int minGuests, int maxGuests, String name, String description, String address, List<String> images, List<Integer> ratings, String priceType, double defaultPrice, long hostId) {
        this.id = id;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.name = name;
        this.description = description;
        this.address = address;
        this.images = images;
        this.ratings = ratings;
        this.priceType = priceType;
        this.defaultPrice = defaultPrice;
        this.hostId = hostId;
    }
    public AccommodationDTO(long id, int minGuests, int maxGuests, String name, String description, String address) {
        this.id = id;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.name = name;
        this.description = description;
        this.address = address;
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
    protected AccommodationDTO(Parcel in) {
        // Čitanje ostalih atributa proizvoda iz Parcel objekta
        id = in.readLong();
        name = in.readString();
        description = in.readString();
    }
    public static final Creator<AccommodationDTO> CREATOR = new Creator<AccommodationDTO>() {
        @Override
        public AccommodationDTO createFromParcel(Parcel in) {
            return new AccommodationDTO(in);
        }

        @Override
        public AccommodationDTO[] newArray(int size) {
            return new AccommodationDTO[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
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

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

}