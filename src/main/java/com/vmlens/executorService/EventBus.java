package com.vmlens.executorService;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;



public interface EventBus<T> {
	
	
	/**
	 * returns true when all eventswhere prozessed after shutdown 
	 * 
	 */
	
	boolean isTerminated();
	
	
	/**
	 * After shutdown no more events will be accepted by the event bus. All events published after this call will be silently ignored.
	 * 
	 * 
	 */
	
	void shutdown();
	
	
	/**
	 * 
	 * Starts the consumer thread for receiving events
	 * 
	 * @param consumer
	 * @param threadFactory
	 */
	
	void start(EventSink<T>  consumer,ThreadFactory threadFactory);
	
	/**
	 * 
	 * Creates a new Consumer to be stored in a thread local field.
	 * 
	 * @param thread
	 * @return
	 */
	 Consumer<T> newConsumerForThreadLocalStorage(Thread thread);
		
	/**
	 * 
	 * Creates a new Consumer which is stored in ThreadLoal field.
	 * 
	 * @return
	 */
	 Consumer<T> newConsumer();
}
