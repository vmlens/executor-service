package com.vmlens.executorService;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;



public interface EventBus<T> {
	
	
	boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
	void shutdown();
	void start(EventSink<T>  consumer,ThreadFactory threadFactory);
	
	
	
	 Consumer<T> newConsumerForThreadLocalStorage(Thread thread);
		
}
