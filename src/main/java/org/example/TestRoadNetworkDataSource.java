package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestRoadNetworkDataSource implements RoadNetworkDataSource{

    @Override
    public Graph<GraphNode, DefaultWeightedEdge> getRoadNetwork() {
        Graph<GraphNode, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        GraphNode v1 = new GraphNode("1");
        GraphNode v2 = new GraphNode("2");
        GraphNode v3 = new GraphNode("3");
        GraphNode v4 = new GraphNode("4");
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        DefaultWeightedEdge e1 = graph.addEdge(v1,v2);
        graph.setEdgeWeight(e1, 2);
        DefaultWeightedEdge e2 = graph.addEdge(v1,v3);
        graph.setEdgeWeight(e2, 10);
        DefaultWeightedEdge e3 = graph.addEdge(v2,v3);
        graph.setEdgeWeight(e3, 4);
        DefaultWeightedEdge e4 = graph.addEdge(v3,v4);
        graph.setEdgeWeight(e4, 6);


        return graph;
    }
}
