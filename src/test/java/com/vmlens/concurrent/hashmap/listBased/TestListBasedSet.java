package com.vmlens.concurrent.hashmap.listBased;

import org.junit.Test;

import com.vmlens.concurrent.hashmap.KeyEqualAndHashCode;
import com.vmlens.concurrent.hashmap.ObjectHashCodeAndEquals;

import static org.junit.Assert.*;

public class TestListBasedSet {
	
	
	public static final KeyEqualAndHashCode<String> objectHashCodeAndEqualsString = new ObjectHashCodeAndEquals<String>(); 
	public static final KeyEqualAndHashCode<Integer> objectHashCodeAndEqualsInteger = new ObjectHashCodeAndEquals<Integer>();
	public static final KeyEqualAndHashCode<Long> objectHashCodeAndEqualsLong = new ObjectHashCodeAndEquals<Long>();
	
	
	public static final KeyEqualAndHashCode<String> allKeysEqual = new KeyEqualAndHashCode<String>()
			{

				@Override
				public int keyHashCode(String key) {
					return 0;
				}

				@Override
				public boolean keyEquals(String first, String second) {
					
					return true;
				}
		
			};
	
	
	@Test
	public void addAllKeysEqual()
	{
		ListBasedMap<String,String> set = new ListBasedMap<String,String>();
		
		set.putIfAbsent(   "a" , "a"  , allKeysEqual );
		String element = set.putIfAbsent( "b" , "b" , allKeysEqual );
		
		assertEquals(  "a" ,   element );
		
		set.remove( "x" , allKeysEqual );
		
		assertEquals(   0  , set.size() );
	}
	
	
	
	
	
	
	
	@Test
	public void add()
	{
		ListBasedMap<String,String> set = new ListBasedMap<String,String>();
		
		set.putIfAbsent(   "a" , "a"  , objectHashCodeAndEqualsString );
		String element = set.putIfAbsent( "a" , "b"  , objectHashCodeAndEqualsString);
		
		assertEquals(  "a" ,   element );
	}
	
	@Test
	public void remove()
	{
		ListBasedMap<String,String> set = new ListBasedMap<String,String>();
		
		set.putIfAbsent(   "a" , "a" , objectHashCodeAndEqualsString );
		
		set.remove( "a", objectHashCodeAndEqualsString );
		set.remove( "a", objectHashCodeAndEqualsString );
		
		String element = set.putIfAbsent( "a" , "b" , objectHashCodeAndEqualsString);
		
		assertEquals(  "b" ,   element );
	}

	
	
	@Test
	public void size()
	{
		ListBasedMap<String,String> set = new ListBasedMap<String,String>();
		
		set.putIfAbsent(   "a" , "a" , objectHashCodeAndEqualsString  );
		
		set.remove( "a", objectHashCodeAndEqualsString );
		set.remove( "a", objectHashCodeAndEqualsString );
		
		set.putIfAbsent( "a" , "b" , objectHashCodeAndEqualsString );
		
		assertEquals(   1 , set.size()  );
		
		set.putIfAbsent( "a" , "b" , objectHashCodeAndEqualsString );
		set.putIfAbsent( "b" , "b" , objectHashCodeAndEqualsString );
		set.putIfAbsent( "c" , "b" , objectHashCodeAndEqualsString);
		
		for(int i = 0 ; i < 10 ; i++)
		{
			set.putIfAbsent( "b" , "b" , objectHashCodeAndEqualsString);
		}
		
		assertEquals(   3  , set.size() );
	}
	
	
	
}
