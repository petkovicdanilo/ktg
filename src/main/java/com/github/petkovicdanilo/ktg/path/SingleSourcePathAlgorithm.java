package com.github.petkovicdanilo.ktg.path;

import java.util.HashMap;

import com.github.petkovicdanilo.ktg.Graph;

public interface SingleSourcePathAlgorithm {

	HashMap<Integer, PathNodeInfo> getPaths(Graph g, int startingNode);
}
