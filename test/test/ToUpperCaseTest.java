package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import plugins.ToUpperCase;

public class ToUpperCaseTest {

	@Test
	public void testWithoutParam() {
		ToUpperCase code = new ToUpperCase();
		assertEquals("Not the good transformation ?", code.transform("testTEST"), "TESTTEST");
		assertEquals("Not the good label ?", code.getLabel(), "to upper case");
		assertEquals("Not the good help ?", code.helpMessage(), "Transform all the letters to upper case");
	}

}
