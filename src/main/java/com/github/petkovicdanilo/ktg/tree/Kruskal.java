package com.github.petkovicdanilo.ktg.tree;

import java.util.Comparator;
import java.util.List;

import com.github.petkovicdanilo.ktg.Edge;
import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.util.DisjointSetsUnion;

public class Kruskal implements MinSpanningTreeAlgorithm {

	@Override
	public Graph getMinSpanningTree(Graph g) {
		Graph spanningTree = new Graph(g.nodes());
		List<Edge> edges = g.edges();
		
		edges.sort(new Comparator<Edge>() {

			@Override
			public int compare(Edge e1, Edge e2) {
				if(e1.getWeight() < e2.getWeight()) {
					return -1;
				}
				if(e1.getWeight() > e2.getWeight()) {
					return 1;
				}
				
				return 0;
			}
		});
		
		int V = g.nodes().size();
		DisjointSetsUnion setsUnion = new DisjointSetsUnion(V);
		
		int edgesAdded = 0, edgesIndex = 0;
		while(edgesAdded < V - 1) {
			Edge edge = edges.get(edgesIndex++);
			
			int firstNode = edge.getFirstNode(), secondNode = edge.getSecondNode();
			
			if(setsUnion.find(firstNode) != setsUnion.find(secondNode)) {
				setsUnion.union(firstNode, secondNode);
				
				spanningTree.addEdge(edge);
				spanningTree.addEdge(edge.reverse());
				
				edgesAdded++;
			}
			
		}
		
		return spanningTree;
	}
}
