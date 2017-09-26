package com.vmlens.executorService.internal;

import java.lang.reflect.Constructor;

import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ProzessAllRingsRunnable<T> implements Runnable {

	
	private final EventSink<T>  eventSink;
	private final EventBusImpl<T> eventBus;
	
	private static final sun.misc.Unsafe UNSAFE;
	
	   
	   static{
		   
		   try{
		   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
		   unsafeConstructor.setAccessible(true);
		   UNSAFE = unsafeConstructor.newInstance();
		   }
		   catch(Exception e)
		   {
			   throw new RuntimeException(e);
		   }
		   
		   
	   }
	
	public ProzessAllRingsRunnable(EventSink<T> eventSink, EventBusImpl<T> eventBus) {
		super();
		this.eventSink = eventSink;
		this.eventBus = eventBus;
	}
	
	private void execute()
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
	}
	
	
	
	@Override
	public void run() {
		
		while(!  eventBus.isStopped  )
		{
			
			 execute();
			 
			 UNSAFE.park(false, 1);
			
			eventSink.onWait();
			
		}
		
		
//		for(int i = 0 ; i < 10; i++)
//		{
//			execute();
//			 
//			 UNSAFE.park(false, 10);
//		}
		
		
		eventSink.close();
		eventBus.runnableIsTerminated();
		
		
		
		
	}







}
