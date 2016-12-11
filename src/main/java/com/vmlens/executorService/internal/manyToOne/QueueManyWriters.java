package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.RejectedExecutionException;


import com.vmlens.executorService.internal.service.StopService;

public class QueueManyWriters<E>  {
	
	private final ConcurrentLinkedList writingThreads;
	private final StopService stopService;
	
	
	
	public QueueManyWriters(ConcurrentLinkedList writingThreads, StopService stopService) {
		super();
		this.writingThreads = writingThreads;
		this.stopService = stopService;
	}



	private  final ThreadLocal<LastWrittenQueueNode<E>> lastWrittenQueueNode 
	= new ThreadLocal<LastWrittenQueueNode<E>>();
	
	
	
	public void accept(E element)
	{
		
		if( stopService.stop )
		{
			stopService.onStop();
			return;
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
