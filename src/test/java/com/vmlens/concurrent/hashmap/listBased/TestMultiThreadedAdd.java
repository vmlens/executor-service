package com.vmlens.concurrent.hashmap.listBased;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;


@RunWith(ConcurrentTestRunner.class)
public class TestMultiThreadedAdd {

	
	ListBasedMap<Long,String> set = new ListBasedMap<Long,String>();
	
	
	@Test
	public void add()
	{
		set.putIfAbsent( Thread.currentThread().getId() , "a" , TestListBasedSet.objectHashCodeAndEqualsLong );
	}
	
	
	@After
	public void assertCount()
	{
		assertEquals( 4 ,  set.size() );
	}
	
}
