package com.github.petkovicdanilo.ktg.traversal;

import java.util.List;

import com.github.petkovicdanilo.ktg.Graph;

public interface TraversalAlgorithm {
	List<TraversalNodeInfo> traverse(Graph g, int startingNode);
}
