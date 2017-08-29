package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.locks.LockSupport;

import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.service.StopService;

public class QueueReader<E>   {

	
	
	
	
	
	private final QueueSingleReader<E> queueSingleReader;
	private final LinkedNodeExctractor<E> extractor = new  LinkedNodeExctractor<E>();
	private final StopService stopService; 
	
	
	

	public QueueReader(QueueSingleReader<E> queueSingleReader, StopService stopService) {
		super();
		this.queueSingleReader = queueSingleReader;
		this.stopService = stopService;
	}




	/**
	 * 
	 * called by the worker threads for processing the events
	 * 
	 * 
	 * @param eventSink
	 *
	 * 
	 */
	
	
	public void processTillStopped(EventSink<E>  eventSink)
	{
		
		int waitCount = 0;
		
		while( true )
		{
			LinkedNode<E> current = extractTaskList();
			
			if( current == null )
			{
				if( stopService.stop)
				{
					eventSink.close();
					return;
				}
				else
				{	eventSink.onWait();
				
				
				   if( waitCount < 1000 )
				   {
					   waitCount++;
					   Thread.yield();
				   }
				   else if( waitCount < 10000)
				   {
					   LockSupport.parkNanos(1);
					   waitCount++;
				   }
				   else 
				   {
					   LockSupport.parkNanos(10);
					   //waitCount++;
				   }
//				   else
//				   {
//					   LockSupport.parkNanos(100);
//				   }
				
				
					
				}
				
			
				
				
			}
			else
			{
				waitCount = 0;
				
				while( current != null )
				{
					
					eventSink.execute(current.element);
					current = current.next;
					
					
					
				}
				
			}
		}
		
		
	
		
		
		
	}
	
	
	
	
	private synchronized LinkedNode<E> extractTaskList()
	{
		queueSingleReader.consumeQueueElements(extractor);
		return extractor.getAndCreateNew();
	}



	
	
	
	
	

}
