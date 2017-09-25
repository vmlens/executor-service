package com.vmlens.executorService.internal;

import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ProzessAllRingsRunnable<T> implements Runnable {

	
	private final EventSink<T>  eventSink;
	private final EventBusImpl<T> eventBus;
	
	
	public ProzessAllRingsRunnable(EventSink<T> eventSink, EventBusImpl<T> eventBus) {
		super();
		this.eventSink = eventSink;
		this.eventBus = eventBus;
	}
	
	
	@Override
	public void run() {
		
		while(!  eventBus.isStopped  )
		{
			TLongObjectHashMap<ProzessOneRing<T>> threadId2ProzessOneRing= new TLongObjectHashMap<ProzessOneRing<T>>();
			eventBus.syndicate( threadId2ProzessOneRing );
			boolean nothingProzessed = false;
			
			
			while( ! nothingProzessed)
			{
				nothingProzessed = true;
				
				TLongObjectIterator<ProzessOneRing<T>> iterator = threadId2ProzessOneRing.iterator();
				
				while( iterator.hasNext() )
				{
					iterator.advance();
					ProzessOneRing<T> current = iterator.value();
					if(current.prozess(  eventSink  ) )
					{
						nothingProzessed = false;
					}
				}
			}
			
			eventSink.onWait();
			
		}
		
		eventSink.close();
		eventBus.runnableIsTerminated();
		
		
		
		
	}







}
