package com.vmlens.executorService;

import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.internal.manyToOne.BackPressureStrategy;

public interface EventBus<T> {
	
	
	boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
	void shutdown();
	void start(Iterator<EventSink<T>>  consumerIterator,ThreadFactory threadFactory);
	
	
	//void execute(T obj);
	boolean isShutdown();
	boolean isTerminated();
	
	 Consumer<T> newConsumerForThreadLocalStorage(BackPressureStrategy BackPressureStrategy);
		
}
