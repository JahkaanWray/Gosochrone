package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class OSMRoadNetworkDataSource implements RoadNetworkDataSource{

    public OSMRoadNetworkDataSource(){

    }

    @Override
    public Graph<GraphNode, DefaultWeightedEdge> getRoadNetwork() {
        return null;
    }
}
