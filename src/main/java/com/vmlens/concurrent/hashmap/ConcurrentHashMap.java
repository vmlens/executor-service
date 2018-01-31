package com.vmlens.concurrent.hashmap;

import java.lang.reflect.Constructor;

import com.vmlens.concurrent.hashmap.listBased.ListBasedMap;



public  class ConcurrentHashMap<K,V> implements ParentContainer {

	
    /**
     * The largest possible table capacity.  This value must be
     * exactly 1<<30 to stay within Java array allocation and indexing
     * bounds for power of two table sizes, and is further required
     * because the top two bits of 32bit hash fields are used for
     * control purposes.
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The default initial table capacity.  Must be a power of 2
     * (i.e., at least 1) and at most MAXIMUM_CAPACITY.
     */
    private static final int DEFAULT_CAPACITY = 16;
	

    /**
     * The array of bins. Lazily initialized upon first insertion.
     * Size is always a power of two. Accessed directly by iterators.
     */
    private transient volatile ListBasedMap<K,V>[] table;

    /**
     * The next table to use; non-null only while resizing.
     */
    private transient volatile ListBasedMap<K,V>[] nextTable;

	
	 /**
     * Returns a power of two table size for the given desired capacity.
     * See Hackers Delight, sec 3.2
     */
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    
    
    private static final int ABASE;
    private static final int ASHIFT;
    
    private static final sun.misc.Unsafe U;
	
	   
	   static{
		   
		   try{
		   Constructor<sun.misc.Unsafe> unsafeConstructor = sun.misc.Unsafe.class.getDeclaredConstructor();
		   unsafeConstructor.setAccessible(true);
		   U = unsafeConstructor.newInstance();
		   
		   		   
		   ABASE = U.arrayBaseOffset(ListBasedMap[].class);
           int scale = U.arrayIndexScale(ListBasedMap[].class);
           if ((scale & (scale - 1)) != 0)
               throw new Error("array index scale not a power of two");
           ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
		   }
		   catch(Exception e)
		   {
			   throw new RuntimeException(e);
		   }
		   
		   
	   }
    
    
    
    
    
    
    
   
    private static final <K,V> int indexFor(ListBasedMap<K,V>[] array , int hashCode ) 
    {
    	int n = array.length;
    	return (n - 1) & hashCode;
    }
    
    
    private static final <K,V> ListBasedMap<K,V> tabAt(ListBasedMap<K,V>[] array, int i) {
        return (ListBasedMap<K,V>)U.getObjectVolatile(array, ((long) i  << ASHIFT) + ABASE);
    }
    
    
 
    
    static final <K,V> ListBasedMap<K,V> getOrCreate(ListBasedMap<K,V>[] tab, int i) {
    	
    	    ListBasedMap<K,V>  x = new ListBasedMap<K,V>();
    	    
    	
           if( !  U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, null, x) )
           {
        	   tabAt( tab , i );
           }
           
           return x;
    }
    
    
    private final KeyEqualAndHashCode<K> keyEqualAndHashCode;
    private final int resizeThreshold;
    
    
    public ConcurrentHashMap()
    {
    	table = new ListBasedMap[DEFAULT_CAPACITY];
    	keyEqualAndHashCode = new ObjectHashCodeAndEquals<K>();
    	resizeThreshold = 10;
    	
    }
	
    
    public  V  putIfAbsent(K key, V value)
    {
    	return computeIfAbsent(key, new FactoryForPutIfAbsent<K,V>(value) );
    }
  
    
    /**
     * 
     * immer nur am ende hinzufügen
     * löschen: wenn next vorhanden kann das element gelöscht werden
     * 
     * 

     */
    
    
    public V computeIfAbsent(K key,Factory<? super K,? extends V> factory )
    {
    	/*
    	 * first check in table than nextTable
    	 * if not there create with cas
    	 * 
    	 * 
    	 * 
    	 */
    	ListBasedMap<K,V>[] localTable = table;
    	int hashCode = keyEqualAndHashCode.keyHashCode(key);
    	int index = indexFor(localTable , hashCode );
    	
    	ListBasedMap<K,V> listBasedMap = tabAt(localTable , index );
    	
    	if(  listBasedMap != null )
    	{
    		return listBasedMap.computeIfAbsent(key, factory , keyEqualAndHashCode,this);
    	}
    	
    	 listBasedMap = getOrCreate( localTable , index );
    	
    	 return listBasedMap.computeIfAbsent(key, factory , keyEqualAndHashCode,this);
    	
    	
    	
    }


	@Override
	public void newSize(int size) {
		if( size >  resizeThreshold  )
		{
			
		}
		
	}
    
    
  
    
	
}
