package com.vmlens.executorService.internal.service;

import java.util.Iterator;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;

public class WorkerThreadForEventConsumer<E> extends WorkerThread<E> {

	private final Consumer<E> eventConsumer;
	
	
	
	
	public WorkerThreadForEventConsumer(Consumer<E> eventConsumer) {
		super();
		this.eventConsumer = eventConsumer;
	}




	@Override
	void processList(LinkedNode<E> nodeList) {
		
		Iterator<E> it = new IteratorForLinkedNode<E>(nodeList);
		
		while(it.hasNext() )
		{
			eventConsumer.accept(it.next());
		}
		
		
		
		
	}

}
