package com.github.petkovicdanilo.ktg;

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
		if(adjMatrix.length != adjMatrix[0].length) {
//			throw new
		}
		
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
	
	public void addEdge(int first, int second) {
		addEdge(first, second, 1);
	}
	
	public void addEdge(int first, int second, int weight) {
		adjList.get(first).add(new EdgeInfo(second, weight));
	}
	
	public void addEdge(Edge edge) {
		addEdge(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
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
	
	public int getEdgeWeight(int firstNode, int secondNode) {
		return getEdgeInfo(firstNode, secondNode)
				.map(edgeInfo -> edgeInfo.getWeight())
				.orElse(null);
	}
}
