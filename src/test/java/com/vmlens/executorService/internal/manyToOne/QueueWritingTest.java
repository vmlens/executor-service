package com.vmlens.executorService.internal.manyToOne;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;
import com.vmlens.executorService.internal.service.DispatcherThread;
import com.vmlens.executorService.internal.service.StopService;

@RunWith(ConcurrentTestRunner.class)
public class QueueWritingTest {
	
	private static final int THREAD_COUNT = 5;
	private static final int ITERATION_COUNT = 10;
	private final ConcurrentLinkedList concurrentLinkedList  = new ConcurrentLinkedList();
	
	
	int count = 0;
	
	@Test
	@ThreadCount(THREAD_COUNT)
	public void testWriting()
	{
		StopService stopService = new StopService();   
		QueueManyWriters queueWriter = new QueueManyWriters(concurrentLinkedList,stopService);
		
		for( int i = 0 ; i < ITERATION_COUNT ; i++)
		{
			queueWriter.accept(i);
		}
		
	}

	
	@After
	public void check()
	{
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
		
		
		assertEquals( "count not equal"  , THREAD_COUNT * ITERATION_COUNT, count );
		
		
	}
	
	
}
