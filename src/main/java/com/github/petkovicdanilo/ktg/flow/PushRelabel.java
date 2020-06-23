package com.github.petkovicdanilo.ktg.flow;

import java.util.HashMap;
import java.util.Map;

import com.github.petkovicdanilo.ktg.Edge;
import com.github.petkovicdanilo.ktg.Graph;

public class PushRelabel implements MaxFlowAlgorithm {

	private Graph graph, flowGraph, residual;
	private Map<Integer, Integer> heights;
	private Map<Integer, Integer> excessFlow;
	private int source, sink;
	
	@Override
	public MaxFlow getMaxFlow(Graph g, int source, int sink) {
		this.graph = g;
		this.flowGraph = new Graph(g);
		this.residual = new Graph(g);
		this.heights = new HashMap<>();
		this.excessFlow = new HashMap<>();
		
		this.source = source;
		this.sink = sink;
		
		preFlow();
		
		int overNode = overflowNode();
		while(overNode != -1) {
			if(!push(overNode)) {
				relabel(overNode);
			}
			
			overNode = overflowNode();
		}
		
		flowGraph.removeZeroEdges();
		int maxFlow = calculateMaxFlow();
		
		return new MaxFlow(flowGraph, maxFlow);
	}

	private int calculateMaxFlow() {
		int maxFlow = 0;
		for(int neighbour : flowGraph.neighbours(source)) {
			maxFlow += flowGraph.getEdgeWeight(source, neighbour);
		}
		return maxFlow;
	}
	
	private void preFlow() {
		for(int node : graph.nodes()) {
			heights.put(node, 0);
			excessFlow.put(node, 0);
		}
		
		for(Edge edge : graph.edges()) {
			flowGraph.setEdgeWeight(
					edge.getFirstNode(), edge.getSecondNode(), 0);
		}
		
		heights.put(source, graph.nodes().size());
		
		for(int neighbour : graph.neighbours(source)) {
			int weight = graph.getEdgeWeight(source, neighbour);
			
			residual.addEdgeWeight(source, neighbour, -weight);
			if(!residual.containsEgde(neighbour, source)) {
				residual.addEdge(neighbour, source, weight);
			}
			else {
				residual.addEdgeWeight(neighbour, source, weight);
			}
			
			flowGraph.addEdgeWeight(source, neighbour, weight);
			
			excessFlow.put(neighbour, weight);
		}
	}
	
	private int overflowNode() {
		int retNode = -1;
		int maxValue = Integer.MIN_VALUE;
		
		for(int node : residual.nodes()) {
			if(excessFlow.get(node) <= 0) {
				continue;
			}
			
			if(heights.get(node) > maxValue) {
				maxValue = heights.get(node);
				retNode = node;
			}
		}
		
		return retNode;
	}
	
	private boolean push(int node) {
		for(int neighbour : residual.neighbours(node)) {
			
			if(residual.getEdgeWeight(node, neighbour) == 0 ||
				graph.containsEgde(node, neighbour) &&
				flowGraph.getEdgeWeight(node, neighbour) == 
				graph.getEdgeWeight(node, neighbour)) 
			{
				continue;
			}
			
			if(heights.get(node) > heights.get(neighbour)) {
				int flow = Math.min(
					excessFlow.get(node), 
					residual.getEdgeWeight(node, neighbour)
				);
				
				excessFlow.put(node, excessFlow.get(node) - flow);
				if(neighbour != source && neighbour != sink) {
					excessFlow.put(neighbour, excessFlow.get(neighbour) + flow);
				}
				
				residual.addEdgeWeight(node, neighbour, -flow);
				if(!residual.containsEgde(neighbour, node)) {
					residual.addEdge(neighbour, node, flow);
				}
				else {
					residual.addEdgeWeight(neighbour, node, flow);
				}
					
				if(!flowGraph.containsEgde(node, neighbour)) {
					flowGraph.addEdgeWeight(neighbour, node, -flow);
				}
				else {
					flowGraph.addEdgeWeight(node, neighbour, flow);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	private void relabel(int node) {
		int minHeight = Integer.MAX_VALUE;
		
		for(int neighbour : residual.neighbours(node)) {
			if(residual.getEdgeWeight(node, neighbour) == 0 ||
				graph.containsEgde(node, neighbour) &&
				flowGraph.getEdgeWeight(node, neighbour) == 
				graph.getEdgeWeight(node, neighbour)) 
			{
				continue;
			}
			
			int neighbourHight = heights.get(neighbour);
			
			if(neighbourHight < minHeight) {
				minHeight = neighbourHight;
			}
		}
		
		heights.put(node, minHeight + 1);
	}
}
