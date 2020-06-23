package com.github.petkovicdanilo.ktg;

import java.util.Map.Entry;

import com.github.petkovicdanilo.ktg.flow.FordFulkerson;
import com.github.petkovicdanilo.ktg.flow.MaxFlow;
import com.github.petkovicdanilo.ktg.flow.PushRelabel;
import com.github.petkovicdanilo.ktg.path.Dijkstra;
import com.github.petkovicdanilo.ktg.path.PathNodeInfo;
import com.github.petkovicdanilo.ktg.traversal.BreadthFirstTraversal;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo;
import com.github.petkovicdanilo.ktg.tree.Kruskal;

@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
//		bfs();
//		dijkstra();
//		kruskal();
//		fordFulkerson();
//		pushRelabel();
//		dinitz();
	}

	private static Graph graph1() {
		int[][] adjMat = 
			{ { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, 
			  { 4, 0, 8, 0, 0, 0, 0, 11, 0 }, 
			  { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
			  { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, 
			  { 0, 0, 0, 9, 0, 10, 0, 0, 0 }, 
			  { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
			  { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, 
			  { 8, 11, 0, 0, 0, 0, 1, 0, 7 }, 
			  { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
		
		return new Graph(adjMat);
	}
	
	private static Graph graph2() {
		int[][] adjMat = { 
			{0, 16, 13, 0, 0, 0}, 
	        {0, 0, 10, 12, 0, 0}, 
	        {0, 4, 0, 0, 14, 0}, 
	        {0, 0, 9, 0, 0, 20}, 
	        {0, 0, 0, 7, 0, 4}, 
	        {0, 0, 0, 0, 0, 0} 
	      };
		
		return new Graph(adjMat);
	}
	
	private static Graph graph3() {
		Graph g = new Graph(6);
		
		g.addEdge(0, 1, 10);
		g.addEdge(0, 2, 10);
		g.addEdge(1, 2, 2);
		g.addEdge(1, 3, 4);
		g.addEdge(1, 4, 8);
		g.addEdge(2, 4, 9);
		g.addEdge(3, 5, 10);
		g.addEdge(4, 3, 6);
		g.addEdge(4, 5, 10);
		
		return g;
	}
	
	private static void bfs() {
		Graph g = graph1();
		
		BreadthFirstTraversal bfs = new BreadthFirstTraversal();
		for(Entry<Integer, TraversalNodeInfo> entry : bfs.traverse(g, 0).entrySet()) {
			System.out.println(
				entry.getValue().getOrderNumber() + " " + entry.getKey() + " " 
				+ entry.getValue().getDistance());
		}
		
		System.out.println();
	}
	
	private static void dijkstra() {
		Graph g = graph1();
		
		Dijkstra dijkstra = new Dijkstra();
		for (Entry<Integer, PathNodeInfo> entry : dijkstra.getPaths(g, 0).entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue().getDistance() 
				+ " " + entry.getValue().getParent());
		}

		System.out.println();
	}
	
	private static void kruskal() {
		Graph g = graph1();
		Kruskal kruskal = new Kruskal();
		Graph mst = kruskal.getMinSpanningTree(g);
		
		System.out.println(mst);
	}
	
	private static void fordFulkerson() {
//		Graph g = graph2();
		Graph g = graph3();
		
		FordFulkerson fordFulkerson = new FordFulkerson();
		MaxFlow maxFlow = fordFulkerson.getMaxFlow(g, 0, 5);
		
		System.out.println(maxFlow.getFlow());
		System.out.println(maxFlow.getFlowGraph());
	}
	
	private static void pushRelabel() {
//		Graph g = graph2();
		Graph g = graph3();
		
		PushRelabel pushRelabel = new PushRelabel();
		MaxFlow maxFlow = pushRelabel.getMaxFlow(g, 0, 5);
		
		System.out.println(maxFlow.getFlow());
		System.out.println(maxFlow.getFlowGraph());
		
	}
}
