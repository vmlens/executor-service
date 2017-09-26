package com.vmlens.executorService.internal;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventFactory;
import com.vmlens.executorService.FillEvent;

public class Ring<T> implements Consumer<T>  {

	
	final RingElement<T> startNode;
	
	
	private RingElement<T> lastWritten;
	private final Thread thread;
	private final EventFactory<T> eventFactory;
	private final EventBusImpl<T> eventBus;
	
	public Ring(Thread thread,EventFactory<T> eventFactory,EventBusImpl<T> eventBus) {
		super();
		this.thread = thread;
		this.eventFactory = eventFactory;
		
		startNode = new RingElement<T>( eventFactory.create() );
		lastWritten = startNode;
		
		RingElement<T> current = startNode;
		
		this.eventBus = eventBus;
		
		for(int i = 0 ; i < 10 ; i++)
		{
			
			current.next = new RingElement<T>( eventFactory.create() );
			current = current.next;
			
		}
		
		
		
		
	}

	@Override
	public void accept(FillEvent<T> fillEvent) {
		
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
			fillEvent.fill( lastWritten.event );
			
			lastWritten.state =  RingElement.IS_FULL;
			lastWritten = lastWritten.next;
		}
		else
		{
			
			if( lastWritten.compareAndSet(RingElement.IS_FULL, RingElement.IS_CHANGED) )
			{
				RingElement<T>  next = lastWritten.next;
				lastWritten.next =new RingElement<T>( eventFactory.create() );
				lastWritten.next.next = next;
				
				RingElement<T> changed = lastWritten;
				
				lastWritten = lastWritten.next;
				
				fillEvent.fill( lastWritten.event );
				
				changed.state = RingElement.IS_FULL;
				lastWritten.state =  RingElement.IS_FULL;
				
				
				lastWritten = lastWritten.next;
				
			}
			else
			{
				fillEvent.fill( lastWritten.event );
				
				lastWritten.state =  RingElement.IS_FULL;
				lastWritten = lastWritten.next;
			}
			
			
		}	
		
		
		
	}
	
	
	
}
