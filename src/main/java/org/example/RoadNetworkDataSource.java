package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public interface RoadNetworkDataSource {

    Graph<GraphNode, DefaultWeightedEdge> getRoadNetwork();
}
