package com.github.petkovicdanilo.ktg.traversal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraversalNodeInfo {

	public enum Color {
		WHITE, GRAY, BLACK
	};
	
	private int node;
	private Integer parent;
	private Color color;
	private int distance;
	private int level;
}
