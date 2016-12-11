package com.vmlens.executorService.internal.service;

import java.util.Iterator;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;

public class WorkerThreadForEventConsumer<E> extends WorkerThread<E> {

	private final EventSink<E> eventConsumer;
	
	
	
	
	public WorkerThreadForEventConsumer(EventSink<E> eventConsumer) {
		super( "anarsoft");
		this.setDaemon(true);
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
	
	@Override
	  protected void onStop()
	    {
		  eventConsumer.close();
	    }

}
