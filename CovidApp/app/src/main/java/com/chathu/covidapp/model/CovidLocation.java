package com.chathu.covidapp.model;


public class CovidLocation{
    private double latitude;
    private double longitude;
    private int covidCases;

    public CovidLocation(double latitude, double longitude, int covidCases){
        this.latitude = latitude;
        this.longitude = longitude;
        this.covidCases = covidCases;
    }

    public double[] getCoordinates(){
        return new double[]{getLatitude(), getLongitude()};
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCovidCases() {
        return covidCases;
    }

    public void setCovidCases(int covidCases) {
        this.covidCases = covidCases;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
