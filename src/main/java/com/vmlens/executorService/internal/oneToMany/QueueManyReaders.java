package com.vmlens.executorService.internal.oneToMany;

import java.util.concurrent.locks.LockSupport;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;


public class QueueManyReaders<T> {

	 public final ToBeConsumed toBeConsumed;
	


	
	
	
	
	public QueueManyReaders(ToBeConsumed toBeConsumed) {
		super();
		this.toBeConsumed = toBeConsumed;
	}







	public void proccessElement(Consumer objectConsumer)
	{
		
		
		int spinCount = 0;
		
		
		while( toBeConsumed.toBeConsumed == null )
		{
			
			if(spinCount < 1000 )
			{
				spinCount++;
			}
			else
			{
				LockSupport.parkNanos(100);
			}
			
			
		}
		
		
		objectConsumer.accept(toBeConsumed.toBeConsumed);
		
		toBeConsumed.toBeConsumed = null;
		
		
	}
	
}
