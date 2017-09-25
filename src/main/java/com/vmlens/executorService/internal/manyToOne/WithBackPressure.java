package com.vmlens.executorService.internal.manyToOne;

import java.lang.reflect.Constructor;

public class WithBackPressure implements BackPressureStrategy {

	
	private int written = 1;
	private volatile int  read;
	
	
	private static final int MAX_INT = Integer.MAX_VALUE - 100;
	
	
	private  final int MAX_DIFF;
	
	
   private static final sun.misc.Unsafe UNSAFE;
	
   
   static{
	   
	   try{
	   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
	   unsafeConstructor.setAccessible(true);
	   UNSAFE = unsafeConstructor.newInstance();
	   }
	   catch(Exception e)
	   {
		   throw new RuntimeException(e);
	   }
	   
	   
   }
   
   
   
	
	public WithBackPressure(int mAX_DIFF) {
		super();
		MAX_DIFF = mAX_DIFF;
	}


	public void writeOne()
	{
		written++;
		
		int diff = written - read;
		
		
		int waitCount = 0;
		
		while(  diff > MAX_DIFF )
		{
			
			
			waitCount++;
			
			 if( waitCount < 1000)
			   {
				   UNSAFE.park(false, 1);
				   waitCount++;
			   }
			   else 
			   {
				   UNSAFE.park(false, 10);
				   //waitCount++;
			   }
			
			
			
//			if( waitCount > 100)
//			{
//				try{
//				Thread.sleep(1);
//				}
//				catch(Exception e)
//				{
//					
//				}
//			}
//			else
//			{
//				Thread.yield();
//			}
			
			diff = written - read;
		}
		
	}
	
	
	public void read(int amount)
	{
		read += amount;
	}
	
	
	
}
