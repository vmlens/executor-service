package com.vmlens.executorService.internal.service;

import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;
import com.vmlens.executorService.internal.manyToOne.QueueManyWritersForThreadLocal;

public class EventBusImpl<T> implements EventBus<T>   {

	private final QueueManyWriters<T> queueManyWriters;
	private final StopService stopService;
	private final ConcurrentLinkedList writingThreads;
	
	
	
//	public Consumer<T> newConsumerForThreadLocalStorage()
//	{
//		return new QueueManyWritersForThreadLocal<T>(writingThreads,dispatcherThread);
//	}
	
	
	
	
	public EventBusImpl(QueueManyWriters<T> queueManyWriters, StopService stopService,ConcurrentLinkedList writingThreads) {
		super();
		this.queueManyWriters = queueManyWriters;
		this.stopService = stopService;
		this.writingThreads = writingThreads;
	}

	@Override
	public void close() throws Exception {
		stopService.stop = true;
		
	}

	@Override
	public void accept(T obj) {
		queueManyWriters.accept(obj);
		
	}
	
	
	
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		
		long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		while( ! stopService.terminated )
		{
			synchronized(stopService.terminationSignal)
			{
				unit.timedWait(stopService.terminationSignal, timeout);
			}
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return false;
			}
			
			
		}
		
		return stopService.terminated;
	}

}
