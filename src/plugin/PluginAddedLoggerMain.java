package plugin;

import java.io.File;

/**
 * Launch the text version of the plugin.
 */
public class PluginAddedLoggerMain {

	public static void main(String[] args) {
		PluginFinder plug = new PluginFinder(new File("classes/plugins"));
		plug.addListener(new PluginAddedLogger());
		plug.startTimer();
		while(true);

	}

}
