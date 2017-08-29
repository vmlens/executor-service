package com.vmlens.executorService.internal.service;

import java.util.concurrent.ThreadFactory;

public class DefaultWorkerThreadFactory implements ThreadFactory {

	public Thread newThread(Runnable taskExecutor)
	{
		return new Thread(taskExecutor);
	}
	
	
}
