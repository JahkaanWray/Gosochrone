package org.example;

public class Haversine {
    private static final double EARTH_RADIUS = 6378.1 * 1000; //Earth radius in m

    public static double distance(double lat1, double lon1, double lat2, double lon2){
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dlat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.pow(Math.sin(dlat/2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2) , 2);
        double b = Math.sqrt(a);
        double c = 2 * EARTH_RADIUS * Math.asin(b);

        return c;
        
    }
}
