package com.vmlens.concurrent.hashmap.listBased;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;


@RunWith(ConcurrentTestRunner.class)
public class TestMultiThreadedAddAndRemove {

	ListBasedMap<Long,String> set = new ListBasedMap<Long,String>();
	
	
	@Test
	public void addAndRemove()
	{
		set.putIfAbsent(Thread.currentThread().getId() , "a" , TestListBasedSet.objectHashCodeAndEqualsLong);
		set.remove( Thread.currentThread().getId(), TestListBasedSet.objectHashCodeAndEqualsLong );
	}
	
	@After
	public void assertCount()
	{
		assertEquals( 0,  set.size() );
	}
	
	
}
