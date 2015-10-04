package test;

import static org.junit.Assert.*;

import org.junit.Test;

import plugins.CesarCode;

public class CesarCodeTest {

	@Test
	public void testWithTwo() {
		CesarCode code = new CesarCode(2);
		assertEquals("Not the good transformation ?", code.transform("test"), "vguv");
	}
	
	@Test
	public void testWithoutParam() {
		CesarCode code = new CesarCode();
		assertEquals("Not the good transformation ?", code.transform("testTEST"), "uftuUFTU");
		assertEquals("Not the good label ?", code.getLabel(), "Cesar Code 1");
		assertEquals("Not the good help ?", code.helpMessage(), "Apply a Cesar code to the text (ie. a shift by 1 character)");
	}

}
