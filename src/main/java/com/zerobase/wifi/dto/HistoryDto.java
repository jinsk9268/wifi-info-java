package com.zerobase.wifi.dto;

public class HistoryDto {
    private int id;
    private double longitude;
    private double latitude;
    private String checkDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCheckDatetime() {
        return checkDatetime;
    }

    public void setCheckDatetime(String checkDatetime) {
        this.checkDatetime = checkDatetime;
    }
}
