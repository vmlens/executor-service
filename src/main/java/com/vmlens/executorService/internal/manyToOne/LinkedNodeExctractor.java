package com.vmlens.executorService.internal.manyToOne;

import com.vmlens.executorService.Consumer;

public class LinkedNodeExctractor<E> implements Consumer<E>   {

	private LinkedNode<E> current;
	private LinkedNode<E> start;
	
	
	@Override
	public void accept(E obj) {
		
		if( current == null )
		{
			start= new LinkedNode<E>(obj);
			current = start; 
			
		}
		else
		{
			current.next = new LinkedNode<E>(obj);
			current = current.next;
		}
		
		
		
	}

	
	
	public LinkedNode<E> getAndCreateNew()
	{
	    if(  current == null  )
	    {
	    	return null;
	    }
	    else
	    {
	    	LinkedNode<E> temp =  start;
	    	
	    	start   = null;
	    	current = null;
	    	
	    	return temp;
	    	
	    	
	    }
		
		
		
	}
	
	
	
}
