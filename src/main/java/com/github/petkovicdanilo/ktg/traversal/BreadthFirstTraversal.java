package com.github.petkovicdanilo.ktg.traversal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.github.petkovicdanilo.ktg.Graph;
import com.github.petkovicdanilo.ktg.traversal.TraversalNodeInfo.Color;

public class BreadthFirstTraversal implements TraversalAlgorithm {

	public Map<Integer, TraversalNodeInfo> traverse(
			Graph g, 
			int startingNode, 
			boolean allowZeroWeightEdges) 
	{
		Map<Integer, TraversalNodeInfo> traversalInfo = new HashMap<>();

		for (int node : g.nodes()) {
			TraversalNodeInfo nodeInfo = TraversalNodeInfo.builder()
					.parent(null)
					.color(Color.WHITE)
					.orderNumber(Integer.MAX_VALUE)
					.distance(Integer.MAX_VALUE)
					.build();

			traversalInfo.put(node, nodeInfo);
		}
		
		int nextOrderNumber = 0;
		
		traversalInfo.get(startingNode).setColor(Color.GRAY);
		traversalInfo.get(startingNode).setDistance(0);
		traversalInfo.get(startingNode).setLevel(0);
		
		Queue<Integer> q = new LinkedList<>();
		q.add(startingNode);
		
		while(!q.isEmpty()) { 
			int currentNode = q.remove();
			TraversalNodeInfo currentNodeInfo = traversalInfo.get(currentNode);
			
			for(int neighbour : g.neighbours(currentNode)) {
				TraversalNodeInfo neighbourInfo = traversalInfo.get(neighbour);
				
				if(neighbourInfo.getColor() == Color.WHITE) {
					int weight = g.getEdgeWeight(currentNode, neighbour);
					
					if(!allowZeroWeightEdges && weight == 0) {
						continue;
					}
					
					neighbourInfo.setColor(Color.GRAY);
					neighbourInfo.setParent(currentNode);
					neighbourInfo.setLevel(currentNodeInfo.getLevel() + 1);
					neighbourInfo.setDistance(currentNodeInfo.getDistance() + weight);
					
					q.add(neighbour);
				}
			}
			
			currentNodeInfo.setColor(Color.BLACK);
			currentNodeInfo.setOrderNumber(nextOrderNumber++);
		}

		return traversalInfo;
	}

	@Override
	public Map<Integer, TraversalNodeInfo> traverse(Graph g, int startingNode) {
		return traverse(g, startingNode, false);
	}

}
