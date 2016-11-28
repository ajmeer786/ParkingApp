package com.map.parkingmgt;

/**
 * Created by L on 21-Nov-16.
 */
public class Formuladistance {

        public static final double R = 6372.8; // In kilometers
        public static double haversine(double lat1, double lon1, double lat2, double lon2) {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            double a = (Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2)) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        }
}

//formula
/**    double rad = 6372.8; // In kilometers
 double dLat = Math.toRadians(userLat - parklat);
 double dLon = Math.toRadians(userLon - parklon);
 userLat = Math.toRadians(userLat);
 parklat = Math.toRadians(parklat);

 double a = (Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2)) * (Math.cos(userLat)) * (Math.cos(parklat));
 double c = 2 * Math.asin(Math.sqrt(a));

 */