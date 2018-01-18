package com.android.dev.qrcoursedorientation.models;

/**
 * Created by iem on 16/01/2018.
 */

public class Checkpoint {

    private String idBalise;
    private String time;
    private double latitude;
    private double longitude;

    public Checkpoint(String idBalise, String time, double latitude, double longitude) {
        this.idBalise = idBalise;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdBalise() {
        return idBalise;
    }

    public String getTime() {
        return time;
    }

    public void setIdBalise(String idBalise) {
        this.idBalise = idBalise;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "idBalise='" + idBalise + '\'' +
                ", time='" + time + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
