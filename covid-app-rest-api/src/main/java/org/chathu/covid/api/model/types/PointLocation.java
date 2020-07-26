package org.chathu.covid.api.model.types;

public class PointLocation {
    private String type;
    private double[] coordinates;

    public PointLocation(){
        this("point",null);
    }

    public PointLocation(double[] coordinates){
        this("point",coordinates);
    }

    public PointLocation(String type, double[] coordinates){
        this.type = type;
        this.coordinates = coordinates;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
