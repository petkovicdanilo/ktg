package com.github.petkovicdanilo.ktg.flow;

import com.github.petkovicdanilo.ktg.Graph;

public interface MaxFlowAlgorithm {

	MaxFlow getMaxFlow(Graph g, int source, int sink);
}
