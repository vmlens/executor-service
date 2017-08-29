package com.vmlens.executorService.internal.service;

import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.manyToOne.QueueReader;;


public class TaskExecutor<E> implements Runnable  {

	
	private final QueueReader<E> queueReader;
	private final EventSink<E> eventSink;
	private final EventBusImpl<E> eventBus;

	
	
	
	
	public TaskExecutor(QueueReader<E> queueReader, EventSink<E> eventSink,EventBusImpl<E> eventBus) {
		super();
		this.queueReader = queueReader;
		this.eventSink = eventSink;
		this.eventBus = eventBus;
	}




	
	public void run() {
			queueReader.processTillStopped(eventSink);
			eventBus.decrementReaderCount();
	
	}


}
