package com.vmlens.executorService.internal;

import java.lang.reflect.Constructor;

class RingElement<T> {
	
	
	public static final int IS_EMPTY = 0;
	public static final int IS_FULL = 1;
	public static final int IS_CHANGED = 2;
	
	volatile int state;
	volatile RingElement<T> next;
    final T event;
	
	public RingElement(T event) {
		super();
		this.event = event;
	}
	
	

	 public final boolean compareAndSet(int expect, int update) {
	        return UNSAFE.compareAndSwapInt(this, stateOffset, expect, update);
	    }
	
	private static long stateOffset;
	private static final sun.misc.Unsafe UNSAFE;
	
	   
	   static{
		   
		   try{
		   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
		   unsafeConstructor.setAccessible(true);
		   UNSAFE = unsafeConstructor.newInstance();
		   
		   stateOffset = UNSAFE.objectFieldOffset
		            (RingElement.class.getDeclaredField("state"));
		   
		   }
		   catch(Exception e)
		   {
			   throw new RuntimeException(e);
		   }
		   
		   
	   }

}
