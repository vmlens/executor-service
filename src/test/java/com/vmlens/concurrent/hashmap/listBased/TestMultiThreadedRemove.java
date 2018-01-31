package com.vmlens.concurrent.hashmap.listBased;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;



@RunWith(ConcurrentTestRunner.class)
public class TestMultiThreadedRemove {

	ListBasedMap<Integer,String> set = new ListBasedMap<Integer,String>();
	private AtomicInteger counter = new AtomicInteger();
	
	
	
	@Before
	public void setup()
	{
		for( int i = 0; i < 4 ; i++)
		{
			set.putIfAbsent( i , "a" , TestListBasedSet.objectHashCodeAndEqualsInteger );
		}
	
		
	}
	
	
	@Test
	public void remove()
	{
		int myCount = counter.getAndIncrement();
		set.remove(myCount, TestListBasedSet.objectHashCodeAndEqualsInteger);
	
		
		
	}
	
	
	@After
	public void assertCount()
	{
		assertEquals( 0 ,   set.size() );
	}
	
}
