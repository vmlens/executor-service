package com.vmlens.executorService.internal;

import com.vmlens.executorService.Consumer;


public class Ring<T> implements Consumer<T>  {

	
	final RingElement<T> startNode;
	
	
	private RingElement<T> lastWritten;
	private final Thread thread;
	private final EventBusImpl<T> eventBus;
	
	public Ring(Thread thread,EventBusImpl<T> eventBus) {
		super();
		this.thread = thread;
		
		
		startNode = new RingElement<T>( );
		lastWritten = startNode;
		
		RingElement<T> current = startNode;
		
		this.eventBus = eventBus;
		
		for(int i = 0 ; i < 10 ; i++)
		{
			
			current.next = new RingElement<T>( );
			current = current.next;
			
		}
		
		
		
		
	}

	@Override
	public void accept(T event) {
		
		if( eventBus.isStopped )
		{
			return;
		}
		
		
		
		if( lastWritten == null)
		{
			lastWritten = startNode;
		}
		
		
		
		
		if(  lastWritten.state == RingElement.IS_EMPTY  )
		{
			lastWritten.event = event;
			
			lastWritten.state =  RingElement.IS_FULL;
			lastWritten = lastWritten.next;
		}
		else
		{
			
			if( lastWritten.compareAndSet(RingElement.IS_FULL, RingElement.IS_CHANGED) )
			{
				RingElement<T>  next = lastWritten.next;
				lastWritten.next =new RingElement<T>(  );
				lastWritten.next.next = next;
				
				RingElement<T> changed = lastWritten;
				
				lastWritten = lastWritten.next;
				lastWritten.event = event;
				
				changed.state = RingElement.IS_FULL;
				lastWritten.state =  RingElement.IS_FULL;
				
				
				lastWritten = lastWritten.next;
				
			}
			else
			{
				lastWritten.event = event;
				lastWritten.state =  RingElement.IS_FULL;
				lastWritten = lastWritten.next;
			}
			
			
		}	
		
		
		
	}
	
	
	
}
