package com.vmlens.executorService;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;
import com.vmlens.executorService.internal.manyToOne.QueueReader;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;
import com.vmlens.executorService.internal.service.DefaultWorkerThreadFactory;
import com.vmlens.executorService.internal.service.EventBus2ExecutorServiceBridge;
import com.vmlens.executorService.internal.service.EventBusImpl;
import com.vmlens.executorService.internal.service.ExecutorServiceEventSink;
import com.vmlens.executorService.internal.service.IteratorForLinkedNode;
import com.vmlens.executorService.internal.service.StopService;
import com.vmlens.executorService.internal.service.StopServiceWithoutException;


/**
 * 
 * A high throughput java executor service. 
 * The vmlens executor service achieves three times higher 
 * throughput than the standard JDK executor service. 
 * The tradeoff is that the latency is much higher than that of the  standard JDK executor service. 
 * 
 * @author thomas
 *
 */

public class VMLensExecutors {
	

	
	/**
	 * 
	 * Creates a executor service that uses a fixed number of threads operating 
	 * off a shared unbounded queue.
	 * 
	 * 
	 * @param threadCount the number of threads in the pool
	 * @return the newly created executor service
	 */
	
	public static ExecutorService newHighThroughputExecutorService(int threadCount)
	{
		return newExecutorService( threadCount, true);
	}

	
	
	
	static ExecutorService newExecutorService(int threadCount, boolean startThreads)
	{
		LinkedNode<ExecutorServiceEventSink> start = new LinkedNode<ExecutorServiceEventSink>(new ExecutorServiceEventSink()); 
		LinkedNode<ExecutorServiceEventSink> current = start;
		
		for(int i = 1 ; i < threadCount; i++)
		{
			current.next = new LinkedNode<ExecutorServiceEventSink>(new ExecutorServiceEventSink());
			current = current.next;
			
			
		}
		
		
		EventBusImpl<Runnable> bus = (EventBusImpl) createEventBus(   );
		 
		 bus.start(  new  IteratorForLinkedNode(start) , new DefaultWorkerThreadFactory());
		 
		 return new EventBus2ExecutorServiceBridge(bus);
	}
	
	
	
	
	
	
	public static <T> EventBus<T> createEventBus( )
	{
		 ConcurrentLinkedList<T> writingThreads = new ConcurrentLinkedList<T>();
		 StopService stopService = new StopServiceWithoutException();
		

		 		 
		 
		 QueueManyWriters<T> queueManyWriters = new QueueManyWriters<T>(writingThreads,stopService);
		 
		 EventBus<T> eventBus =  new EventBusImpl<T>(queueManyWriters,stopService,writingThreads );
		 
		 
		
		 
		 
		 return eventBus;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
