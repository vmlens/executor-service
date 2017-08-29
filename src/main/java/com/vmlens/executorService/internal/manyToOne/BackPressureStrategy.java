package com.vmlens.executorService.internal.manyToOne;

public interface BackPressureStrategy {

	void writeOne();
	void read(int amount);
	
	
}
