package com.ying.test.mockitotest.example1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class Test3 {

	@Mock
	private List mockList;

	/*
	 * public Test3() { MockitoAnnotations.initMocks(this); }
	 */

	@Test
	public void test1() {
		mockList.add(1);
		verify(mockList).add(1);
	}

	@Test(expected = RuntimeException.class)
	public void test2() {
		// 模拟连续调用返回期望值，如果分开，则只有最后一个有效

		when(mockList.get(0)).thenReturn(0);
		when(mockList.get(0)).thenReturn(1);
		when(mockList.get(0)).thenReturn(2);

		when(mockList.get(1)).thenReturn(3).thenReturn(4).thenThrow(new RuntimeException());

		// assertEquals(0, mockList.get(0));
		// assertEquals(1, mockList.get(0));
		assertEquals(2, mockList.get(0));

		assertEquals(3, mockList.get(1));
		assertEquals(4, mockList.get(1));

		mockList.get(1);
	}

	/*
	 * 使用回调生成期望值
	 */
	@Test
	public void test3() {
		when(mockList.get(anyInt())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Object[] strArray = invocation.getArguments();

				return "Input : " + strArray[0];
			}
		});

		assertEquals("Input : 1", mockList.get(1));
		assertEquals("Input : 2", mockList.get(2));
	}

	@Test
	public void test4() {
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

	/*
	 * 监控真实对象
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void test5() {
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
	 * 捕获参数来进一步断言
	 */
	@Test
	public void test6() {
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

	@Captor
	private ArgumentCaptor<Person> personArgument;

	@Test
	public void test7() {
		PersonDao personDao = mock(PersonDao.class);
		PersonService personService = new PersonService(personDao);

		personService.update(1, "Ying");

		verify(personDao).update(personArgument.capture());
		assertEquals(1, personArgument.getValue().getId());
		assertEquals("Ying", personArgument.getValue().getName());
	}

	/*
	 * 真实的部分mock
	 */
	@Test
	public void test8() {
		Person person = mock(Person.class);

		when(person.getId()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(person).setId(anyInt());

		assertEquals(0, person.getId());
	}

	/*
	 * 重置mock
	 */
	@Test
	public void test9() {
		List list = mock(List.class);
		when(list.size()).thenReturn(100);

		assertEquals(100, list.size());

		reset(list);

		assertEquals(0, list.size());
	}

	/*
	 * @InjectMocks
	 */
	@Mock
	private Map<String, String> wordMap;
	@InjectMocks
	private MyDictionary myDictionary = new MyDictionary();

	@Test
	public void test10() {
		when(wordMap.get("aWord")).thenReturn("aMeaning");

		assertEquals("aMeaning", myDictionary.getMeaning("aWord"));
	}

	/*
	 * unstubbed方法的返回策略  RETURNS_SMART_NULLS
	 */
	@Test
	public void test11() {

		List mock = mock(List.class, Mockito.RETURNS_SMART_NULLS);
		System.out.println(mock.get(0));
		System.out.println(mock.toArray().length);
	}
	
	/*
	 * unstubbed方法的返回策略  RETURNS_DEEP_STUBS  
	 */
	@Test
	public void test12 () {
		MyDictionary myDictionary = mock(MyDictionary.class, Mockito.RETURNS_DEEP_STUBS);
		System.out.println(myDictionary.getMeaning("12"));
	}

	private class Person {
		private int id;
		private String name;

		Person(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private interface PersonDao {
		public void update(Person person);
	}

	private class PersonService {
		private PersonDao personDao;

		PersonService(PersonDao personDao) {
			this.personDao = personDao;
		}

		public void update(int id, String name) {
			personDao.update(new Person(id, name));
		}
	}

	private class MyDictionary {
		private Map<String, String> wordMap;

		public void add(final String word, final String meaning) {
			wordMap.put(word, meaning);
		}

		public String getMeaning(final String word) {
			return wordMap.get(word);
		}
	}
}
