package com.vmlens.executorService.internal.service;

import java.util.concurrent.RejectedExecutionException;

public class StopService {
	
	
	public volatile boolean  stop = false;
	

	
	
	public void onStop()
	{
		throw new RejectedExecutionException();
	}

}
