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
			
			int localReadCount = 0;
			
			// Das ist nur am start
			
			if(  ! list.lastRead.isRead )
			{
				eventSink.execute( list.lastRead.element.event  );
				
				list.lastRead.isRead = true;
				readCount++;
				localReadCount++;
			}
			
			LinkedListElement<T> current = list.lastRead.element;
			
			while(  current.next != null )
			{
				readCount++;
				localReadCount++;
				eventSink.execute( current.event  );
			
				current = current.next;
			
			}
			
			list.lastRead.element = current;
			
			list.read(localReadCount);
			
				
		}	
		
	}
	
	
    public void prozessWithoutReadCount(EventSink<T> eventSink) {
		if(list.lastRead  != null  )
		{
			int localReadCount = 0;
			
			// First element read			
			if(  ! list.lastRead.isRead )
			{
				eventSink.execute( list.lastRead.element.event  );
				localReadCount++;
				list.lastRead.isRead = true;
			}
			LinkedListElement<T> current = list.lastRead.element;
			while(  current.next != null )
			{
				eventSink.execute( current.event  );
				localReadCount++;
				current = current.next;
			}
			list.lastRead.element = current;
			
			
			list.read(localReadCount);
		}	
		
	}
	
	
	
	
}
