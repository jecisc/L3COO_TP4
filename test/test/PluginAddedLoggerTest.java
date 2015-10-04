package test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plugin.PluginAddedLogger;

public class PluginAddedLoggerTest {

	File file;
	int cpt;
	PluginAddedLoggerChange log;
	
	@Before
	public void setUp(){
			file = new File ("classes/MockPlugin.class");
			log = new PluginAddedLoggerChange();
			cpt = 0;
	}

	@After
	public void tearDown(){
		file = null;
		log = null;
	}

	public class PluginAddedLoggerChange extends PluginAddedLogger{
		
		public void update(File file) {
			super.update(file); cpt++;
		}
		
		public void deletePlugin(File file){
			super.deletePlugin(file);
			cpt--;
		}
	}
	
	@Test
	public void testPrint() {
		log.update(file);
		assertEquals("not add ?", cpt, 1);
		log.deletePlugin(file);
		assertEquals("not remove ?", cpt, 0);
	}

}
