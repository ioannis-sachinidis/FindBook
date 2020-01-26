package com.example.findbook;

public class LocationClass //klasi pou krataei ti topothesia pou tha emfanistei sto xarti
{
    private float lat,lng;
    private String address;

    public LocationClass() {
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;

    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

