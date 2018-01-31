package com.vmlens.executorService.internal;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.EventSink;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.hash.TLongObjectHashMap;

public class EventBusImpl<T> implements EventBus<T> {
	
	
	
	
	
	
	
	private final int queueSize;
	private  TLongObjectHashMap<LinkedList<T>> threadId2Ring = new TLongObjectHashMap<LinkedList<T>>();
    private final Object threadId2PerThreadQueueLock = new Object();
	
    
    volatile boolean isStopped;
    
    
    
    private volatile boolean isTerminated ;
   
    
    
    
  
	 public EventBusImpl(int queueSize) {
		this.queueSize = queueSize;
		
	}


	public Consumer<T> newConsumerForThreadLocalStorage(Thread thread)
	{
		LinkedList<T> ring = new LinkedList<T>(this,thread,queueSize); 
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
		
			isTerminated = true;
		
	}


	@Override
	public boolean isTerminated() {
       return isTerminated;
		
		
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


	@Override
	public Consumer<T> newConsumer() {
		return new ThreadLocalConsumer<T>(this);
	}




	
	
}
