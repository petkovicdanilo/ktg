package com.github.petkovicdanilo.ktg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

	private HashMap<Integer, HashSet<Integer>> adjList;

	public Graph() {
		this(Collections.emptyList(), Collections.emptyList());
	}
	
	public Graph(Iterable<Integer> nodes) {
		this(nodes, Collections.emptyList());
	}
	
	public Graph(Iterable<Integer> nodes, Iterable<Edge> edges) {
		adjList = new HashMap<>();

		Iterator<Integer> nodesIterator = nodes.iterator();
		while (nodesIterator.hasNext()) {
			addNode(nodesIterator.next());
		}

		Iterator<Edge> edgesIterator = edges.iterator();
		while (edgesIterator.hasNext()) {
			addEdge(edgesIterator.next());
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
		adjList.get(first).add(second);
	}
	
	public void addEdge(Edge edge) {
		addEdge(edge.getFirstNode(), edge.getSecondNode());
	}
	
	public boolean containsEgde(Edge edge) {
		return containsEgde(edge.getFirstNode(), edge.getSecondNode());
	}
	
	public boolean containsEgde(int firstNode, int secondNode) {
		return adjList.get(firstNode).contains(secondNode);
	}
	
	public HashSet<Integer> neighbours(int node) {
		return adjList.get(node);
	}
	
}
