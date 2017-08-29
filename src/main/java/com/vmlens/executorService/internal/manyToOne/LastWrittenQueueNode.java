package com.vmlens.executorService.internal.manyToOne;

public class LastWrittenQueueNode<E>  {
	
	 QueueNode<E> last;
	 final BackPressureStrategy backPressure;

	public LastWrittenQueueNode(QueueNode<E> last,BackPressureStrategy backPressure) {
		super();
		this.last = last;
		this.backPressure = backPressure;
	}
	
	

}
