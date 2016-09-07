package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.RejectedExecutionException;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.service.DispatcherThread;

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
	private final DispatcherThread dispatcherThread;
	
	
	
	public QueueManyWritersForThreadLocal(ConcurrentLinkedList writingThreads, DispatcherThread dispatcherThread) {
		super();
		this.writingThreads = writingThreads;
		this.dispatcherThread = dispatcherThread;
	}



	private  LastWrittenQueueNode<E> lastWrittenQueueNode;
	
	
	
	public void accept(E element)
	{
		
		if( dispatcherThread.stop )
		{
			throw new RejectedExecutionException();
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
