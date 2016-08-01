package com.ying.test.mockitotest.example1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * http://blog.csdn.net/sdyy321/article/details/38757135/#
 * 
 * @author Ying
 *
 */
public class Test1 {
	
	
	/**
	 * Mock对象
	 * 
	 * unstubbed方法的返回策略  RETURNS_SMART_NULLS
	 */
	@Test
	public void test0_1() {

		List mock = mock(List.class, Mockito.RETURNS_SMART_NULLS);
		System.out.println(mock.get(0));
		System.out.println(mock.toArray().length);
	}
	
	/*
	 * unstubbed方法的返回策略  RETURNS_DEEP_STUBS  
	 */
	@Test
	public void test0_2 () {
		MyDictionary myDictionary = mock(MyDictionary.class, Mockito.RETURNS_DEEP_STUBS);
		System.out.println(myDictionary.getMeaning("12"));
	}
	

	/*
	 * 重置mock
	 */
	@Test
	public void test0_3() {
		List list = mock(List.class);
		when(list.size()).thenReturn(100);

		assertEquals(100, list.size());

		reset(list);

		assertEquals(0, list.size());
	}
	
	/**
	 * 验证行为
	 */
	@Test
	public void test1_1() {
		List mockList = mock(List.class);

		mockList.add(1);
		mockList.clear();

		verify(mockList).add(1);
		verify(mockList).clear();
	}

	/*
	 * 验证未曾执行的方法
	 */
	@Test
	public void test1_2() {
		List list = mock(List.class);
		list.get(0);

		verify(list, never()).get(1);
	}

