package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class IsochroneCalculator {

    private final RoadNetworkDataSource roadNetworkDataSource;
    private Timer timer;
    public IsochroneCalculator(RoadNetworkDataSource dataSource){
        this.roadNetworkDataSource = dataSource;
    }

    public void calculateIsochrone(Pair<Double, Double> startLocation, Float travelDistance){
        System.out.println("Start Location: " + startLocation);
        System.out.println("Travel Distance: " + travelDistance);
        Graph<GraphNode, DefaultWeightedEdge> graph = roadNetworkDataSource.getRoadNetwork();
        Object[] vertices = graph.vertexSet().toArray();
        GraphNode startVertex = (GraphNode) vertices[(int) (vertices.length*Math.random())];
        GraphNode sink = (GraphNode) vertices[(int) (vertices.length*Math.random())];
        System.out.println("Start Coordinates: " + startVertex.coords().toString());
        System.out.println("End Coordinates: " + sink.coords().toString());
        System.out.println("Straight Line Distance: " + Haversine.distance(startVertex.coords().getFirst(), startVertex.coords().getSecond(), sink.coords().getFirst(), sink.coords().getSecond()));
        DijkstraShortestPath<GraphNode, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<GraphNode, DefaultWeightedEdge> paths = dijkstraShortestPath.getPaths(startVertex);
        System.out.println(paths.toString());
        GraphPath<GraphNode, DefaultWeightedEdge> path = paths.getPath(sink);
        List<GraphNode> pathlist = path.getVertexList();
        RouteRenderer renderer = new RouteRenderer(pathlist.stream().map(GraphNode::coords).collect(Collectors.toList()), graph, paths);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        frame.add(renderer);
        frame.setVisible(true);
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setMaxWeight(renderer.getMaxWeight() + 5);
                System.out.println(renderer.getMaxWeight());
                if(renderer.getMaxWeight() > 6000){
                    renderer.setMaxWeight(1000);
                }
            }
        });
        timer.start();

        new Isochrone();
    }
}
