package org.example;

import org.jgrapht.alg.util.Pair;
import org.w3c.dom.Document;

import java.io.File;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        try{
            File osmFile = new File("src/main/resources/example.xml");
            XMLParser xmlParser = new XMLParser(osmFile);
            Document document = xmlParser.parse();

            RoadGraph roads = new RoadGraph(document);
            System.out.println(roads);

            IsochroneCalculator isochroneCalculator = new IsochroneCalculator(new TestRoadNetworkDataSource());
            isochroneCalculator.calculateIsochrone(new Pair<>(1.0f, 2.0f), 10f);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}