package com.vmlens.concurrent.hashmap;

public interface Factory<T,R> {
	
	
	R apply(T t);

}
