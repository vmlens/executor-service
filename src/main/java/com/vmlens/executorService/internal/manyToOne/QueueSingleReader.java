package com.vmlens.executorService.internal.manyToOne;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.ListNode;

public class QueueSingleReader<E> extends ProcessConcurrentList<E> {
	
	
	
	

	public QueueSingleReader(ConcurrentLinkedList<E> externalWritingThreads) {
		super(externalWritingThreads);
		
	}
	
	
	
	
	
	public void consumeQueueElements(final Consumer<E> consumer)
	{
            foreach(
				
				new Consumer<ListNode<SingleReaderPointer<E>>>()
				{
					public void accept(ListNode<SingleReaderPointer<E>> in)
					{
						
						int readCount = 0;
						
						SingleReaderPointer<E> pointer = in.element;
						
						if( ! pointer.valueConsumed )
						{
							consumer.accept(pointer.value.element);
							readCount++;
							
							 pointer.valueConsumed = true;
							 
						}
						
						QueueNode<E> current = pointer.value.next;
						
						
						
						while(  current != null )
						{
							consumer.accept(current.element);
							readCount++;
							
							pointer.value = current;
							current = current.next;
						
						}
						
						
						in.backPressure.read(readCount);
						
						
						
					}
				}
				
				
				
				);
	}
	
	
	
	
	
	

}
