package com.github.petkovicdanilo.ktg.path;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.github.petkovicdanilo.ktg.Graph;

import lombok.AllArgsConstructor;
import lombok.Value;

public class Dijkstra implements SingleSourcePathAlgorithm {

	@Override
	public HashMap<Integer, PathNodeInfo> getPaths(Graph g, int startingNode) {
		HashMap<Integer, PathNodeInfo> paths = new HashMap<>();
		Set<Integer> visited = new HashSet<>();
		
		for(int node : g.nodes()) {
			PathNodeInfo pathInfo = new PathNodeInfo();
			
			pathInfo.setParent(null);
			pathInfo.setDistance(Integer.MAX_VALUE);
			
			paths.put(node, pathInfo);
		}
		
		paths.get(startingNode).setDistance(0);
		
		PriorityQueue<PathNode> pq = new PriorityQueue<>();
		paths.entrySet().stream()
			.forEach(path -> pq.add(
				new PathNode(path.getKey(), path.getValue()))
			);
		
		while(!pq.isEmpty()) {
			PathNode pathNode = pq.remove();
			int currentNode = pathNode.node;
			PathNodeInfo currentNodePathInfo = pathNode.getPathNodeInfo();
			
			for(int neighbour : g.neighbours(currentNode)) {
				if(!visited.contains(neighbour)) {
					PathNodeInfo neighbourPathInfo = paths.get(neighbour);
					
					int weight = g.getEdgeWeight(currentNode, neighbour);
					int neighbourDistance = currentNodePathInfo.getDistance(); 
					
					if(neighbourPathInfo.getDistance() > 
						neighbourDistance + weight) 
					{
						
						PathNode neighbourPathNode = 
							new PathNode(neighbour, neighbourPathInfo); 
						
						pq.remove(neighbourPathNode);
						
						neighbourPathInfo.setDistance(neighbourDistance + weight);
						neighbourPathInfo.setParent(currentNode);
						
						pq.add(neighbourPathNode);
					}	
				}
				
			}
			visited.add(currentNode);
		}
		
		return paths;
	}
	
	@Value
	@AllArgsConstructor
	private class PathNode implements Comparable<PathNode> {
		
		private int node;
		private PathNodeInfo pathNodeInfo;
		
		@Override
		public int compareTo(PathNode other) {
			int distance = pathNodeInfo.getDistance();
			int otherDistance = other.pathNodeInfo.getDistance();
			
			if(distance < otherDistance) {
				return -1;
			}
			if(distance > otherDistance) {
				return 1;
			}
			
			return 0;
		}
	}
}
