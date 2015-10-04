package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import plugin.PluginAddedLogger;
import plugin.PluginEventListener;
import plugin.PluginFinder;

public class PluginFinderTest {
	
	PluginFinder plug;
	PluginFinderTimer plug2 ;
	PluginEventListener listener;
	File folder;
	int cpt;
	
	@Before
	public void setUp(){
		folder = new File("classes/plugins");
		plug = new PluginFinder(this.folder);
		plug2 = new PluginFinderTimer(folder);
		listener = new PluginAddedLogger();
		cpt = 0;
	}

	@After
	public void tearDown(){
		plug= null;
		listener = null;
	}
	
	public class PluginFinderTimer extends PluginFinder{

		public PluginFinderTimer(File folder) {
			super(folder);
		}
		
		public void actionPerformed(ActionEvent e){
			super.actionPerformed(e);
			cpt++;
		}
		
	}

	@Test
	public void testTimer(){
		assertEquals("cpt not initialize ?", cpt, 0);
		plug2.startTimer();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		plug2.stopTimer();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("cpt still at 0 ?", cpt, 2);
	}
	
	@Test
	public void testListenerAddOrRemove(){
		assertTrue("Not add ?", plug.addListener(listener));
		assertTrue("Not remove ?", plug.removeListener(listener));
		assertFalse("Remove ?", plug.removeListener(new PluginAddedLogger()));
	}
	
	@Test
	public void testSelectFile() {
		Set<File> pluginsSet = plug.selectFiles();
		assertTrue("Don't contains the MockPlugin ?", pluginsSet.size() > 0);
	}
	
	@Test
	public void testMoveFile(){
		plug.addListener(listener);
		plug.startTimer();
		int nbPlug = plug.selectFiles().size();
		assertTrue("Zero plugin ?", nbPlug > 0);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		File source = new File("classes/plugins/MockPlugin.class");
		File destination = new File("classes/MockPlugin.class");
		source.renameTo(destination);
		
		assertEquals ("Not the good number of plugins ?", plug.selectFiles().size(), nbPlug -1);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		source = new File("classes/MockPlugin.class");
		destination = new File("classes/plugins/MockPlugin.class");
		source.renameTo(destination);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals ("Not the good number of plugins ?", plug.selectFiles().size(), nbPlug);
		
		
	}

}
