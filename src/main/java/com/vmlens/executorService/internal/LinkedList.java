package com.vmlens.executorService.internal;

import com.vmlens.executorService.Consumer;


public class LinkedList<T> implements Consumer<T>  {

	
	
	
	volatile ListElementPointer<T> lastRead;
	private LinkedListElement<T> lastWritten;
	
	private final EventBusImpl<T> eventBus;
	 final Thread thread;
	
	
	public LinkedList(EventBusImpl<T> eventBus,Thread thread) {
		super();
		
		this.eventBus = eventBus;
		this.thread = thread;
	
		
	}

	@Override
	public void accept(T event) {
		
		if( eventBus.isStopped )
		{
			return;
		}
		
		
		LinkedListElement<T> linkedListElement= new LinkedListElement<T>(event);
		
		if( lastWritten == null )
		{
			lastWritten = linkedListElement;
			lastRead= new ListElementPointer<T>(lastWritten);
		}
		else
		{
			lastWritten.next = linkedListElement;
			lastWritten = lastWritten.next;
		}
	    
			
		
		
	}
	
	
	
}
