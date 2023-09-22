package org.example;

import org.jgrapht.alg.util.Pair;

public record GraphNode(String id, Pair<Double, Double> coords) {
}
