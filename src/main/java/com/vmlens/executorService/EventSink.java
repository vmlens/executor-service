package com.vmlens.executorService;

public interface EventSink<T> extends Consumer<T>  {
	
	
	void close();

}
