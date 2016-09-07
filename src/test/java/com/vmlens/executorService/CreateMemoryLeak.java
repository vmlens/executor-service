package com.vmlens.executorService;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;



@RunWith(ConcurrentTestRunner.class)
public class CreateMemoryLeak {


	private static final int THREAD_COUNT = 5;
	private final ExecutorService executorService = VMLensExecutors.newExecutorService(2,false);
	
	@After
	public void tearDown() throws Exception {
		
		
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
