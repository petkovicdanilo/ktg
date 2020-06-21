package com.github.petkovicdanilo.ktg.flow;

import com.github.petkovicdanilo.ktg.Graph;

import lombok.Value;

@Value
public class MaxFlow {
	private Graph flowGraph;
	private int flow;
}
