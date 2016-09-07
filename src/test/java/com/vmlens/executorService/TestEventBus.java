package com.vmlens.executorService;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;

@RunWith(ConcurrentTestRunner.class)
public class TestEventBus {
	
	private static final int THREAD_COUNT = 5;
	private static final int ITERATION_COUNT = 5;
	private  EventBus<String> eventBus;
	private final AtomicInteger sendCount = new AtomicInteger();
	private final AtomicInteger receivedCount = new AtomicInteger();
	
    @Before
    public void startUp()
    {
	    List<Consumer<Iterator<String>>> consumerList = new LinkedList<Consumer<Iterator<String>>>();
    	
	    consumerList.add(
	    		
	    		new Consumer<Iterator<String>>()
	    		{

					@Override
					public void accept(Iterator<String> it) {
						
						while( it.hasNext() )
						{
							it.next();
							receivedCount.incrementAndGet();
							
						}
						
					}
	    			
	    		}
	    		
	    		
	    		
	    		);
    	
    	eventBus = VMLensExecutors.<String>createEventBus(consumerList.iterator());
    }
			
			


	@Test
	@ThreadCount(THREAD_COUNT)
	public void test() {
		
		for( int i = 0 ; i < ITERATION_COUNT ; i++)
		{
			eventBus.accept("Test");
			sendCount.incrementAndGet();
			LockSupport.parkNanos(1000);
		}
		
	}

	
	
	
	@After
	public void checkCounts() throws Exception {
		
		
		
		
		
		
		eventBus.close();
        boolean terminated = eventBus .awaitTermination(5,  TimeUnit.SECONDS );
		
		
		assertTrue( "is not terminated "  + terminated,  terminated);
		
		
		
		assertEquals( "not all sended" ,  THREAD_COUNT  * ITERATION_COUNT  , receivedCount.get());
		assertEquals( "not all received" ,  THREAD_COUNT  * ITERATION_COUNT  , sendCount.get());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
