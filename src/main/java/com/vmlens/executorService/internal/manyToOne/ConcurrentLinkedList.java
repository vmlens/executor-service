package com.vmlens.executorService.internal.manyToOne;

public class ConcurrentLinkedList<T> {
	
	
	 public volatile ConcurrentListNode<T> first = null;
	 private volatile ConcurrentListNode<T> last = null;
	
	public synchronized void append(QueueNode<T> in,BackPressureStrategy backPressure)
	{
		if(  last == null )
		{
			first = new ConcurrentListNode<T>(in,backPressure);
			last = first;
		}
		else
		{
			last.next =  new ConcurrentListNode<T>(in,backPressure);
			last = last.next;
		}
	}
	
	

}
