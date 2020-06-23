package com.github.petkovicdanilo.ktg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Graph {

	private HashMap<Integer, HashSet<EdgeInfo>> adjList = new HashMap<>();

	@Data
	@AllArgsConstructor
	private class EdgeInfo {
		private int neighbour;
		private int weight;
	}
	
	public Graph() {
		this(Collections.emptyList(), Collections.emptyList());
	}
	
	public Graph(int V) {
		for(int i = 0; i < V; ++i) {
			addNode(i);
		}
	}
	
	public Graph(Iterable<Integer> nodes) {
		this(nodes, Collections.emptyList());
	}
	
	public Graph(Iterable<Integer> nodes, Iterable<Edge> edges) {
		Iterator<Integer> nodesIterator = nodes.iterator();
		while (nodesIterator.hasNext()) {
			addNode(nodesIterator.next());
		}

		Iterator<Edge> edgesIterator = edges.iterator();
		while (edgesIterator.hasNext()) {
			addEdge(edgesIterator.next());
		}
	}
	
	public Graph(int[][] adjMatrix) {
		
		for(int i = 0; i < adjMatrix.length; ++i) {
			addNode(i);
		}
		
		for(int i = 0; i < adjMatrix.length; ++i) {
			for(int j = 0; j < adjMatrix.length; ++j) {
				if(adjMatrix[i][j] != 0) {
					addEdge(i, j, adjMatrix[i][j]);
				}
			}
		}
	}
	
	public Graph(Graph g) {
		this(g.nodes(), g.edges());
	}
	
	public List<Integer> nodes() {
		return adjList.keySet().stream().collect(Collectors.toList());
	}
	
	public void addNode(int node) {
		if(!adjList.containsKey(node)) {
			adjList.put(node, new HashSet<>());
		}
	}
	
	public boolean containsNode(int node) {
		return adjList.containsKey(node);
	}
	
	public List<Edge> edges() {
		List<Edge> edges = new ArrayList<>();
		
		for(int node : adjList.keySet()) {
			for(EdgeInfo edgeInfo : adjList.get(node)) {
				edges.add(new Edge(node, edgeInfo.neighbour, edgeInfo.weight));
			}
		}
		
		return edges;
	}
	
	public void addEdge(int first, int second) {
		addEdge(first, second, 1);
	}
	
	public void addEdge(int first, int second, int weight) {
		adjList.get(first).add(new EdgeInfo(second, weight));
	}
	
	public void addEdge(Edge edge) {
		addEdge(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
	}
	
	public void removeEdge(int first, int second) {
		EdgeInfo edgeInfo = getEdgeInfo(first, second).get();
		adjList.get(first).remove(edgeInfo);
	}
	
	public void removeEdge(Edge edge) {
		removeEdge(edge.getFirstNode(), edge.getSecondNode());
	}
	
	public boolean containsEgde(Edge edge) {
		return containsEgde(edge.getFirstNode(), edge.getSecondNode());
	}
	
	public boolean containsEgde(int firstNode, int secondNode) {
		return getEdgeInfo(firstNode, secondNode)
				.isPresent();
	}
	
	public List<Integer> neighbours(int node) {
		return adjList.get(node)
				.stream()
				.map(edgeInfo -> edgeInfo.getNeighbour())
				.collect(Collectors.toList());
	}
	
	private Optional<EdgeInfo> getEdgeInfo(int firstNode, int secondNode) {
		return adjList.get(firstNode)
				.stream()
				.filter(edgeInfo -> edgeInfo.getNeighbour() == secondNode)
				.findFirst();
	}
	
	public void setEdgeWeight(int firstNode, int secondNode, int newWeight) {
		getEdgeInfo(firstNode, secondNode).get().setWeight(newWeight);
	}
	
	public void addEdgeWeight(int firstNode, int secondNode, int amount) {
		EdgeInfo edgeInfo = getEdgeInfo(firstNode, secondNode).get();
		int oldWeight = edgeInfo.getWeight();
		edgeInfo.setWeight(oldWeight + amount);
	}
	
	public int getEdgeWeight(int firstNode, int secondNode) {
		return getEdgeInfo(firstNode, secondNode)
				.map(edgeInfo -> edgeInfo.getWeight())
				.orElse(null);
	}
	
	public Graph getResidual() {
		Graph residual = new Graph(this.nodes());
		
		for(int node1 : this.nodes()) {
			for(int node2 : this.nodes()) {
				if(node1 == node2) {
					continue;
				}
					
				if(!this.containsEgde(node1, node2) && !this.containsEgde(node2, node1)) {
					continue;
				}
				
				int weight = 0;
				if(this.containsEgde(node1, node2)) {
					weight = this.getEdgeWeight(node1, node2);
				}
				
				residual.addEdge(node1, node2, weight);
			}
		}
		
		return residual;
	}
	
	public void removeZeroEdges() {
		List<Edge> edgesToRemove = new ArrayList<>();
		for(Edge edge : this.edges()) {
			if(edge.getWeight() == 0) {
				edgesToRemove.add(edge);
			}
		}
		
		for(Edge edge : edgesToRemove) {
			this.removeEdge(edge);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Graph (\n");
		
		for(int node : nodes()) {
			sb.append("\t" + node + ": ");
			
			for(EdgeInfo edgeInfo : adjList.get(node)) {
				sb.append(edgeInfo.getNeighbour() + 
						"(weight = " + edgeInfo.getWeight() + "), ");
			}
			
			sb.append("\n");
		}
		
		sb.append(")\n");
		
		return sb.toString();
	}
}
