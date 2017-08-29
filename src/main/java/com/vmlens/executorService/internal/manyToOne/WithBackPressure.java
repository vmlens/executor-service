package com.vmlens.executorService.internal.manyToOne;

import java.util.concurrent.locks.LockSupport;

public class WithBackPressure implements BackPressureStrategy {

	
	private int written = 1;
	private volatile int  read;
	
	
	private static final int MAX_INT = Integer.MAX_VALUE - 100;
	
	
	private  final int MAX_DIFF;
	
	
	
	
	
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
			
			if( waitCount > 100)
			{
				try{
				Thread.sleep(1);
				}
				catch(Exception e)
				{
					
				}
			}
			else
			{
				Thread.yield();
			}
			
			diff = written - read;
		}
		
	}
	
	
	public void read(int amount)
	{
		read += amount;
	}
	
	
	
}
