package com.vmlens.executorService.internal.service;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;

public class ExecutorServiceImpl extends AbstractExecutorService {

	
	
	private final QueueManyWriters<Runnable> queueManyWriters;
	private final DispatcherThread dispatcherThread;
	
	
	
	
	public ExecutorServiceImpl(QueueManyWriters<Runnable> queueManyWriters, DispatcherThread dispatcherThread) {
		super();
		this.queueManyWriters = queueManyWriters;
		this.dispatcherThread = dispatcherThread;
	}

	public void execute(Runnable command) {
		
		queueManyWriters.accept(command);
	}

	public void shutdown() {
		dispatcherThread.stop = true;
		
	}

	public List<Runnable> shutdownNow() {
		
		dispatcherThread.stop = true;
		
		return Collections.<Runnable>emptyList();
	}

	public boolean isShutdown() {
	
		return dispatcherThread.stop;
	}

	public boolean isTerminated() {
		return dispatcherThread.terminated;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		
		long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		while( ! dispatcherThread.terminated )
		{
			synchronized(dispatcherThread.terminationSignal)
			{
				unit.timedWait(dispatcherThread.terminationSignal, timeout);
			}
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return false;
			}
			
			
		}
		
		return dispatcherThread.terminated;
	}



}
