package com.vmlens.executorService.internal.service;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.oneToMany.QueueManyReaders;
import com.vmlens.executorService.internal.oneToMany.ToBeConsumed;

public abstract class WorkerThread<E> extends Thread implements Consumer<LinkedNode<E>> {

	
	private final QueueManyReaders<LinkedNode<LinkedNode<E>>> queueManyReaders
	 = new QueueManyReaders<LinkedNode<LinkedNode<E>>>(new ToBeConsumed<LinkedNode<E>>());
	
	private boolean stopped = false;
	



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
	   }
	   else
	   {
		   processList(current);
	   }
		
		
		
//		while( current != null )
//		{
//			if( current.element instanceof PoisenedMessage )
//			{
//				stopped = true;
//				
//				
//			}
//			else
//			{	
//			
//				current.element.run();
//			
//			}
//
//			
//			current = current.next;
//			
//			
//			
//		}
		
		
	}
	
	
	abstract void processList(LinkedNode<E> current);
	
	
	
	
}
