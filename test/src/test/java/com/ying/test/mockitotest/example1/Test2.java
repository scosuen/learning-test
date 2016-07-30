package com.ying.test.mockitotest.example1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mockito.Matchers;
import org.mockito.Mockito;

public class Test2 {

	private LinkedList<String> mockList = mock(LinkedList.class);

	public static void main(String[] args) {
		Test2 t = new Test2();
		t.test4();
	}

	public void test1() {
		List mockList = mock(List.class);

		mockList.add("one");
//		mockList.clear();

		verify(mockList).add("one");
		verify(mockList).clear();
	}

	public void test2() {

		when(mockList.get(0)).thenReturn("first");
		when(mockList.get(1)).thenThrow(new RuntimeException());

		System.out.println(mockList.get(0));

		System.out.println(mockList.get(999));

		System.out.println(mockList.get(1));

		verify(mockList).get(0);
	}

	public void test3() {
		List<String> mockList = mock(ArrayList.class);
		
		when(mockList.get(0)).thenReturn("0").thenReturn("1");
		
		System.out.println(mockList.get(0));
		System.out.println(mockList.get(0));
	}

	public void test4 () {
		List<String> list = new ArrayList<String>();

		List<String> spy = Mockito.spy(list);
		
		when(spy.size()).thenReturn(100);
		
		spy.add("one");
		spy.add("two");
		
		System.out.println(spy.get(0));
		System.out.println(spy.size());
		
		verify(spy).add("one");
		verify(spy).add("two");
	}
	
	public void test5 () {
	}

	public void city() {
		Map mapMock = Mockito.mock(Map.class);
		Mockito.when(mapMock.get("city")).thenReturn("Shenzhen");

		assertEquals("city test", "Shenzhen", mapMock.get("city"));

		Mockito.verify(mapMock).get(Matchers.eq("city"));
		Mockito.verify(mapMock, Mockito.times(2));
	}
}
