package com.vmlens.executorService;

import java.util.concurrent.TimeUnit;

public interface EventBus<T> extends Consumer<T> , AutoCloseable {
	
	
	boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

}
