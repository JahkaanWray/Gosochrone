package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OSMRoadNetworkDataSource implements RoadNetworkDataSource{

    private final File file;
    public OSMRoadNetworkDataSource(File file){
        this.file = file;
    }

    @Override
    public Graph<GraphNode, DefaultWeightedEdge> getRoadNetwork() {
        Graph<GraphNode, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        Map<String, GraphNode> nodeMap = new HashMap<>();
        try{
            Document document = new XMLParser(file).parse();
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName("node");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node childNode = nodeList.item(i);
                if(childNode.getNodeType() == Node.ELEMENT_NODE){
                    Element childElement = (Element) childNode;
                    String id = childElement.getAttribute("id");
                    Double lat = Double.parseDouble(childElement.getAttribute("lat"));
                    Double lon = Double.parseDouble(childElement.getAttribute("lon"));
                    GraphNode graphNode = new GraphNode(id, new Pair(lat, lon));
                    graph.addVertex(graphNode);
                    nodeMap.put(id, graphNode);
                }
            }

            NodeList wayList = rootElement.getElementsByTagName("way");
            for(int i = 0; i < wayList.getLength(); i++) {
                Node wayNode = wayList.item(i);
                if(wayNode.getNodeType() == Node.ELEMENT_NODE){
                    Element wayElement = (Element)wayNode;
                    NodeList wayTags = wayElement.getElementsByTagName("tag");
                    boolean highway = false;
                    for(int j = 0; j < wayTags.getLength(); j++){
                        Element tagNode = (Element) wayTags.item(j);
                        String tagKey = tagNode.getAttribute("k");
                        System.out.println(tagKey);
                        String tagValue = tagNode.getAttribute("v");
                        boolean validTagValue = tagValue.equals("residential") || tagValue.equals("secondary") || tagValue.equals("primary") || tagValue.equals("tertiary") || tagValue.equals("trunk") || tagValue.equals("service") || tagValue.equals("unclassified");
                        if(tagKey.equals("highway") && validTagValue){
                            highway = true;
                        }
                    }

                    if(highway) {
                        System.out.println("Found highway");
                        NodeList nodeRefList = wayElement.getElementsByTagName("nd");
                        for(int j = 0; j < nodeRefList.getLength() - 1; j++){
                            Node currentNodeRef = nodeRefList.item(j);
                            Node nextNodeRef = nodeRefList.item(j + 1);
                            if(currentNodeRef.getNodeType() == Node.ELEMENT_NODE && nextNodeRef.getNodeType() == Node.ELEMENT_NODE){
                                Element currentNodeRefElement = (Element) currentNodeRef;
                                Element nextNodeRefElement = (Element) nextNodeRef;

                                String currentId = currentNodeRefElement.getAttribute("ref");
                                String nextId = nextNodeRefElement.getAttribute("ref");
                                GraphNode start = nodeMap.get(currentId);
                                GraphNode end = nodeMap.get(nextId);
                                graph.addEdge(start, end);

                                graph.setEdgeWeight(nodeMap.get(currentId), nodeMap.get(nextId), Haversine.distance(start.coords().getFirst(), start.coords().getSecond(), end.coords().getFirst(), end.coords().getSecond()));

                            }
                        }
                    }
                }
            }

            List<GraphNode> vertices = graph.vertexSet().stream().toList();
            for(int i = 0; i < vertices.size(); i++){
                GraphNode vertex = vertices.get(i);
                if(graph.degreeOf(vertex) == 0){
                    graph.removeVertex(vertex);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }
}
