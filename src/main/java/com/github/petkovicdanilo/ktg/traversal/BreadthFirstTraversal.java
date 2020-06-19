package com.github.petkovicdanilo.ktg.traversal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo.Color;

public class BreadthFirstTraversal implements TraversalAlgorithm {

	public HashMap<Integer, TraversalNodeInfo> traverse(Graph g, int startingNode) {
		HashMap<Integer, TraversalNodeInfo> traversalInfo = new HashMap<>();

		for (int node : g.nodes()) {
			TraversalNodeInfo nodeInfo = new TraversalNodeInfo();

			nodeInfo.setParent(null);
			nodeInfo.setColor(Color.WHITE);
			nodeInfo.setDistance(Integer.MAX_VALUE);

			traversalInfo.put(node, nodeInfo);
		}
		
		traversalInfo.get(startingNode).setColor(Color.GRAY);
		traversalInfo.get(startingNode).setDistance(0);
		
		Queue<Integer> q = new LinkedList<>();
		q.add(startingNode);
		
		while(!q.isEmpty()) { 
			int currentNode = q.remove();
			TraversalNodeInfo currentNodeInfo = traversalInfo.get(currentNode);
			
			for(int neighbour : g.neighbours(currentNode)) {
				TraversalNodeInfo neighbourInfo = traversalInfo.get(neighbour);
				
				if(neighbourInfo.getColor() == Color.WHITE) {
					neighbourInfo.setColor(Color.GRAY);
					neighbourInfo.setDistance(currentNodeInfo.getDistance() + 1);
					neighbourInfo.setParent(currentNode);
					
					q.add(neighbour);
				}
			}
			
			currentNodeInfo.setColor(Color.BLACK);
		}

		return traversalInfo;
	}

}
