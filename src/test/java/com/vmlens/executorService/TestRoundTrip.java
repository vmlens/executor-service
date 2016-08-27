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
public class TestRoundTrip {

	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutorServiceFactory.create(THREAD_COUNT);
	private final AtomicInteger processCount = new AtomicInteger();
	
	@Test
	@ThreadCount(THREAD_COUNT)
	public void test() throws Exception
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
	
	
	@After
	public void shutdownAndCheck() throws InterruptedException
	{
		
		executorService.shutdown();
		boolean terminated = executorService.awaitTermination(5,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
	
		
	}

}
