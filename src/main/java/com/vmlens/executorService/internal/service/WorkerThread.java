package com.vmlens.executorService.internal.service;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.oneToMany.QueueManyReaders;
import com.vmlens.executorService.internal.oneToMany.ToBeConsumed;

public abstract class WorkerThread<E> extends Thread implements Consumer<LinkedNode<E>> {

	
	private final QueueManyReaders<LinkedNode<LinkedNode<E>>> queueManyReaders
	 = new QueueManyReaders<LinkedNode<LinkedNode<E>>>(new ToBeConsumed<LinkedNode<E>>());
	
	private boolean stopped = false;
	
	







	public WorkerThread() {
		super();
		// TODO Auto-generated constructor stub
	}






	public WorkerThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}






	public WorkerThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}






	public ToBeConsumed<LinkedNode<E>> getToBeConsumed() {
		return queueManyReaders.toBeConsumed;
	}






	@Override
	public void run() {
		
		while( ! stopped )
		{
			queueManyReaders.proccessElement(this);
		}
		
		
		
	}



	


	public void accept(LinkedNode<E> obj) {
		
		LinkedNode<E> current = obj;
		
	
	   if(  current instanceof PoisenedMessage  )
	   {
		   stopped = true;
		   onStop();
	   }
	   else
	   {
		   processList(current);
	   }
		
		
		

		
		
	}
	
	
	abstract void processList(LinkedNode<E> current);
    protected void onStop()
    {
    	
    }
	
	
	
}
