package com.github.petkovicdanilo.ktg.traversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo.Color;

public class BreadthFirstTraversal implements TraversalAlgorithm {

	public List<TraversalNodeInfo> traverse(Graph g, int startingNode) {
		List<TraversalNodeInfo> traversalInfo = new ArrayList<>();
		List<TraversalNodeInfo> orderedTraversal = new ArrayList<>();

		for (int node : g.nodes()) {
			TraversalNodeInfo nodeInfo = TraversalNodeInfo.builder()
					.node(node)
					.parent(null)
					.color(Color.WHITE)
					.distance(Integer.MAX_VALUE)
					.build();

			traversalInfo.add(nodeInfo);
		}
		
		traversalInfo.get(startingNode).setColor(Color.GRAY);
		traversalInfo.get(startingNode).setDistance(0);
		traversalInfo.get(startingNode).setLevel(0);
		
		Queue<Integer> q = new LinkedList<>();
		q.add(startingNode);
		
		while(!q.isEmpty()) { 
			int currentNode = q.remove();
			TraversalNodeInfo currentNodeInfo = traversalInfo.get(currentNode);
			orderedTraversal.add(currentNodeInfo);
			
			for(int neighbour : g.neighbours(currentNode)) {
				TraversalNodeInfo neighbourInfo = traversalInfo.get(neighbour);
				
				if(neighbourInfo.getColor() == Color.WHITE) {
					neighbourInfo.setColor(Color.GRAY);
					neighbourInfo.setParent(currentNode);
					neighbourInfo.setLevel(currentNodeInfo.getLevel() + 1);
					
					int weight = g.getEdgeWeight(currentNode, neighbour);
					neighbourInfo.setDistance(currentNodeInfo.getDistance() + weight);
					
					q.add(neighbour);
				}
			}
			
			currentNodeInfo.setColor(Color.BLACK);
		}

		return orderedTraversal;
	}

}
