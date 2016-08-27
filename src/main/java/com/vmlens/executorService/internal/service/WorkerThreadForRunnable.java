package com.vmlens.executorService.internal.service;


import com.vmlens.executorService.internal.manyToOne.LinkedNode;


public class WorkerThreadForRunnable extends WorkerThread<Runnable> {

	@Override
	void processList(LinkedNode<Runnable> obj) {
		
          LinkedNode<Runnable> current = obj;
		
	
		
		while( current != null )
		{
			
				current.element.run();
			current = current.next;
			
			
			
		}
		
		
		
	}

	
	
	
	
}
