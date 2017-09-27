package com.vmlens.executorService.internal;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class EventBusImpl<T> implements EventBus<T> {
	
	
	
	
	
	
	
	
	private  TLongObjectHashMap<LinkedList<T>> threadId2Ring = new TLongObjectHashMap<LinkedList<T>>();
    private final Object threadId2PerThreadQueueLock = new Object();
	
    
    volatile boolean isStopped;
    
    
    
    private boolean isTerminated ;
    private final Object isTerminatedLock = new Object();
    
    
    
  
	 public EventBusImpl() {
		super();
	}


	public Consumer<T> newConsumerForThreadLocalStorage(Thread thread)
	 {
		 
		LinkedList<T> ring = new LinkedList<T>(this,thread); 
		 
		synchronized(threadId2PerThreadQueueLock)
		{
			threadId2Ring.put( thread.getId() , ring );	
		}
		
		
		return ring;
		
		
		
	 }


	public void syndicate(TLongObjectHashMap<ProzessOneList<T>> threadId2ProzessOneRing) {
		
		
		synchronized(threadId2PerThreadQueueLock)
		{
			
			TLongObjectIterator<LinkedList<T>> iterator = threadId2Ring.iterator();
			
			while( iterator.hasNext() )
			{
				iterator.advance();
				
					threadId2ProzessOneRing.put(   iterator.key()  , new ProzessOneList<T>(iterator.value()) );
				
			
			};
			
			
		threadId2Ring = new TLongObjectHashMap<LinkedList<T>>();
			
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
		 Thread workerThread = threadFactory.newThread(new ProzessAllListsRunnable<T>(consumer,this));
		 workerThread.start();
		
	}




	
	
}
