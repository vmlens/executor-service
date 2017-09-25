package com.vmlens.executorService.internal;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.EventFactory;
import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class EventBusImpl<T> implements EventBus<T> {
	
	
	private final  EventFactory<T>  eventFactory;
	
	
	
	
	
	private final TLongObjectHashMap<Ring<T>> threadId2Ring = new TLongObjectHashMap<Ring<T>>();
    private final Object threadId2PerThreadQueueLock = new Object();
	
    
    volatile boolean isStopped;
    
    
    
    private boolean isTerminated ;
    private final Object isTerminatedLock = new Object();
    
    
    
  
	 public EventBusImpl(EventFactory<T> eventFactory) {
		super();
		this.eventFactory = eventFactory;
	}


	public Consumer<T> newConsumerForThreadLocalStorage(Thread thread)
	 {
		 
		Ring<T> ring = new Ring<T>(thread,eventFactory); 
		 
		synchronized(threadId2PerThreadQueueLock)
		{
			threadId2Ring.put( thread.getId() , ring );	
		}
		
		
		return ring;
		
		
		
	 }


	public void syndicate(TLongObjectHashMap<ProzessOneRing<T>> threadId2ProzessOneRing) {
		
		
		synchronized(threadId2PerThreadQueueLock)
		{
			
			TLongObjectIterator<Ring<T>> iterator = threadId2Ring.iterator();
			
			while( iterator.hasNext() )
			{
				iterator.advance();
				
				if( ! threadId2ProzessOneRing.contains(  iterator.key() )  )
				{
					threadId2ProzessOneRing.put(   iterator.key()  , new ProzessOneRing(iterator.value()) );
				}
			
			}
			;
			
		}
		
		
		
	}


	public void runnableIsTerminated() {
		synchronized(isTerminatedLock)
		{
			isTerminated = true;
			isTerminatedLock.notifyAll();
		}
		
	}


	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		synchronized(isTerminatedLock)
		{
		while( ! isTerminated )
		{
			

				unit.timedWait(isTerminatedLock, timeout);
			
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return isTerminated;
			}
			
			
		}
			
		return isTerminated;
			
		}
		
		
	}


	@Override
	public void shutdown() {
		isStopped = true;
		
	}


	@Override
	public void start(EventSink<T> consumer, ThreadFactory threadFactory) {
		 Thread workerThread = threadFactory.newThread(new ProzessAllRingsRunnable<T>(consumer,this));
		 workerThread.start();
		
	}




	
	
}
