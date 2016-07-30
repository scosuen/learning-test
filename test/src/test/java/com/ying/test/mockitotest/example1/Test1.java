package com.ying.test.mockitotest.example1;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.NoInteractionsWanted;

/**
 * http://blog.csdn.net/sdyy321/article/details/38757135/#
 * 
 * @author Ying
 *
 */
public class Test1 {

	/*
	 * 验证行为
	 */
	@Test
	public void test1() {
		List mockList = mock(List.class);

		mockList.add(1);
		mockList.clear();

		verify(mockList).add(1);
		verify(mockList).clear();
	}

	/*
	 * 模拟我们所期望的结果
	 */
	@Test
	public void test2() {
		Iterator<String> iterator = mock(Iterator.class);

		when(iterator.next()).thenReturn("str 1").thenReturn("str 2");

		assertEquals("str 1str 2str 2str 2", iterator.next() + iterator.next() + iterator.next() + iterator.next());

	}

	@Test(expected = IOException.class)
	public void test3() throws IOException {
		OutputStream outputStream = mock(OutputStream.class);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

		doThrow(new IOException()).when(outputStream).close();

		outputStreamWriter.close();
	}

	/*
	 * 参数匹配
	 */
	@Test
	public void test4() {
		Comparable comparable = mock(Comparable.class);

		when(comparable.compareTo("Test")).thenReturn(1);
		when(comparable.compareTo("Omg")).thenReturn(2);

		assertEquals(1, comparable.compareTo("Test"));
		assertEquals(2, comparable.compareTo("Omg"));
	}

	/*
	 * 匹配任意参数
	 */
	@Test
	public void test5() {
		List list = mock(List.class);

		when(list.get(anyInt())).thenReturn(1);
		when(list.contains(argThat(new ArgumentMatcher() {
			@Override
			public boolean matches(Object argument) {
				return (Integer) argument == 1 || (Integer) argument == 2;
			}
		}))).thenReturn(true);

		assertEquals(1, list.get(2136));
		assertEquals(1, list.get(45685));

		assertTrue(list.contains(1));
		assertTrue(!list.contains(3));
	}

	@Test
	public void test6() {
		Comparator comparator = mock(Comparator.class);

		comparator.compare("1", "2");

		// 如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
		verify(comparator).compare(anyString(), eq("2"));
		verify(comparator).compare(eq("1"), eq("2"));
		// 下面的为无效的参数匹配使用
		// verify(comparator).compare(anyString(),"hello");
	}

	/*
	 * 验证确切的调用次数
	 */
	@Test
	public void test7() {

		List list = mock(List.class);

		list.add(1);
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(3);
		list.add(3);
		list.add(3);

		// 验证是否被调用一次，等效于下面的times(1)
		verify(list).add(1);
		verify(list, times(1)).add(1);

		// 验证是否被调用2次
		verify(list, times(2)).add(2);

		// 验证是否被调用3次
		verify(list, times(4)).add(3);

		// 验证是否从未被调用过
		verify(list, never()).add(4);

		// 验证至少调用一次
		verify(list, atLeastOnce()).add(3);

		// 验证至少调用2次
		verify(list, atLeast(2)).add(2);

		// 验证至多调用3次
		verify(list, atMost(4)).add(3);
	}

	/*
	 * 模拟方法体抛出异常
	 */
	@Test(expected = RuntimeException.class)
	public void test8() {
		List list = mock(List.class);

		doThrow(new RuntimeException()).when(list).add(10);

		list.add(10);
	}

	/*
	 * 验证执行顺序
	 */
	@Test
	public void test9() {

		List list1 = mock(List.class);
		List list2 = mock(List.class);

		list1.add(1);
		list2.add(1);
		list1.add(2);
		list2.add(2);

		// 将需要排序的mock对象放入InOrder
		InOrder inOrder = inOrder(list1, list2);

		// 下面的代码不能颠倒顺序，验证执行顺序
		inOrder.verify(list1).add(1);
		inOrder.verify(list2).add(1);
		inOrder.verify(list1).add(2);
		inOrder.verify(list2).add(2);
	}

	/*
	 * 确保模拟对象上无互动发生
	 */
	@Test
	public void test10() {
		List list1 = mock(List.class);
		List list2 = mock(List.class);
		List list3 = mock(List.class);

		// list1.addAll(list2);

		verifyZeroInteractions(list1, list2, list3);
	}

	/*
	 * 找出冗余的互动(即未被验证到的)
	 */
	@Test(expected = NoInteractionsWanted.class)
	public void test11() {
		List list = mock(List.class);
		list.add(1);
		list.add(2);

		verify(list, times(2)).add(anyInt());

		// 检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
		verifyZeroInteractions(list);

		List list2 = mock(List.class);
		list2.add(1);
		list2.add(2);

		verify(list2).add(1);
		// 检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
		verifyZeroInteractions(list2);
	}


}
