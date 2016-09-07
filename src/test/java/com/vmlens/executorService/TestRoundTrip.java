package com.vmlens.executorService;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

@RunWith(ConcurrentTestRunner.class)
public class TestRoundTrip {

	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutors.newHighThroughputExecutorService(THREAD_COUNT);
	
	
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
		
		
		
		
	}
	
	
	@After
	public void shutdownAndCheck() throws InterruptedException
	{
		
		executorService.shutdown();
		boolean terminated = executorService.awaitTermination(5,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
	
		
	}

}
