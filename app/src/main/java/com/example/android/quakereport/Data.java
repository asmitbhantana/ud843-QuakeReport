package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by asmit on 9/4/17.
 */

public class Data {

    private float mag;
    private String place;
    private String date;



    private void Date() {
    }

    public float getMag() {
        return mag;
    }

    public void setMag(float mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String title) {
        this.place = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
