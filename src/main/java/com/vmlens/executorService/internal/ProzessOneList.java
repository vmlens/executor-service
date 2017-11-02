package com.vmlens.executorService.internal;

import com.vmlens.executorService.EventSink;

public class ProzessOneList<T> {

	

    final LinkedList<T> list;
    int readCount;
	
	
	public ProzessOneList(LinkedList<T> list) {
		super();
		this.list = list;
	   
	
	}

	public void prozessWithReadCount(EventSink<T> eventSink) {
		
		if(list.lastRead  != null  )
		{
			
			// Das ist nur am start
			
			if(  ! list.lastRead.isRead )
			{
				eventSink.execute( list.lastRead.element.event  );
				
				list.lastRead.isRead = true;
				readCount++;
			}
			
			LinkedListElement<T> current = list.lastRead.element;
			
			while(  current.next != null )
			{
				readCount++;
				eventSink.execute( current.event  );
			
				current = current.next;
			
			}
			
			list.lastRead.element = current;
				
		}	
		
	}
	
	
    public void prozessWithoutReadCount(EventSink<T> eventSink) {
		if(list.lastRead  != null  )
		{
			// First element read			
			if(  ! list.lastRead.isRead )
			{
				eventSink.execute( list.lastRead.element.event  );
				list.lastRead.isRead = true;
			}
			LinkedListElement<T> current = list.lastRead.element;
			while(  current.next != null )
			{
				eventSink.execute( current.event  );
				current = current.next;
			}
			list.lastRead.element = current;	
		}	
		
	}
	
	
	
	
}
