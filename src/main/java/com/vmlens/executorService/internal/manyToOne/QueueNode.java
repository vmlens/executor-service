package com.vmlens.executorService.internal.manyToOne;

public class QueueNode<E> {
	
	
	 final E element;
     volatile QueueNode<E> next;
	
	public QueueNode(E element) {
		super();
		this.element = element;
	}
	
	

}
