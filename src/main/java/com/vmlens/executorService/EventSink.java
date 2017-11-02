package com.vmlens.executorService;

/**
 * 
 * Implement this interface to consume events. This class runs single threaded.
 * 
 */


public interface EventSink<T>   {
	
	/**
	 * 
	 * consumes one event.
	 * 
	 * @param event
	 */
	void execute(T event);
	
	/**
	 * 
	 * The event bus was shutdown
	 * 
	 */
	void close();
    
	
	/**
	 * There are currently no events to be processed
	 * 
	 * 
	 */
	void onWait();
}
