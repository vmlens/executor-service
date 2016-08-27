package com.vmlens.executorService.internal;

public interface ArrayAndThreadIdConsumer<T> {
	
	void apply(T[] obj,long threadId);

}
