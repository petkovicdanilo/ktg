package com.github.petkovicdanilo.ktg;

import java.util.Map.Entry;

import com.github.petkovicdanilo.ktg.path.Dijkstra;
import com.github.petkovicdanilo.ktg.path.PathNodeInfo;
import com.github.petkovicdanilo.ktg.traversal.BreadthFirstTraversal;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo;

public class Main {

	public static void main(String[] args) {
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

		Graph g = new Graph(adjMat);

		BreadthFirstTraversal bfs = new BreadthFirstTraversal();
		for(TraversalNodeInfo nodeTraversal : bfs.traverse(g, 0)) {
			System.out.println(nodeTraversal.getNode() + " " + nodeTraversal.getDistance());
		}
		
		System.out.println();
		
		Dijkstra dijkstra = new Dijkstra();
		for (Entry<Integer, PathNodeInfo> entry : dijkstra.getPaths(g, 0).entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue().getDistance() 
				+ " " + entry.getValue().getParent());
		}

		System.out.println();
	}

}
