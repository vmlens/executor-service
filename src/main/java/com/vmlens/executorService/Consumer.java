package com.vmlens.executorService;

/**
 * 
 * The consumer to consume events. Call accept to publish an event.
 * 
 * 
 * @author thomas
 *
 * @param <T>
 */


public interface Consumer<T> {

	
	void accept(T  event );
    void acceptWithoutBackPressure(T  event);
	
	
}
