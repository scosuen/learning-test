package com.ying.test.mockitotest.example1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationTest {

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
	
	/*
	 * @Captor
	 */
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
}
