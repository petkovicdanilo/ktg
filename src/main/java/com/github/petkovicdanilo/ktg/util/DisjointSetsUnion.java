package com.github.petkovicdanilo.ktg.util;

public class DisjointSetsUnion {

	private int[] rank, parent;
	
	public DisjointSetsUnion(int n) {
		rank = new int[n];
		parent = new int[n];
		
		// all sets contain one member
		for(int i = 0; i < n; ++i) {
			parent[i] = i;
		}
	}
	
	// find representative member for x's set
	public int find(int x) {
		if(parent[x] == x) {
			return x;
		}
		
		return find(parent[x]);
	}
	
	// make union of x's and y's sets
	public void union(int x, int y) {
		int xRoot = find(x);
		int yRoot = find(y);
		
		if(xRoot == yRoot) {
			return;
		}
		
		if(rank[xRoot] < rank[yRoot]) {
			parent[xRoot] = yRoot;
		}
		else {
			parent[yRoot] = xRoot;
			
			if(rank[xRoot] == rank[yRoot]) {
				rank[xRoot] = rank[xRoot] + 1;
			}
		}
	}
}
