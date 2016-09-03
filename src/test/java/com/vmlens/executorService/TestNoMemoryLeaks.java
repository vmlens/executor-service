package com.vmlens.executorService;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

public class TestNoMemoryLeaks {

	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutorServiceFactory.create(2);
	
	@After
	public void tearDown() throws Exception {
		
		
		executorService.shutdown();
		boolean terminated = executorService.awaitTermination(10,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
		
		
		System.out.println("Please generate Heap Dump");
		
		Thread.sleep(2 * 60 * 1000);
		
	}

	
	
	
	@Test
	@ThreadCount(THREAD_COUNT)
	public void test() {
		
		
		for(int i = 0 ; i < 200000 ; i++)
		{
			executorService.execute(new BigTask());
		}
		
		
	}
	
	
	
}
