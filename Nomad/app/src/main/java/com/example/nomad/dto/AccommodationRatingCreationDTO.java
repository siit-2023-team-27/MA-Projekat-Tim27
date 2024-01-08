package com.example.nomad.dto;



public class AccommodationRatingCreationDTO {
    private Long userId;
    private Long accommodationId;
    private String text;
    private int rating;

    public AccommodationRatingCreationDTO() {
    }

    public AccommodationRatingCreationDTO(Long userId, Long accommodationId, String text, int rating) {
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.text = text;
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
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
