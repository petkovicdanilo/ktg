package com.github.petkovicdanilo.ktg.flow;

import java.util.Map;

import com.github.petkovicdanilo.ktg.Edge;
import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.BreadthFirstTraversal;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo;

public class Dinitz implements MaxFlowAlgorithm {

	private Graph residual, flowGraph;
	private Map<Integer, TraversalNodeInfo> bfsInfo;
	
	@Override
	public MaxFlow getMaxFlow(Graph g, int source, int sink) {
		this.residual = g.getResidual();
		
		this.flowGraph = new Graph(g.nodes());
		for(Edge edge : g.edges()) {
			flowGraph.addEdge(edge.getFirstNode(), edge.getSecondNode(), 0);
		}
		
		int maxFlow = 0;
		
		BreadthFirstTraversal bfs = new BreadthFirstTraversal();
		this.bfsInfo = bfs.traverse(residual, source, false);
		
		while(bfsInfo.get(sink).getLevel() != -1) {
			
			int flow = sendFlow(source, sink, Integer.MAX_VALUE);
			while(flow != 0) {
				maxFlow += flow;
				flow = sendFlow(source, sink, Integer.MAX_VALUE);
			}
			
			bfsInfo = bfs.traverse(residual, source, false);
		}
		
		flowGraph.removeZeroEdges();
		
		return new MaxFlow(flowGraph, maxFlow);
	}

	private int sendFlow(int node, int sink, int flow) {
		if(node == sink) {
			return flow;
		}
		
		int nodeLevel = bfsInfo.get(node).getLevel();
		
		for(int neighbour : residual.neighbours(node)) {
			int neighbourLevel = bfsInfo.get(neighbour).getLevel();
			
			int edgeCapacity = residual.getEdgeWeight(node, neighbour);
			
			if(neighbourLevel == nodeLevel + 1 && edgeCapacity > 0) {
				
				int currentFlow = Math.min(flow, edgeCapacity);
				
				int restFlow = sendFlow(neighbour, sink, currentFlow);
				
				if(restFlow > 0) {
					residual.addEdgeWeight(node, neighbour, -restFlow);
					residual.addEdgeWeight(neighbour, node, restFlow);
					
					int flowDirection = 1;
					if(!flowGraph.containsEgde(node, neighbour)) {
						flowDirection = -1;
					}
					flowGraph.addEdgeWeight(node, neighbour, flowDirection * restFlow);
					
					return restFlow;
				}
			}
		}
		
		return 0;
	}
	
}
