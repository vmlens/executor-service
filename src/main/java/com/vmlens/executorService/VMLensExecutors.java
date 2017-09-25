package com.vmlens.executorService;

import com.vmlens.executorService.internal.EventBusImpl;

/**
 * 
 * A high throughput java executor service. 
 * The vmlens executor service achieves three times higher 
 * throughput than the standard JDK executor service. 
 * The tradeoff is that the latency is much higher than that of the  standard JDK executor service. 
 * 
 * @author thomas
 *
 */

public class VMLensExecutors {
		
	
	
	public static <T> EventBus<T> createEventBus(EventFactory<T>  eventFactory  )
	{	 
		 return new EventBusImpl<T>(eventFactory);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
