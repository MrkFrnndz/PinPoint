package com.example.markfernandez.pinpoint;

/**
 * Created by AstroNuts on 2/10/2017.
 */
public class LatLngEvent {
    double lat,lng;
    String mapAddress;

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
