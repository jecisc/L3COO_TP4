package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plugin.PluginFilter;

public class PluginFilterTest {

	PluginFilter plugin;
	File folder;
	@Before
	public void setUp(){
		plugin = new PluginFilter();
		folder = new File("/classes");
	}

	@After
	public void tearDown(){
		plugin = null;
	}

	@Test
	public void testAccept() {
		assertTrue("Not a good file ?", plugin.accept(folder, "MockPlugin.class"));
	    assertFalse("A good file ?", plugin.accept(folder, "test.pasclass"));
	}
	
	@Test
	public void testAcceptWithConstructor(){
		assertFalse("accept it ?", plugin.accept(folder, "MockWithArg.class"));
	}

}
