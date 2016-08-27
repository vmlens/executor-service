package com.vmlens.executorService.internal.manyToOne;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;

public class QueueReadingTest {

	
	int count = 0;
	
	@Test
	public void testReading()
	{
		ConcurrentLinkedList concurrentLinkedList  = new ConcurrentLinkedList();
		QueueNode first = new QueueNode(1);
		first.next =  new QueueNode(2);
		first.next.next =  new QueueNode(3);
		
		concurrentLinkedList.append(first, 1L);
		
		QueueSingleReader queueReader = new QueueSingleReader(concurrentLinkedList);
		
		
       queueReader.consumeQueueElements(
				
				new Consumer<Object>()
				{
					public void accept(Object in)
					{
						count++;
					}
				}
				
				
				
				);
		
		
		assertEquals( "count not equal"  , 3 , count );
	}
	
	
}
