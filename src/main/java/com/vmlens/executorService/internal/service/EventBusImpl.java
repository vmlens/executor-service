package com.vmlens.executorService.internal.service;

import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;

public class EventBusImpl<T> implements EventBus<T>   {

	private final QueueManyWriters<T> queueManyWriters;
	private final DispatcherThread dispatcherThread;
	
	
//	public createConsumerForThreadLocalStorage()
//	{
//		
//	}
	
	
	
	
	public EventBusImpl(QueueManyWriters<T> queueManyWriters, DispatcherThread dispatcherThread) {
		super();
		this.queueManyWriters = queueManyWriters;
		this.dispatcherThread = dispatcherThread;
	}

	@Override
	public void close() throws Exception {
		dispatcherThread.stop = true;
		
	}

	@Override
	public void accept(T obj) {
		queueManyWriters.push(obj);
		
	}
	
	
	
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		
		long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		while( ! dispatcherThread.terminated )
		{
			synchronized(dispatcherThread.terminationSignal)
			{
				unit.timedWait(dispatcherThread.terminationSignal, timeout);
			}
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return false;
			}
			
			
		}
		
		return dispatcherThread.terminated;
	}

}
