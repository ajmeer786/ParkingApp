package com.map.parkingmgt;

/**
 * Created by L on 25-Oct-16.
 */
public class GPS {
    //name and slot
    private double lat;
    private double lon;

    public GPS() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
