package com.vmlens.executorService.internal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.ListNode;
import com.vmlens.executorService.internal.manyToOne.ConcurrentLinkedList;
import com.vmlens.executorService.internal.manyToOne.QueueNode;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;


@RunWith(ConcurrentTestRunner.class)
public class ConcurrentLinkedListTest {

	
	private final ConcurrentLinkedList<QueueNode> concurrentLinkedList  = new ConcurrentLinkedList<QueueNode>();
	private static final int THREAD_COUNT = 5;
	
	
	@Test
	@ThreadCount(THREAD_COUNT)
	public void testAppend() {
		concurrentLinkedList.append(new QueueNode(Thread.currentThread().getName()  ),Thread.currentThread().getId() );
	}
	
	int count = 0;
	
	@After
	public void check()
	{
		QueueSingleReader queueReader = new QueueSingleReader(concurrentLinkedList);
		
		
		
		queueReader.foreach(
				
				new Consumer<ListNode<QueueNode>>()
				{
					public void accept(ListNode<QueueNode> in)
					{
						count++;
					}
				}
				
				
				
				);
		
		
		assertEquals( "count not equal"  , THREAD_COUNT , count );
		
		
	}

}
