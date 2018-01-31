package com.vmlens.concurrent.hashmap;

public interface KeyEqualAndHashCode<K> {

	
	int keyHashCode(K key);
	boolean keyEquals(K first,K second);
	
}
