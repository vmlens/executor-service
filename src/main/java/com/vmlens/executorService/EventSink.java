package com.vmlens.executorService;

public interface EventSink<T>   {
	
	
	void execute(T event);
	void close();
    void onWait();
}
