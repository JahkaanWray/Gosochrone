package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class RouteRenderer  extends JPanel{
    private double maxWeight;
    private List<Pair<Double, Double>> pointList;
    private double latMin, latMax, lonMin, lonMax;
    private Graph<GraphNode, DefaultWeightedEdge> graph;

    private ShortestPathAlgorithm.SingleSourcePaths<GraphNode,DefaultWeightedEdge> paths;

    public RouteRenderer(List<Pair<Double, Double>> pointList, Graph graph, ShortestPathAlgorithm.SingleSourcePaths paths){
        this.maxWeight = 100;
        this.graph = graph;
        this.pointList = pointList;
        this.paths = paths;
        this.latMin = 51.4715;
        this.latMax = 51.47517;
        this.lonMin = -0.12705;
        this.lonMax = -0.1187;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
        repaint();
    }

    public double getMaxWeight(){
        return this.maxWeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        Stroke stroke = new BasicStroke(3);
        g2d.setStroke(stroke);
        double width = lonMax - lonMin;
        double height = latMax - latMin;

        List<DefaultWeightedEdge> edgeList = graph.edgeSet().stream().toList();
        for(int i = 0; i < edgeList.size(); i++ ) {
            DefaultWeightedEdge edge = edgeList.get(i);
            GraphNode s = graph.getEdgeSource(edge);
            GraphNode e = graph.getEdgeTarget(edge);
            double endWeight = paths.getWeight(e);
            double startWeight = paths.getWeight(s);
            int r = Math.min(255, (int)(255*endWeight/5000));
            int green = Math.max((int)(255*(5000-endWeight)/5000),0);
            g2d.setColor(new Color(r,green,0));
            if(endWeight < maxWeight && startWeight < maxWeight){

                drawEdge(edge,0,1, g2d);
            }else{
                double percentage;
                if(startWeight < maxWeight){
                    percentage = (maxWeight - startWeight)/(endWeight - startWeight);
                    drawEdge(edge,0, percentage, g2d);
                }else if(endWeight < maxWeight){
                           percentage = (maxWeight - endWeight)/(startWeight - endWeight);
                    drawEdge(edge,1, 1 - percentage, g2d);
                }
            }
        }

        g2d.setColor(Color.black);
        for(int i = 0; i < pointList.size() - 10; i++){
            Pair p1 = pointList.get(i);
            Pair p2 = pointList.get(i + 1);
            g2d.drawLine((int) (((double) p1.getSecond() - lonMin)*400/width), 400 - (int) (((double) p1.getFirst() - latMin)*400/ height),(int) (((double) p2.getSecond() - lonMin)*400/width), 400 - (int) (((double) p2.getFirst() - latMin)*400/height));
        }

    }
    private void drawEdge(DefaultWeightedEdge edge, double start, double end, Graphics2D g2d) {
        GraphNode source = graph.getEdgeSource(edge);
        GraphNode sink = graph.getEdgeTarget(edge);
        double width = lonMax - lonMin;
        double height = latMax - latMin;

        double startLat = source.coords().getFirst() + start*(sink.coords().getFirst() - source.coords().getFirst());
        double startLon = source.coords().getSecond() + start*(sink.coords().getSecond() - source.coords().getSecond());
        double endLat = source.coords().getFirst() + end*(sink.coords().getFirst() - source.coords().getFirst());
        double endLon = source.coords().getSecond() + end*(sink.coords().getSecond() - source.coords().getSecond());
        g2d.drawLine((int) (((double) startLon - lonMin)*400/width),400 - (int) (((double) startLat - latMin)*400/ height),(int) (((double) endLon - lonMin)*400/width),400 - (int) (((double) endLat - latMin)*400/height));
    }
}
