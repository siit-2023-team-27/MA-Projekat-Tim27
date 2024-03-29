package com.example.nomad.dto;



public class Amenity {

    private Long id;
    private String name;
    private String icon;

    public Amenity() {}

    public Amenity(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Override
    public String toString() {
        return "Amenity{" +
                ", name='" + name + '\'' +
                ", id='" + id;
    }

}
