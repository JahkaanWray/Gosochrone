package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RoadGraph {

    public RoadGraph(Document document){
        Element rootElement = document.getDocumentElement();
        System.out.println("Root Element: " + rootElement.getTagName());

        NodeList nodeList = rootElement.getElementsByTagName("node");
        for(int i = 0; i < nodeList.getLength(); i++) {
            if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element elementNode = (Element) nodeList.item(i);
                String id = elementNode.getAttribute("id");
                Graph<GraphNode, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
                graph.addVertex(new GraphNode(id));
                System.out.println(elementNode.getTagName() + " id = " + id);
            }
        }

        NodeList wayList = rootElement.getElementsByTagName("way");
        for(int i = 0; i < wayList.getLength(); i++ ){
            if(wayList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element wayNode = (Element) wayList.item(i);
                System.out.println(wayNode.getTagName());
                NodeList refNodeList = wayNode.getElementsByTagName("nd");
                for(int j = 0; j < refNodeList.getLength() - 1; j++){
                    if(refNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element refNode = (Element) refNodeList.item(j);
                        System.out.println(refNode.getTagName() + " ref = " + refNode.getAttribute("ref"));
                    }
                }
            }
        }
    }
}
