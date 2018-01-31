package com.vmlens.concurrent.hashmap;

public class ObjectHashCodeAndEquals<K> implements KeyEqualAndHashCode<K>  {

	@Override
	public final int keyHashCode(K key) {
		return key.hashCode();
	}

	@Override
	public final boolean keyEquals(K first, K second) {
		return first.equals(second);
	}

}
