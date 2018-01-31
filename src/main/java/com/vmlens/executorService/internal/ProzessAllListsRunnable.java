package com.vmlens.executorService.internal;

import java.lang.reflect.Constructor;

import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ProzessAllListsRunnable<T> implements Runnable {

	
	private final EventSink<T>  eventSink;
	private final EventBusImpl<T> eventBus;
	
	
	private int checkThreadCount = 0;
	
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
	
	public ProzessAllListsRunnable(EventSink<T> eventSink, EventBusImpl<T> eventBus) {
		super();
		this.eventSink = eventSink;
		this.eventBus = eventBus;
	}
	
	private NonVolatileLinkedListElement<T>  executeAllLists(TLongObjectHashMap<ProzessOneList<T>> threadId2ProzessOneRing)
	{
		eventBus.syndicate( threadId2ProzessOneRing );
		

		 
			
		TLongObjectIterator<ProzessOneList<T>> iterator = threadId2ProzessOneRing.iterator();
				while( iterator.hasNext() )
				{
					iterator.advance();
					ProzessOneList<T> current = iterator.value();
					current.readCount = 0;
					
					current.prozessWithReadCount(  eventSink  );
					
				}
			
	   for(int i = 0 ; i < 10 ; i++)
	    {
			 iterator = threadId2ProzessOneRing.iterator();
			while( iterator.hasNext() )
			{
				iterator.advance();
				ProzessOneList<T> current = iterator.value();	
				current.prozessWithReadCount(  eventSink  );
				
			}
			
			
		
	    }
		
	   
	   checkThreadCount++;
	   
	   boolean ckeckTreads = false;
	   
	   if(checkThreadCount > 200)
	   {
		   checkThreadCount = 0;
		   ckeckTreads = true;
	   }
	   
	   
	   
	   
	    int maxReadCount = 0;
	    iterator = threadId2ProzessOneRing.iterator();
		while( iterator.hasNext() )
		{
			iterator.advance();
			ProzessOneList<T> current = iterator.value();	
			
			if(current.readCount > maxReadCount)
			{
				maxReadCount = current.readCount;
			}
			
			if(ckeckTreads && current.readCount  == 0)
			{
				if( ! current.list.thread.isAlive() )
				{
					iterator.remove();
				}
				
				
				
			}
			
			
			
		}
	   
	   
	   if(maxReadCount > 0)
	   {
		   int tenPercent = maxReadCount - ((int)(maxReadCount/10));
		   NonVolatileLinkedListElement<T> start = null;
		   NonVolatileLinkedListElement<T> currentResult = null;
		   
		     iterator = threadId2ProzessOneRing.iterator();
			while( iterator.hasNext() )
			{
				iterator.advance();
				ProzessOneList<T> current = iterator.value();
				
				if( current.readCount > tenPercent )
				{
					if( start == null )
					{
						start = new NonVolatileLinkedListElement<T>(current);
						currentResult = start;
					}
					else
					{
						currentResult.next = new NonVolatileLinkedListElement<T>(current);
						currentResult = currentResult.next;
					}
					
					
					
				}
			}	
				
		   
		   return start;
		   
		   
	   }
	   else
	   {
		 return  null;
	   }
	   
		
		
		
	}
	
	
	private void prozessHotlist( NonVolatileLinkedListElement<T> start)
	{
		for(int i = 0 ; i < 5 ; i++)
		{
			NonVolatileLinkedListElement<T> current = start;
			
			while( current != null )
			{
				
				current.prozessOneList.prozessWithoutReadCount(  eventSink  );
				
				current = current.next;
			}
			
			
		}
	}
	
	
	public void oneIteration( TLongObjectHashMap<ProzessOneList<T>> threadId2ProzessOneRing)
	{
		
		 NonVolatileLinkedListElement<T> hotList = executeAllLists(threadId2ProzessOneRing);
		 
		 
		 if(hotList == null)
		 {
			 UNSAFE.park(false, 1);
				
				eventSink.onWait();
		 }
		 else
		 {
			 prozessHotlist(hotList);
		 }
	}
	
	
	@Override
	public void run() {
		
		
		
		
		TLongObjectHashMap<ProzessOneList<T>> threadId2ProzessOneRing= new TLongObjectHashMap<ProzessOneList<T>>();
		
		while(!  eventBus.isStopped  )
		{
		
			 
			oneIteration(threadId2ProzessOneRing);
			
		}
		
		
//		for(int i = 0 ; i < 10; i++)
//		{
//		      // System.out.println(	execute(threadId2ProzessOneRing)); ergibt true
//			
//			executeAllLists(threadId2ProzessOneRing);
//			 
//			 UNSAFE.park(false, 1);
//		}
		
		NonVolatileLinkedListElement<T> hotList = executeAllLists(threadId2ProzessOneRing);
		
		while(hotList != null)
		{
			hotList = executeAllLists(threadId2ProzessOneRing);
			
			  NonVolatileLinkedListElement<T>  current = hotList;
			  
			  while( current != null )
			  {
				  
				  System.out.println( current.prozessOneList.readCount );
				  
				  current = current.next;
			  }
			  
			
			
		}
		
	
				
				
		eventSink.close();
		eventBus.runnableIsTerminated();
		
		
		
		
	}







}
