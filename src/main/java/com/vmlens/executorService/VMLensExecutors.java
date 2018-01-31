package com.vmlens.executorService;

import com.vmlens.executorService.internal.EventBusImpl;

/**
 * 
 *  An Executor Service similar to the JDK ExecutorService but with different API. Supports one reading and multiple 
 *  writing threads.  
 * 
 * 
 * @author thomas
 *
 */

public class VMLensExecutors {
		
	/**
	 * 
	 * Creates a new event bus for publishing events to the background queue.
	 * 
	 * 
	 */
	
	public static <T> EventBus<T> createEventBus(int queueSize )
	{	 
		 return new EventBusImpl<T>(queueSize);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
