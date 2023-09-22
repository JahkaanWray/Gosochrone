package org.example;

import org.jgrapht.alg.util.Pair;
import org.w3c.dom.Document;

import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        try{
            File osmFile = new File("src/main/resources/export.osm");
            IsochroneCalculator isochroneCalculator = new IsochroneCalculator(new OSMRoadNetworkDataSource(osmFile));
            isochroneCalculator.calculateIsochrone(new Pair<>(1.0, 2.0), 10f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}