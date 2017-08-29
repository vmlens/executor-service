package com.vmlens.executorService.internal.service;

import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.manyToOne.BackPressureStrategy;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;
import com.vmlens.executorService.internal.manyToOne.QueueManyWritersForThreadLocal;
import com.vmlens.executorService.internal.manyToOne.QueueReader;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;

public class EventBusImpl<T> implements EventBus<T>   {

	private final QueueManyWriters<T> queueManyWriters;
	private final StopService stopService;
	private final  ConcurrentLinkedList<T> writingThreads;
	private int activeReaderThreadCount = 0;
	
	public EventBusImpl(QueueManyWriters<T> queueManyWriters, StopService stopService,ConcurrentLinkedList<T>  writingThreads) {
		super();
		this.queueManyWriters = queueManyWriters;
		this.stopService = stopService;
		this.writingThreads = writingThreads;
		
	}

	@Override
	public void shutdown() {
		stopService.stop = true;
		
	}

	
	 void execute(T obj) {
		queueManyWriters.accept(obj);
		
	}
	
	
	public boolean isShutdown() {
		
		return stopService.stop;
	}

	public synchronized boolean isTerminated() {
		
		if( activeReaderThreadCount <= 0 )
		{
			this.notifyAll();
		}
		
		return activeReaderThreadCount <= 0;
	}
	
	
	public synchronized void decrementReaderCount()
	{
		
	
		
		activeReaderThreadCount--;
		
		
		if( activeReaderThreadCount <= 0 )
		{
			this.notifyAll();
		}
		
	}
	
	
	
    public  boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		
		long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		synchronized(this)
		{
		while( !  isTerminated() )
		{
			

				unit.timedWait(this, timeout);
			
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return false;
			}
			
			
		}
			
			
		}
		
		return isTerminated();
	}
    
    
   public  synchronized   void  start(Iterator<EventSink<T>>  consumerIterator,ThreadFactory threadFactory)
    {
    	 QueueSingleReader<T> queueSingleReader = new QueueSingleReader<T>(writingThreads);	
    	 QueueReader<T>  queueReader = new QueueReader<T> (queueSingleReader , stopService);
    	
    	
    	 while(  consumerIterator.hasNext()  )
		 {
			 EventSink<T> consumer = consumerIterator.next();
			 TaskExecutor<T> taskExecutor = new TaskExecutor<T>(queueReader , consumer,this);
			 Thread workerThread = threadFactory.newThread(taskExecutor);
			 workerThread.start();
			 
			 
			 activeReaderThreadCount++;
			 
			 
		 }
    }
    
    
    public Consumer<T> newConsumerForThreadLocalStorage(BackPressureStrategy backPressureStrategy)
	{
		return new QueueManyWritersForThreadLocal<T>(writingThreads,stopService,backPressureStrategy);
	}
	
    
    

}
