package com.vmlens.executorService.internal;

import com.vmlens.executorService.EventSink;

public class ProzessOneRing<T> {

	
	private RingElement<T> lastRead;	
	private final Ring<T> ring;
	
	public ProzessOneRing(Ring<T> ring) {
		super();
		this.ring = ring;
	    lastRead = ring.startNode;
	
	}

	public boolean prozess(EventSink<T> eventSink) {
		
		boolean somethingProzessed = false;
		
		 RingElement<T> current = lastRead;
		
		for(int i = 0 ; i < 10 ; i++)
		{
		
			if( current == null )
			{
				current = ring.startNode;
			}
			
			if( current.isFull)
			{
				eventSink.execute( current.event );
				current.isFull = false;
				
				somethingProzessed = true;
				
			}
				
			current = current.next;
		
			
		}
		
		
		lastRead = current;
		
		
		return somethingProzessed;
	}
	
	
	
	
	
	
	
}
