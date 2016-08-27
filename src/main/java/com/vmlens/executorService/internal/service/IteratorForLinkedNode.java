package com.vmlens.executorService.internal.service;

import java.util.Iterator;

import com.vmlens.executorService.internal.manyToOne.LinkedNode;

public class IteratorForLinkedNode<E> implements Iterator<E> {
	
	
	private LinkedNode<E> current;
	
	
	

	public IteratorForLinkedNode(LinkedNode<E> current) {
		super();
		this.current = current;
	}

	@Override
	public boolean hasNext() {
		
		return current != null;
	}

	@Override
	public E next() {
	
		E  element =  current.element;

		current = current.next;
		
		return element;
	
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
