package com.vmlens.executorService.internal;

import com.vmlens.executorService.internal.manyToOne.BackPressureStrategy;

public class ListNode<T> {
	
	public ListNode<T> next;
	public T element;
	public final BackPressureStrategy backPressure;
	
	public ListNode(T element, BackPressureStrategy backPressure) {
		super();
		this.element = element;
		this.backPressure = backPressure;
	} 
	
	
	
	
	

}
