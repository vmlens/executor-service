package com.vmlens.executorService.internal.manyToOne;

import com.vmlens.executorService.internal.ListNode;

public class ConcurrentListNode<T> {
	
	 private  QueueNode<T> element;
	 private final BackPressureStrategy backPressure;
	 public volatile ConcurrentListNode<T> next;
	
	
	
	
	
	public ConcurrentListNode(QueueNode<T> element, BackPressureStrategy backPressure) {
		super();
		this.element = element;
		this.backPressure = backPressure;
	}





	public ListNode<SingleReaderPointer<T>> createListNodeAndClearElement()
	{
		ListNode<SingleReaderPointer<T>> listNode =  new ListNode<SingleReaderPointer<T>>(new SingleReaderPointer<T>((QueueNode<T>) element),backPressure);
		element = null;
		return listNode;
	}

	
	
	

}
