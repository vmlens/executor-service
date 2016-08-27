package com.vmlens.executorService.internal;



public class ListNode<T> {
	
	public ListNode<T> next;
	public T element;
	public final long threadId;
	
	public ListNode(T element, long threadId) {
		super();
		this.element = element;
		this.threadId = threadId;
	} 
	
	
	
	
	

}
