package com.vmlens.executorService;

public interface Consumer<T> {

	void accept(T  event ) ;
	
	
}
