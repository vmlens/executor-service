package com.vmlens.executorService;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

@RunWith(ConcurrentTestRunner.class)
public class TestFireAndForget {

	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutors.newHighThroughputExecutorService(2);
	private final AtomicInteger processCount = new AtomicInteger();
	
	@Test
	@ThreadCount(THREAD_COUNT)
	public void test() {
		
		executorService.execute(new Runnable()
				{

					public void run() {
						processCount.incrementAndGet();
						
					}
			
				}
				
				
				);
		
		
	}
	
	
	@After
	public void shutdownAndCheck() throws InterruptedException
	{
		
		executorService.shutdown();
		boolean terminated = executorService.awaitTermination(5,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
		assertEquals( "not all threads processed" ,  THREAD_COUNT  , processCount.get());
		
	}

}
