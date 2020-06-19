package com.github.petkovicdanilo.ktg.traversal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TraversalNodeInfo {

	public enum Color {
		WHITE, GRAY, BLACK
	};
	
	private Integer parent;
	private Color color;
	private int distance;
}
