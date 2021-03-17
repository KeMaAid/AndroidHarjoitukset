package com.example.week9;

import java.time.LocalTime;

public class Movie {
    String title;
    int ID;
    int locationID;
    LocalTime startTime;

    public Movie(String title, int ID, int locationID, LocalTime startTime){
        this.title=title;
        this.ID=ID;
        this.locationID=locationID;
        this.startTime=startTime;
    }

    public int getID() {
        return ID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }
}
