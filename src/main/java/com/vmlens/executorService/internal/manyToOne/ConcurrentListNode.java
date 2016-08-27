package com.vmlens.executorService.internal.manyToOne;

import com.vmlens.executorService.internal.ListNode;

public class ConcurrentListNode<T> {
	
	 private  QueueNode<T> element;
	 private final long threadId;
	 public volatile ConcurrentListNode<T> next;
	
	
	
	
	
	public ConcurrentListNode(QueueNode<T> element, long threadId) {
		super();
		this.element = element;
		this.threadId = threadId;
	}





	public ListNode<SingleReaderPointer<T>> createListNodeAndClearElement()
	{
		ListNode<SingleReaderPointer<T>> listNode =  new ListNode<SingleReaderPointer<T>>(new SingleReaderPointer<T>((QueueNode<T>) element),threadId);
		element = null;
		return listNode;
	}

	
	
	

}
