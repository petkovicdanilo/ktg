package com.github.petkovicdanilo.ktg;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Edge {

	private int firstNode;
	private int secondNode;
	private int weight;
	
	public Edge reverse() {
		return new Edge(secondNode, firstNode, weight);
	}
}
