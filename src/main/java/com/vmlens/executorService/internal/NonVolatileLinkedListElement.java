package com.vmlens.executorService.internal;

public class NonVolatileLinkedListElement<T> {

	final ProzessOneList<T>  prozessOneList;
	NonVolatileLinkedListElement<T> next;
	
	public NonVolatileLinkedListElement(ProzessOneList<T> prozessOneList) {
		super();
		this.prozessOneList = prozessOneList;
	}
	
	
	
}
