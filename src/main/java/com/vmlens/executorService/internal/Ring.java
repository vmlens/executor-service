package com.vmlens.executorService.internal;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventFactory;
import com.vmlens.executorService.FillEvent;

public class Ring<T> implements Consumer<T>  {

	
	final RingElement<T> startNode;
	
	
	private RingElement<T> lastWritten;
	private final Thread thread;
	private final EventFactory<T> eventFactory;
	
	
	public Ring(Thread thread,EventFactory<T> eventFactory) {
		super();
		this.thread = thread;
		this.eventFactory = eventFactory;
		
		startNode = new RingElement<T>( eventFactory.create() );
		lastWritten = startNode;
		
		RingElement<T> current = startNode;
		
		
		for(int i = 0 ; i < 10 ; i++)
		{
			
			current.next = new RingElement<T>( eventFactory.create() );
			current = current.next;
			
		}
		
		
		
		
	}

	@Override
	public void accept(FillEvent<T> fillEvent) {
		
		if( lastWritten == null)
		{
			lastWritten = startNode;
		}
		
		
		
		
		if( ! lastWritten.isFull  )
		{
			fillEvent.fill( lastWritten.event );
			
			lastWritten.isFull = true;
			lastWritten = lastWritten.next;
			return;
			
		}
		else
		{
			RingElement<T> current = lastWritten;
			
			while( current.next != null && current.isFull )
			{
				current = current.next;
			}
				
			
			if(  !  current.isFull )
			{
				fillEvent.fill(  current.event );
				current.isFull = true;
				lastWritten = current.next;
				return;
			}
			else
			{
				current = startNode;
				
				while( current.next != null && current.isFull )
				{
					current = current.next;
				}
					
				
				if(  !  current.isFull )
				{
					fillEvent.fill(  current.event );
					current.isFull = true;
					lastWritten = current.next;
					return;
				}
				else
				{
					current.next = new  RingElement<T>( eventFactory.create() );
					current.isFull = true;
					lastWritten = current;
					return;
				}
				
				
			}
			
			
			
			
			
		}
		
		
		
	}
	
	
	
}
