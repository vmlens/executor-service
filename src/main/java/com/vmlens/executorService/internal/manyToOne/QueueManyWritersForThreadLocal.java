package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.RejectedExecutionException;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.service.StopService;

/**
 * 
 * 
 * To be stored as thread local
 * 
 * @author thomas
 *
 */


public class QueueManyWritersForThreadLocal<E> implements Consumer<E> {
	
	
	private final ConcurrentLinkedList writingThreads;
	private final StopService stopService;
	
	
	
	public QueueManyWritersForThreadLocal(ConcurrentLinkedList writingThreads, StopService stopService) {
		super();
		this.writingThreads = writingThreads;
		this.stopService = stopService;
	}



	private  LastWrittenQueueNode<E> lastWrittenQueueNode;
	
	
	
	public void accept(E element)
	{
		
		if( stopService.stop )
		{
			stopService.onStop();
			return;
		}
		
		
		
	     QueueNode<E> current = new QueueNode<E>(element);
	     
	    
	
		if( lastWrittenQueueNode == null )
		{
			writingThreads.append(current,Thread.currentThread().getId());
			lastWrittenQueueNode = new LastWrittenQueueNode(current);
		}
		else
		{
			
			lastWrittenQueueNode.last.next = current;
			lastWrittenQueueNode.last = current;
			
		}
	}

}
