package com.vmlens.executorService.internal.oneToMany;

import java.util.concurrent.locks.LockSupport;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.ListNode;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.ProcessConcurrentList;



public class QueueSingleWriter<T>  {
	
	
	
	private final ListNode<ToBeConsumed<T>> start;

	
	
	
	
	
	
	public QueueSingleWriter(ListNode<ToBeConsumed<T>> start) {
		super();
		this.start = start;
	}

	
	
	public void sendStopMessageToAllAndWait(final T element)
	{
		boolean allHaveConsumedStopMessage = false;
		
		while( ! allHaveConsumedStopMessage )
		{
			allHaveConsumedStopMessage = true;
			
			
            ListNode<ToBeConsumed<T>> current = start;
			
			
			while( current != null )
			{
				if( current.element.toBeConsumed == null )
				{
					if( current.element.stopMessageSended )
					{
						
					}
					else
					{
						current.element.toBeConsumed = element;	
						 current.element.stopMessageSended = true;
						allHaveConsumedStopMessage = false;
					}
					
					
				
					
				}
				else
				{
					allHaveConsumedStopMessage = false;
				}
				
				
				
				current = current.next;
				
				
			}
			
			
			LockSupport.parkNanos(100);
			
			
		}
	}
	

	
	public void push(final T element)
	{
		
		while( true )
		{
			ListNode<ToBeConsumed<T>> current = start;
			
			
			while( current != null )
			{
				if( current.element.toBeConsumed == null )
				{
					
					current.element.toBeConsumed = element;
					return;
				}
				
				
				
				current = current.next;
				
				
			}
			
			
			LockSupport.parkNanos(100);
		}
		
		
		
		   
		
	}

}
