package com.github.petkovicdanilo.ktg.traversal;

import java.util.HashMap;

import com.github.petkovicdanilo.ktg.Graph;

public interface TraversalAlgorithm {
	HashMap<Integer, TraversalNodeInfo> traverse(Graph g, int startingNode);
}
