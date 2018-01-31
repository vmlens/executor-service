package com.vmlens.executorService.internal;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;

public class ThreadLocalConsumer<T> implements Consumer<T> {
	private final EventBus<T> theBus;
    private static final ThreadLocal<Consumer> threadLocal = new 	ThreadLocal<Consumer>();
	
    public ThreadLocalConsumer(EventBus<T> theBus) {
		super();
		this.theBus = theBus;
	}
	public void accept(T event) {	
		Consumer<T>  consumer = threadLocal.get();
		if( consumer == null )
		{
			consumer = theBus.newConsumerForThreadLocalStorage(Thread.currentThread());
			threadLocal.set( consumer );
		}
		consumer.accept(event);
	}
	@Override
	public void acceptWithoutBackPressure(T event) {
		Consumer<T>  consumer = threadLocal.get();
		if( consumer == null )
		{
			consumer = theBus.newConsumerForThreadLocalStorage(Thread.currentThread());
			threadLocal.set( consumer );
		}
		consumer.acceptWithoutBackPressure(event);
		
	}
}
