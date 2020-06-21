package com.github.petkovicdanilo.ktg.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.petkovicdanilo.ktg.Edge;
import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.BreadthFirstTraversal;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo;

public class FordFulkerson implements MaxFlowAlgorithm {

	@Override
	public MaxFlow getMaxFlow(Graph g, int source, int sink) {
		Graph residual = new Graph(g.nodes());
		Graph flowGraph = new Graph(g.nodes());
		
		for(int node1 : g.nodes()) {
			for(int node2 : g.nodes()) {
				if(node1 == node2) {
					continue;
				}
					
				if(!g.containsEgde(node1, node2) && !g.containsEgde(node2, node1)) {
					continue;
				}
				
				int weight = 0;
				if(g.containsEgde(node1, node2)) {
					weight = g.getEdgeWeight(node1, node2);
					
					flowGraph.addEdge(node1, node2, 0);
				}
				
				residual.addEdge(node1, node2, weight);
			}
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
		
		List<Edge> edgesToRemove = new ArrayList<>();
		for(Edge edge : flowGraph.edges()) {
			if(edge.getWeight() == 0) {
				edgesToRemove.add(edge);
			}
		}
		
		for(Edge edge : edgesToRemove) {
			flowGraph.removeEdge(edge);
		}
		
		return new MaxFlow(flowGraph, maxFlow);
	}

}
