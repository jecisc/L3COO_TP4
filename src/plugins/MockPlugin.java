package plugins;

import plugin.Plugin;

public class MockPlugin implements Plugin {

	public String transform(String s) {
		return s;
	}

	public String getLabel() {
		return "Do Nothing";
	}

	public String helpMessage() {
		return "This plugin does nothing.";
	}

}
