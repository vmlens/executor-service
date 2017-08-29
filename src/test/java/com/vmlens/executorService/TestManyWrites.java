package com.vmlens.executorService;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;


@RunWith(ConcurrentTestRunner.class)
public class TestManyWrites {

	
	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutors.newHighThroughputExecutorService(2);
	private final AtomicInteger processCount = new AtomicInteger();
	
	
	@After
	public void shutdownAndCheck() throws InterruptedException
	{
		executorService.shutdown();
		boolean terminated = executorService.awaitTermination(60,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
		assertEquals( "not all threads processed" ,  THREAD_COUNT  , processCount.get());
		
	}

	@Test
	@ThreadCount(THREAD_COUNT)
	public void test() throws Exception
	{
		for(int i= 0 ; i < 5 ; i++)
		{
			Future<Long> result = executorService.submit(new Callable<Long>()
			{

				public Long call() {
					return 5L;
					
				}
		
			}
			
			
			);
	
	
	      result.get();
		}
		
		
		
		executorService.execute(new Runnable()
		{

			public void run() {
				processCount.incrementAndGet();
				
			}
	
		}
		
		
		);
	}

}
