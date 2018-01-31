package com.vmlens.concurrent.hashmap;

import static org.junit.Assert.*;

import org.junit.Test;
import org.apache.commons.lang3.mutable.MutableInt;

public class TestConfigObjectHashCodeAndEquals {

	private static final Factory<String, String> firstValue = new Factory<String, String>() {

		public String apply(String t) {
			return "firstValue";
		}

	};

	private static final Factory<String, String> secondValue = new Factory<String, String>() {

		public String apply(String t) {
			return "firstValue";
		}

	};


	@Test
	public void testComputeIfAbsent() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

		map.computeIfAbsent("one", firstValue);
		String result = map.computeIfAbsent("one", secondValue);

		assertEquals("firstValue", result);

	}

	@Test
	public void testComputeIfAbsentDegenerateHashKey() {
		ConcurrentHashMap<DegenerateHashKey, MutableInt> map = new ConcurrentHashMap<DegenerateHashKey, MutableInt>();

		for (int i = 0; i < 10; i++) {
			MutableInt mutable = map.putIfAbsent(new DegenerateHashKey(2, i), new MutableInt());
			mutable.add(1);
		}

		for (int i = 0; i < 10; i++) {
			MutableInt mutable = map.putIfAbsent(new DegenerateHashKey(2, i), new MutableInt());
			mutable.add(1);
		}

		int sum = 0;

		for (int i = 0; i < 10; i++) {
			MutableInt mutable = map.putIfAbsent(new DegenerateHashKey(2, i), new MutableInt());
			sum += mutable.getValue();
		}

		assertEquals(20, sum);

	}

}
