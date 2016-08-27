package com.vmlens.executorService.internal.manyToOne;

public class SingleReaderPointer<E> {
	
	
	QueueNode<E>  value;
    boolean valueConsumed = false;
	
    public SingleReaderPointer(QueueNode<E> value) {
		super();
		this.value = value;
	}
    
    
    
}
