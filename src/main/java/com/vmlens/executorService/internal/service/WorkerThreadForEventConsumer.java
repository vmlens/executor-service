package com.vmlens.executorService.internal.service;

import java.util.Iterator;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;

public class WorkerThreadForEventConsumer<E> extends WorkerThread<E> {

	private final Consumer<Iterator<E>> eventConsumer;
	
	
	
	
	public WorkerThreadForEventConsumer(Consumer<Iterator<E>> eventConsumer) {
		super();
		this.eventConsumer = eventConsumer;
	}




	@Override
	void processList(LinkedNode<E> current) {
		
		eventConsumer.accept(new IteratorForLinkedNode<E>(current));
		
		
	}

}
