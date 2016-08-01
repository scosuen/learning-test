package com.ying.test.mockitotest.example1;

import java.util.Map;

public class SupportClasses {

}

class Person {
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

interface PersonDao {
	public void update(Person person);
}

class PersonService {
	private PersonDao personDao;

	PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void update(int id, String name) {
		personDao.update(new Person(id, name));
	}
}

class MyDictionary {
	private Map<String, String> wordMap;

	public void add(final String word, final String meaning) {
		wordMap.put(word, meaning);
	}

	public String getMeaning(final String word) {
		return wordMap.get(word);
	}
}
