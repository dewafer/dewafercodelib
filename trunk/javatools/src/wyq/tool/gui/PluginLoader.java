package wyq.tool.gui;

import wyq.infrastructure.PropertySupporter;
import wyq.infrastructure.SmartExtClasspathLoader;

public class PluginLoader {

    private String plugin_path;

    PluginLoader() {

    }

    public void load(String pluginPath) {
	if (pluginPath != null) {
	    SmartExtClasspathLoader.loadClasspath(pluginPath);
	}
    }

    public static void load() {
	PluginLoader target = new PluginLoader();
	PropertySupporter.inject(target);
	target.load(target.plugin_path);
    }
}
