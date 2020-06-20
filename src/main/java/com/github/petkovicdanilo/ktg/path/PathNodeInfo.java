package com.github.petkovicdanilo.ktg.path;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PathNodeInfo implements Comparable<PathNodeInfo> {

	private Integer parent;
	private int distance;
	
	@Override
	public int compareTo(PathNodeInfo other) {
		
		if(distance < other.distance) {
			return -1;
		}
		if(distance > other.distance) {
			return 1;
		}
		
		return 0;
	}
	
}
