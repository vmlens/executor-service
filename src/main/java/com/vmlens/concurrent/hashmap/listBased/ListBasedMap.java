package com.vmlens.concurrent.hashmap.listBased;

import com.vmlens.concurrent.hashmap.Factory;
import com.vmlens.concurrent.hashmap.FactoryForPutIfAbsent;
import com.vmlens.concurrent.hashmap.KeyEqualAndHashCode;
import com.vmlens.concurrent.hashmap.ParentContainer;

public class ListBasedMap<K,V> extends ListEntry<K,V> {

	
	static final <K,V> Object find(K key,ListEntry<K,V>  start, KeyEqualAndHashCode<K>  keyEqualAndHashCode)
	{
		LinkedListElement<K,V>  current = start.next;
		ListEntry<K,V>  prevoius = start;
	
		while( current != null  )
		{
			Node<K,V> node = current.node;
			if( node != null &&  keyEqualAndHashCode.keyEquals(key,   node.key)  )
			{
				return node;
			}
			prevoius = current;
			current = current.next;
		}			
		return prevoius;
	}
	
	
	V append(LinkedListElement<K,V> newElement , ListEntry<K,V> last , KeyEqualAndHashCode<K>  keyEqualAndHashCode, ParentContainer parentContainer)
	{
		ListEntry<K,V> current = last;
		Node<K,V>  node = newElement.node;
		
		
		while(  ! current.setNextIfNull(newElement) )
		{
			Object  result =   find( node.key , current , keyEqualAndHashCode);
			
			if( result instanceof Node )
		       {
		    	return ((Node<K,V>)result).value;
		       }
			
			 current = (ListEntry<K,V>) result;
			
		}
		
		parentContainer.newSize( size() );
		
		return node.value;
		
		
	}
	
	
	
	
	
	 public  V  putIfAbsent(K key, V value, KeyEqualAndHashCode<K>  keyEqualAndHashCode)
	    {
	    	return computeIfAbsent(key, new FactoryForPutIfAbsent<K,V>(value) , keyEqualAndHashCode , new NoOpParentContainer());
	    }
	
	
	
	
	
	/**
	 * 
	 * prüfen lokale variablen erkannt?
	 * 
	 */
	
	
	
	
	
	public V computeIfAbsent(K key, Factory<? super K,? extends V> factory , KeyEqualAndHashCode<K>  keyEqualAndHashCode, ParentContainer parentContainer) {
		
	       Object result = find(key, this,keyEqualAndHashCode);
	       
	       if( result instanceof Node )
	       {
	    	return ((Node<K,V>)result).value;
	       }
	       
	       ListEntry<K,V> current = (ListEntry<K,V>) result;
	       LinkedListElement<K,V> newElement = new  LinkedListElement<K,V> ( key, factory.apply(key) );
	       
		return append( newElement , current , keyEqualAndHashCode,parentContainer);
		
	}
	
	public boolean remove(K key, KeyEqualAndHashCode<K>  keyEqualAndHashCode)
	{
		
		LinkedListElement<K,V>  current = next;
		while( current != null  )
		{
		
			Node<K,V> node = current.node;
			if( node != null &&  keyEqualAndHashCode.keyEquals(key,   node.key) )
			{
				current.node = null;
				return true;
			}
			current = current.next;
		}
		
		return false;
	}
	
	public int size()
	{
		int size = 0;
		
		LinkedListElement<K,V>  current = next;
		while( current != null  )
		{
			if(  current.node != null )
			{
				size++;
			}
			current = current.next;
		}
		
		return size;
		
	}
	
}
