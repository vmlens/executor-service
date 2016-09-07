package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.RejectedExecutionException;


import com.vmlens.executorService.internal.service.DispatcherThread;

public class QueueManyWriters<E>  {
	
	private final ConcurrentLinkedList writingThreads;
	private final DispatcherThread dispatcherThread;
	
	
	
	public QueueManyWriters(ConcurrentLinkedList writingThreads, DispatcherThread dispatcherThread) {
		super();
		this.writingThreads = writingThreads;
		this.dispatcherThread = dispatcherThread;
	}



	private  final ThreadLocal<LastWrittenQueueNode<E>> lastWrittenQueueNode 
	= new ThreadLocal<LastWrittenQueueNode<E>>();
	
	
	
	public void accept(E element)
	{
		
		if( dispatcherThread.stop )
		{
			throw new RejectedExecutionException();
		}
		
		
		
	     QueueNode<E> current = new QueueNode<E>(element);
	     
	    
	
		if( lastWrittenQueueNode.get() == null )
		{
			writingThreads.append(current,Thread.currentThread().getId());
			lastWrittenQueueNode.set(new LastWrittenQueueNode(current));
		}
		else
		{
			
			lastWrittenQueueNode.get().last.next = current;
			lastWrittenQueueNode.get().last = current;
			
		}
	}
	

}
