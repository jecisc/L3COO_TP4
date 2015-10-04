package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import plugins.ToLowerCase;

public class ToLowerCaseTest {

	@Test
	public void testWithoutParam() {
		ToLowerCase code = new ToLowerCase();
		assertEquals("Not the good transformation ?", code.transform("testTEST"), "testtest");
		assertEquals("Not the good label ?", code.getLabel(), "to lower case");
		assertEquals("Not the good help ?", code.helpMessage(), "Transform all the letters to lower case");
	}

}
