package com.vmlens.executorService.internal.service;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import com.vmlens.executorService.EventBus;

public class EventBus2ExecutorServiceBridge extends AbstractExecutorService {

	
	
	private final EventBusImpl<Runnable> eventBus;
	

	

	public EventBus2ExecutorServiceBridge(EventBusImpl<Runnable> eventBus) {
		super();
		this.eventBus = eventBus;
	}

	public void execute(Runnable command) {
		
		eventBus.execute(command);
	}

	public void shutdown() {
	
		eventBus.shutdown();
		
	}

	public List<Runnable> shutdownNow() {
		
		eventBus.shutdown();
		
		return Collections.<Runnable>emptyList();
	}

	public boolean isShutdown() {
	
		return eventBus.isShutdown();
	}

	public boolean isTerminated() {
		return eventBus.isTerminated();
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		
		return eventBus.awaitTermination(timeout, unit);
	}



}