	/*
	 * 验证确切的调用次数
	 */
	@Test
	public void test1_3() {

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
	 * 确保模拟对象上无互动发生
	 */
	@Test
	public void test1_4() {
		List list1 = mock(List.class);
		List list2 = mock(List.class);
		List list3 = mock(List.class);

		// list1.add(1);

		// list1.addAll(list2);

		verifyZeroInteractions(list1, list2, list3);
	}

	@Test
	public void Test1_5() {
		List list = mock(List.class);
		// list.add(1);

		verifyNoMoreInteractions(list);
	}

	/*
	 * 找出冗余的互动(即未被验证到的)
	 */
	@Test(expected = NoInteractionsWanted.class)
	public void test1_6() {
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

	@Test
	public void test1_7() throws InterruptedException {
		List list = mock(List.class);
		list.add("string1");
		list.add("string2");
		list.add("string2");

		verify(list, timeout(100)).add("string1");
		verify(list, timeout(100).times(2)).add("string2");
		verify(list, timeout(100).atLeast(2)).add("string2");
	}

	/*
	 * 验证执行顺序
	 */
	@Test
	public void test1_8() {

		List list1 = mock(List.class);
		List list2 = mock(List.class);

		list1.add(1);
		list1.add(1);
		list2.add(1);
		list1.add(2);
		list2.add(2);

		// 将需要排序的mock对象放入InOrder
		InOrder inOrder = inOrder(list1, list2);

		// 下面的代码不能颠倒顺序，验证执行顺序
		inOrder.verify(list1, times(2)).add(1);
		inOrder.verify(list2).add(1);
		inOrder.verify(list1).add(2);
		inOrder.verify(list2).add(2);
	}

	@Test
	public void test1_9() {
		Comparator comparator = mock(Comparator.class);

		comparator.compare("1", "2");

		// 如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
		verify(comparator).compare(anyString(), eq("2"));
		verify(comparator).compare(eq("1"), eq("2"));
		// 下面的为无效的参数匹配使用
		// verify(comparator).compare(anyString(),"hello");
	}

	/**
	 * 模拟我们所期望的结果
	 */
	@Test
	public void test2_1() {
		List list = mock(List.class);
		// 模拟连续调用返回期望值，如果分开，则只有最后一个有效
		when(list.get(0)).thenReturn(0);
		when(list.get(0)).thenReturn(1);
		when(list.get(0)).thenReturn(2);

		assertEquals(2, list.get(0));

		// 如果实际调用的次数超过了stub过的次数，则会一直返回最后一次stub的值
		when(list.get(1)).thenReturn(0).thenReturn(1).thenReturn(2);
		assertEquals(0, list.get(1));
		assertEquals(1, list.get(1));
		assertEquals(2, list.get(1));

		assertEquals(2, list.get(1));
		assertEquals(2, list.get(1));

		// 另一种写法
		doReturn("a").doReturn("b").doReturn("c").when(list).get(2);
		assertEquals("a", list.get(2));
		assertEquals("b", list.get(2));
		assertEquals("c", list.get(2));

		assertEquals("c", list.get(2));
		assertEquals("c", list.get(2));
	}

	@Test(expected = IOException.class)
	public void test2_2() throws IOException {
		OutputStream outputStream = mock(OutputStream.class);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

		doThrow(new IOException()).when(outputStream).close();

		outputStreamWriter.close();
	}

	/*
	 * 模拟方法体抛出异常
	 */
	@Test(expected = RuntimeException.class)
	public void test2_3() {
		List list = mock(List.class);

		// 2种写法
		// doThrow(new RuntimeException()).when(list).add(10);
		when(list.add(10)).thenThrow(new RuntimeException());

		// void 方法只能用doThrow()
		doThrow(new RuntimeException()).when(list).clear();

		list.add(10);
		list.clear();
	}

	/*
	 * 真实的部分mock
	 */
	@Test
	public void test2_4() {
		Person person = mock(Person.class);

		when(person.getId()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(person).setId(anyInt());

		assertEquals(0, person.getId());
	}

	/**
	 * 监控真实对象 spy
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void test4() {
		List list = new LinkedList();

		List spy = spy(list);

		// 下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
		// when(spy.get(0)).thenReturn(3);
		// 使用doReturn-when可以避免when-thenReturn调用真实对象api
		doReturn(999).when(spy).get(999);

		// 预设size()期望值
		// when(spy.size()).thenReturn(100);
		doReturn(101).when(spy).size();

		spy.add(1);
		spy.add(2);

		assertEquals(101, spy.size());
		assertEquals(1, spy.get(0));
		assertEquals(2, spy.get(1));

		// 抛出越界异常
		spy.get(2);
	}

	/*
	 * 捕获参数来进一步断言 ArgumentCaptor
	 */
	@Test
	public void test5() {
		List list1 = mock(List.class);
		List list2 = mock(List.class);

		list1.add(1);
		list2.add(1);
		list2.add(2);

		ArgumentCaptor<Integer> argumentCaptor1 = ArgumentCaptor.<Integer> forClass(Integer.class);
		ArgumentCaptor<Integer> argumentCaptor2 = ArgumentCaptor.<Integer> forClass(Integer.class);

		verify(list1).add(argumentCaptor1.capture());
		assertEquals(Integer.valueOf(1), argumentCaptor1.getValue());

		verify(list2, times(2)).add(argumentCaptor2.capture());
		assertEquals(Integer.valueOf(2), argumentCaptor2.getValue());
		assertArrayEquals(new Integer[] { 1, 2 }, argumentCaptor2.getAllValues().toArray());
	}

	/*
	 * 匹配任意参数 ArgumentMatcher
	 */
	@Test
	public void test6() {
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

	/*
	 * 使用回调生成期望值 Answer
	 */
	@Test
	public void test7_1() {
		List list = mock(List.class);

		when(list.get(anyInt())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Object[] strArray = invocation.getArguments();

				return "Input : " + strArray[0];
			}
		});

		assertEquals("Input : 1", list.get(1));
		assertEquals("Input : 2", list.get(2));
	}

	@Test
	public void test7_2() {
		List list = mock(List.class, new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return 123;
			}
		});

		// 下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
		assertEquals(123, list.get(12));
		// 下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
		assertEquals(123, list.size());
	}

}
