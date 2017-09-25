package com.vmlens.executorService.internal;

class RingElement<T> {
	
	
	volatile boolean isFull;
	volatile RingElement<T> next;
    final T event;
	
	public RingElement(T event) {
		super();
		this.event = event;
	}
	
	

}
