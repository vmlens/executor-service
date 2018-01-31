package com.vmlens.concurrent.hashmap.listBased;

import java.lang.reflect.Constructor;

public class ListEntry<K,V> {

	volatile LinkedListElement<K,V> next;
	
    private static final sun.misc.Unsafe U;
	private static final long NEXT;	
	   
	   static{
		   
		   try{
		   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
		   unsafeConstructor.setAccessible(true);
		   U = unsafeConstructor.newInstance(); 
		   NEXT = U.objectFieldOffset(ListEntry.class.getDeclaredField("next"));
		   }
		   catch(Exception e)
		   {
			   throw new RuntimeException(e);
		   }
		      
	   }
	   
	   public boolean setNextIfNull(LinkedListElement<K,V>  element)
	   {
		   return U.compareAndSwapObject(  this , NEXT, null, element);
	   }
	   
	   
	   
}
