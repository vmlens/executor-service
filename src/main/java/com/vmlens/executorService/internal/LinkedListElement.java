package com.vmlens.executorService.internal;


class LinkedListElement<T> {
	volatile LinkedListElement<T> next;
    final T event;	
    public LinkedListElement(T event) {
		super();
		this.event = event;
	}
}
