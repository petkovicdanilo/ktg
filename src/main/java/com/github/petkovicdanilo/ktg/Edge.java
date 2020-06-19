package com.github.petkovicdanilo.ktg;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Edge {

	private int firstNode;
	private int secondNode;
}
