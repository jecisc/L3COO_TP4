package plugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

/**
 * PluginFinder will return all the .class inside a folder than we pass in parameter.
 */
public class PluginFinder implements ActionListener {

	protected final File folder;
	
	protected Set<File> alreadyFoundFiles = new HashSet<File>();
	
	protected List<PluginEventListener> listeners = new ArrayList<PluginEventListener>();
	
	protected final Timer myTimer;
	
	protected static final int TIMER_DELAY = 1000;
	
	/**
	 *  Constructor of a PluginFinder. This method takes a name of File which is a folder we need to check.
	 *  @param folder the folder we need to check.
	 */
	public PluginFinder(File folder) {
		this.folder = folder;
		this.myTimer = new Timer(PluginFinder.TIMER_DELAY, this);
	}
	
	/**
	 * Launch the timer of the PluginFinder.
	 */
	public void startTimer(){
		this.myTimer.start();
	}
	
	/**
	 * Stop the Timer of the plugiFinder.
	 */
	public void stopTimer(){
		this.myTimer.stop();
	}

	/**
	 * This method add a new listener.
	 * @param listener will receive the new plugin when we find one.
	 * @return True if the listener is add. Else return false.
	 */
	public boolean addListener(PluginEventListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * This method remove a listener of this PluginFinder.
	 * @param listener the listener we want to remove.
	 * @return true if the listener is find and remove.
	 */
	public boolean removeListener(PluginEventListener listener){
		return this.listeners.remove(listener);
	}
	
	/**
	 * This method get all the file which finish by .class in the folder.
	 * @return the file which finish by .class.
	 */
	public Set<File> selectFiles(){
		PluginFilter plugin = new PluginFilter();
		Set<File> result = new HashSet<File>();
		for(File file:this.folder.listFiles()){
			if(plugin.accept( folder , file.getName()))
					result.add(file);
		}
		return result;
	}
	
	/**
	 * Every tic of the timer we'll see if we have to add or delete some plugins.
	 */
	public void actionPerformed(ActionEvent e) {
		Set<File> filesFound = this.selectFiles();
		this.checkForUpdate(filesFound);
		this.checkForDelete(filesFound);
	}
	
	/**
	 * This method will add a plugin from all the listeners
	 * @param plugin the plugin we want to add.
	 */
	public void updateAllListeners(File plugin){
		for(PluginEventListener listener: this.listeners){
			listener.update(plugin);
		}
	}
	
	/**
	 * This method will delete a plugin from all the listeners.
	 * @param plugin the plugin we want to delete.
	 */
	public void deletePluginFromAllListeners(File plugin){
		for(PluginEventListener listener: this.listeners){
			listener.deletePlugin(plugin);
		}
		
	}
	
	/**
	 * This method will check a list of plugins and add the plugins which are not on the plugins already found.
	 * @param filesToCheck the list we need to check.
	 */
	public void checkForUpdate(Set<File> filesToCheck){
		for (File plug : filesToCheck){
			if(!this.alreadyFoundFiles.contains(plug)){
				this.updateAllListeners(plug);
				this.alreadyFoundFiles.add(plug);
			}
		}
	}
	
	/**
	 * This method will check a list of plugins and delete the plugins missing from the plugins we found earlier.
	 * @param filesToCheck the lis we need to check.
	 */
	public void checkForDelete(Set<File> filesToCheck){
		Set<File> tmpAlreadyFound = new HashSet<File>(this.alreadyFoundFiles);
		for (File plug : tmpAlreadyFound){
			if(!filesToCheck.contains(plug)){
				this.deletePluginFromAllListeners(plug);
				this.alreadyFoundFiles.remove(plug);
			}
		}
	}

}
