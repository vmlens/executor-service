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
		
			if( current == null )
			{
				current = ring.startNode;
			}
			
			while( current.state == RingElement.IS_FULL)
			{
				eventSink.execute( current.event );
				current.state = RingElement.IS_EMPTY;
				
				somethingProzessed = true;
				
				current = current.next;
				
				if( current == null )
				{
					current = ring.startNode;
				}
				
			}
			
			
			lastRead= current;
		
		
		
		return somethingProzessed;
	}
	
	
	
	
	
	
	
}
