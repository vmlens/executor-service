package com.vmlens.concurrent.hashmap;

public class FactoryForPutIfAbsent<T,R> implements  Factory<T,R>  {

	private final R value;
	
	
	
	
	
	public FactoryForPutIfAbsent(R value) {
		super();
		this.value = value;
	}





	@Override
	public R apply(T t) {
		return value;
	}

}
