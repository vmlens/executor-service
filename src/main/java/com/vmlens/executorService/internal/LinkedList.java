package com.vmlens.executorService.internal;

import java.lang.reflect.Constructor;

import com.vmlens.executorService.Consumer;


public class LinkedList<T> implements Consumer<T>  {
	volatile ListElementPointer<T> lastRead;
	private LinkedListElement<T> lastWritten;
	private final EventBusImpl<T> eventBus;
	private volatile int length;
	
	
	final Thread thread;
	private final int queueSize;
	 
	
	
	private static final long lengthOffset;
	
	
	private static final sun.misc.Unsafe UNSAFE;
	
	   
	   static{
		   
		   try{
		   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
		   unsafeConstructor.setAccessible(true);
		   UNSAFE = unsafeConstructor.newInstance();
		   
		   lengthOffset = UNSAFE.objectFieldOffset
		            (LinkedList.class.getDeclaredField("length"));
		   }
		   catch(Exception e)
		   {
			   throw new RuntimeException(e);
		   }
		   
		   
	   }
	
	
	
	   private final boolean compareAndSet(int expect, int update) {
	        return UNSAFE.compareAndSwapInt(this, lengthOffset, expect, update);
	    }
   
	   
	   
	private void write()
	{
	
		
		for (;;) {
            int current = length;
            int next = current + 1;
            if (compareAndSet(current, next))
                return;
        }
		
	}
	
	
	public void read(int count)
	{
		for (;;) {
            int current = length;
            int next = current - count;
            if (compareAndSet(current, next))
                return;
        }
	}
	
	
	
	
	
	
	
	public LinkedList(EventBusImpl<T> eventBus,Thread thread,int queueSize) {
		super();
		this.eventBus = eventBus;
		this.thread = thread;
		this.queueSize = queueSize;
	}
	@Override
	public void accept(T event) {	
		if(length > queueSize)
		{
			 UNSAFE.park(false, 1);
		}
		acceptWithoutBackPressure(event);
	}



	@Override
	public void acceptWithoutBackPressure(T event) {
		write();
		
		LinkedListElement<T> linkedListElement= new LinkedListElement<T>(event);
		if( lastWritten == null )
		{
			lastWritten = linkedListElement;
			lastRead= new ListElementPointer<T>(lastWritten);
		}
		else
		{
			lastWritten.next = linkedListElement;
			lastWritten = lastWritten.next;
		}
	}
}
