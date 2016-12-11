package com.vmlens.executorService.internal.service;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.internal.manyToOne.QueueManyWriters;

public class ExecutorServiceImpl extends AbstractExecutorService {

	
	
	private final QueueManyWriters<Runnable> queueManyWriters;
	private final StopService stopService;
	
	
	
	
	public ExecutorServiceImpl(QueueManyWriters<Runnable> queueManyWriters, StopService stopService) {
		super();
		this.queueManyWriters = queueManyWriters;
		this.stopService = stopService;
	}

	public void execute(Runnable command) {
		
		queueManyWriters.accept(command);
	}

	public void shutdown() {
		stopService.stop = true;
		
	}

	public List<Runnable> shutdownNow() {
		
		stopService.stop = true;
		
		return Collections.<Runnable>emptyList();
	}

	public boolean isShutdown() {
	
		return stopService.stop;
	}

	public boolean isTerminated() {
		return stopService.terminated;
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		
		long waitTill =  System.nanoTime() + unit.toNanos(timeout);
		
		while( ! stopService.terminated )
		{
			synchronized(stopService.terminationSignal)
			{
				unit.timedWait(stopService.terminationSignal, timeout);
			}
			
			
			if( waitTill <  System.nanoTime()  )
			{
				return false;
			}
			
			
		}
		
		return stopService.terminated;
	}



}
