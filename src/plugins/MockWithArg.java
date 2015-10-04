package plugins;

import plugin.Plugin;

/**
 * A Mock for check if we accept a plugin without constructor without arg
 *
 */
public class MockWithArg implements Plugin {

	public MockWithArg(int i) {
	}

	
	public String transform(String s) {
		return s;
	}

	public String getLabel() {
		return "";
	}

	public String helpMessage() {
		return "";
	}
}
