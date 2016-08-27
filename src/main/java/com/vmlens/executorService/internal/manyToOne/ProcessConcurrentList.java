package com.vmlens.executorService.internal.manyToOne;

import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.internal.ListNode;

public class ProcessConcurrentList<T> {

	
	private ListNode<SingleReaderPointer<T>> firstCopiedListNode;
	private ListNode<SingleReaderPointer<T>> lastCopiedListNode;
	private final ConcurrentLinkedList<T> externalList;
	private ConcurrentListNode<T> lastReadExternalNode;
	
	
	
	
	
	
	public ProcessConcurrentList(ConcurrentLinkedList<T> externalList) {
		super();
		this.externalList = externalList;
	}






	public void foreach(Consumer<ListNode<SingleReaderPointer<T>>> consumer )
	{
		
		
		
		if( lastReadExternalNode == null )
		{
			if( externalList.first == null )
			{
				return;
			}
			
			lastReadExternalNode = externalList.first;
			
			lastCopiedListNode = lastReadExternalNode.createListNodeAndClearElement();
			firstCopiedListNode = lastCopiedListNode;
			
		}
		
		
		ConcurrentListNode current = lastReadExternalNode.next;
		
		while( current != null )
		{
			lastCopiedListNode.next = current.createListNodeAndClearElement();
			lastCopiedListNode  = lastCopiedListNode.next;
			
			lastReadExternalNode = current;
			current = lastReadExternalNode.next;
			
			
		}
		
		ListNode currentListNode = firstCopiedListNode;
		
		while( currentListNode != null )
		{
			consumer.accept(currentListNode);
			
			currentListNode = currentListNode.next;
		}
		
			
		
	}
	
	
}
