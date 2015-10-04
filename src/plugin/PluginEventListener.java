package plugin;

import java.io.File;
import java.util.EventListener;

/**
 * A Plugin Event Listener is a class that can be update when we find a new plugin.
 */
public interface PluginEventListener extends EventListener {

	/**
	 * Define the action to do when we have to do an update.
	 * @param file the plugin we found.
	 */
	public void update(File file);
	
	/**
	 * Define the action when we want to delete a plugin.
	 * @param file the plugin we want to delete.
	 */
	public void deletePlugin(File file);
}
