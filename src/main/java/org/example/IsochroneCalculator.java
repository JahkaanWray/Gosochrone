package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public class IsochroneCalculator {

    private final RoadNetworkDataSource roadNetworkDataSource;
    public IsochroneCalculator(RoadNetworkDataSource dataSource){
        this.roadNetworkDataSource = dataSource;
    }

    public void calculateIsochrone(Pair<Float, Float> startLocation, Float travelDistance){
        System.out.println(startLocation);
        System.out.println(travelDistance);
        Graph<GraphNode, DefaultWeightedEdge> graph = roadNetworkDataSource.getRoadNetwork();
        Object[] vertices = graph.vertexSet().toArray();
        GraphNode startVertex = (GraphNode) vertices[0];
        GraphNode sink = (GraphNode) vertices[3];
        DijkstraShortestPath<GraphNode, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<GraphNode, DefaultWeightedEdge> paths = dijkstraShortestPath.getPaths(startVertex);
        System.out.println(paths.toString());
        GraphPath<GraphNode, DefaultWeightedEdge> path = paths.getPath(sink);
        List<GraphNode> pathlist = path.getVertexList();
        pathlist.forEach((GraphNode node) -> System.out.println(node.id()));
        System.out.println(path.getWeight());
        new Isochrone();
    }
}
