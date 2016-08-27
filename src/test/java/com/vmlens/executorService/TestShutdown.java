package com.vmlens.executorService;

import static org.junit.Assert.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.junit.Test;

public class TestShutdown {

	private ExecutorService executorService =  VMLensExecutorServiceFactory.create(5);
	
	
	

	@Test
	public void test() {
		executorService.shutdown();
		
		boolean exceptionCatched = false;
		
		try{
		
		executorService.execute(new Runnable()
		{

			public void run() {
				
				
			}
	
		}
		
		
		);
		
		
		}
		catch(RejectedExecutionException exp)
		{
			exceptionCatched = true;
		}
		
		assertTrue("no exception thrown" ,exceptionCatched);
		
	}

}
