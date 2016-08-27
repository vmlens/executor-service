package com.vmlens.executorService.internal.manyToOne;

public class LastWrittenQueueNode<E>  {
	
	 QueueNode<E> last;

	public LastWrittenQueueNode(QueueNode<E> last) {
		super();
		this.last = last;
	}
	
	

}
