package com.ying.test.mockitotest.example1;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class TestMap {

	@Test
	public void city() {
		Map mapMock = Mockito.mock(Map.class);
		Mockito.when(mapMock.get("city")).thenReturn("Shenzhen");
		
		assertEquals("city test", "Shenzhen", mapMock.get("city"));
		
		Mockito.verify(mapMock).get(Matchers.eq("city"));
		Mockito.verify(mapMock, Mockito.times(2));
	}

}
