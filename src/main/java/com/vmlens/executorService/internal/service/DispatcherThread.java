package com.vmlens.executorService.internal.service;

import java.util.concurrent.locks.LockSupport;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.manyToOne.LinkedNode;
import com.vmlens.executorService.internal.manyToOne.QueueSingleReader;
import com.vmlens.executorService.internal.oneToMany.QueueSingleWriter;

public class DispatcherThread<T> extends Thread  {

	private final QueueSingleWriter<LinkedNode<T>> queueSingleWriter;
	private final QueueSingleReader<T> queueSingleReader;
	
	public volatile boolean  stop = false;
	public volatile boolean terminated = false;
	
	public final Object terminationSignal = new Object(); 

	
	
	
	public DispatcherThread(QueueSingleWriter<LinkedNode<T>> queueSingleWriter, QueueSingleReader<T> queueSingleReader) {
		super();
		this.queueSingleWriter = queueSingleWriter;
		this.queueSingleReader = queueSingleReader;
	}


	private LinkedNode<T> start = null;
	private LinkedNode<T> current = null;
	


	@Override
	public void run() {
		
		int waitIterations = 0;
		
		while( true )
		{
			
			start = null;
			current = null;
			
			queueSingleReader.consumeQueueElements(
						
						new Consumer<T>()
						{
							public void accept(T in)
							{
								
							  if( start == null  )
							  {
								  start = new LinkedNode<T>(in);
								  current = start;
							  }
							  else
							  {
								  current.next = new  LinkedNode<T>(in);
								  current =  current.next;
								  
							  }
								
								
								
							
								
								
							}	
								
						}
						);
			
			
		 if( start != null )
		 {
			 queueSingleWriter.push(start);
		 }
		 else
		 {
			 
			 LockSupport.parkNanos(100);
			 
			 if( stop )
			 {
				 if( waitIterations > 3 )
				 {
					 break;
				 }
				 else
				 {
					 waitIterations++;
				 }
				
			 }
			 
			 
			 
		 }
		
			
			
		}
		
	
		
		queueSingleWriter.sendStopMessageToAllAndWait(new   PoisenedMessage());
		
		
		
		
		
		terminated = true;
		
		synchronized(terminationSignal)
		{
			
			terminationSignal.notifyAll();
			
			
		}
		
	}

	
	
	
	
	
}
