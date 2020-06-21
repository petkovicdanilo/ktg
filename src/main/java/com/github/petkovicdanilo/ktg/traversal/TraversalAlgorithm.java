package com.github.petkovicdanilo.ktg.traversal;

import java.util.Map;

import com.github.petkovicdanilo.ktg.Graph;

public interface TraversalAlgorithm {
	Map<Integer, TraversalNodeInfo> traverse(Graph g, int startingNode);
	Map<Integer, TraversalNodeInfo> traverse(Graph g, int startingNode, boolean allowZeroWeightEdges);
}
