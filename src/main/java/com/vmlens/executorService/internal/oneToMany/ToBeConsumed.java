package com.vmlens.executorService.internal.oneToMany;

public class ToBeConsumed<T> {
	
	volatile T toBeConsumed;
	boolean stopMessageSended = false;

}
