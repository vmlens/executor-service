package com.vmlens.concurrent.hashmap.listBased;



public class Node<K,V> {
	
	final K key;
	final V value;
	
	
	
	public Node(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}



	@Override
	public String toString() {
		return "Node [key=" + key + ", value=" + value + "]";
	}
	
	
	
	

}
