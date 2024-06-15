package com.s22010334.finalproject.Model;

public class eventModel {
    String place,date,time;

    public eventModel() {
    }

    public eventModel(String place, String date, String time) {
        this.place = place;
        this.date = date;
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
