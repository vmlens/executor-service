package com.vmlens.executorService.internal.service;

import com.vmlens.executorService.EventSink;

public class ExecutorServiceEventSink implements EventSink<Runnable> {

	@Override
	public void execute(Runnable obj) {
		
		obj.run();
		
	}

	@Override
	public void close() {
		
		
	}
	

	@Override
	public void onWait() {
		
		
	}

}
