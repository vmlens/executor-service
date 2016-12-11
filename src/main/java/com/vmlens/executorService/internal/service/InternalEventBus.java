package com.vmlens.executorService.internal.service;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.ListNode;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;
import com.vmlens.executorService.internal.manyToOne.QueueManyWritersForThreadLocal;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;
import com.vmlens.executorService.internal.oneToMany.QueueSingleWriter;
import com.vmlens.executorService.internal.oneToMany.ToBeConsumed;

public class InternalEventBus<T>   {
	
	
	private final StopService stopService;
	private final ConcurrentLinkedList writingThreads;
	private final QueueManyWriters<T> queueManyWriters;
	
	
	public InternalEventBus()
	{
		stopService = new 	StopServiceWithoutException();
		writingThreads = new ConcurrentLinkedList();
	    queueManyWriters = new QueueManyWriters<T>(writingThreads,stopService);
			
	}
	
	

	public void accept(T obj) {
		queueManyWriters.accept(obj);
		
	}
	
	
	
	
	public Consumer<T> newConsumerForThreadLocalStorage()
	{
		return new QueueManyWritersForThreadLocal<T>(writingThreads,stopService);
	}
	
	
	public Iterator<Thread> getThreadListForStart(Iterator<EventSink<T>>  consumerIterator )
	{

		 ListNode<ToBeConsumed<LinkedNode<T>>> start   = null;
		 ListNode<ToBeConsumed<LinkedNode<T>>> current = null;
		 
		 
		 LinkedNode<Thread> startThread = null;
		 LinkedNode<Thread> currentThread = null;
		 
		 while(  consumerIterator.hasNext()  )
		 {
			 EventSink<T> consumer = consumerIterator.next();
			 WorkerThreadForEventConsumer<T>  workerThreadForEventConsumer = new WorkerThreadForEventConsumer<T>(consumer); 
			 
			 if( start == null )
			 {
				 start =  new ListNode<ToBeConsumed<LinkedNode<T>>>(workerThreadForEventConsumer.getToBeConsumed()  , workerThreadForEventConsumer.getId());
				 current = start;
				 
				 startThread = new LinkedNode<Thread>(workerThreadForEventConsumer);
				 currentThread = startThread;
				 
			 }
			 else
			 {
				 current.next = new ListNode<ToBeConsumed<LinkedNode<T>>>(workerThreadForEventConsumer.getToBeConsumed()  , workerThreadForEventConsumer.getId());
				 current =  current.next;
				 
				 currentThread.next = new LinkedNode<Thread>(workerThreadForEventConsumer);
				 currentThread =  currentThread.next ;
				 
			 }
			 
			
			 
			 
			 
		 }
		 
		 QueueSingleWriter<LinkedNode<T>> queueSingleWriter = new QueueSingleWriter<LinkedNode<T>>(start);
		 QueueSingleReader<T> queueSingleReader = new QueueSingleReader<T>(writingThreads);	
		 DispatcherThread dispatcherThread = new DispatcherThread(queueSingleWriter,queueSingleReader,stopService);
		 
		 currentThread.next = new LinkedNode<Thread>(dispatcherThread );
		 
		  
		 return new IteratorForLinkedNode(startThread);
		 
	}
	
	
	
	
	public void close() throws Exception {
		stopService.stop = true;
		
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
