package com.vmlens.concurrent.hashmap;

public class DegenerateHashKey {
	
	private int hashCode;
    private int value;

    
    
    
	

	public DegenerateHashKey(int hashCode, int value) {
		super();
		this.hashCode = hashCode;
		this.value = value;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DegenerateHashKey other = (DegenerateHashKey) obj;
		if (value != other.value)
			return false;
		return true;
	}
    
    
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	
	
	
	
	
	
}
