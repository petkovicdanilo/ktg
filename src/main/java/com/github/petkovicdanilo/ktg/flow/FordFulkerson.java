package com.github.petkovicdanilo.ktg.flow;

import java.util.Map;

import com.github.petkovicdanilo.ktg.Edge;
import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.BreadthFirstTraversal;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo;

public class FordFulkerson implements MaxFlowAlgorithm {

	@Override
	public MaxFlow getMaxFlow(Graph g, int source, int sink) {
		Graph residual = g.getResidual();
		
		Graph flowGraph = new Graph(g.nodes());
		for(Edge edge : g.edges()) {
			flowGraph.addEdge(edge.getFirstNode(), edge.getSecondNode(), 0);
		}
		
		int maxFlow = 0;
		
		BreadthFirstTraversal bfs = new BreadthFirstTraversal();
		Map<Integer, TraversalNodeInfo> bfsInfo = bfs.traverse(residual, source, false);
		
		while(bfsInfo.get(sink).getDistance() != Integer.MAX_VALUE) {
			
			int current = sink;
			int pathFlow = Integer.MAX_VALUE;
			
			while(bfsInfo.get(current).getParent() != null) {
				int parent = bfsInfo.get(current).getParent();
				pathFlow = Math.min(pathFlow, residual.getEdgeWeight(parent, current));
				
				current = parent;
			}
			
			current = sink;
			while(bfsInfo.get(current).getParent() != null) {
				int parent = bfsInfo.get(current).getParent();
				
				residual.addEdgeWeight(current, parent, pathFlow);
				residual.addEdgeWeight(parent, current, -pathFlow);
				
				int flowDirection = 1;
				if(!flowGraph.containsEgde(parent, current)) {
					flowDirection = -1;
				}
				flowGraph.addEdgeWeight(parent, current, flowDirection * pathFlow);
				
				current = parent;
			}
			
			maxFlow += pathFlow;
			
			bfsInfo = bfs.traverse(residual, source, false);
		}
		
		flowGraph.removeZeroEdges();
	
		return new MaxFlow(flowGraph, maxFlow);
	}

}
