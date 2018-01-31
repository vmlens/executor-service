package com.vmlens.concurrent.hashmap.listBased;

public class LinkedListElement<K,V> extends ListEntry<K,V> {

	volatile Node<K,V> node;
	
	
	public LinkedListElement(K key, V value) {
		this( new Node<K,V>(key,value));
		
	}
	
	
	public LinkedListElement(Node<K, V> element) {
		super();
		this.node = element;
	}
	
	
	
	
}
