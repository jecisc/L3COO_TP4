package plugin;

import java.io.File;

/**
 * PluginAddedLogger implements PluginEventListener and define what action to do when we find a plugin.
 */
public class PluginAddedLogger implements PluginEventListener {

	/**
	 * This will print that we found a plugin and print which plugin this is.
	 */
	public void update(File file) {
		System.out.println("We found a Plugin : " +file.getName());
	}

	/**
	 * This will print that we delete a plugin and print which plugin this is.
	 */
	public void deletePlugin(File file){
		System.out.println("We deleted a Plugin : " + file.getName());
	}
}
