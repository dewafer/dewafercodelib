package wyq.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyTool {

	private Properties _prop;

	public PropertyTool() {
		_prop = new Properties();
	}

	public PropertyTool(Properties defaults) {
		_prop = new Properties(defaults);
	}

	public static PropertyTool LoadFromFile(File f, boolean usingXML)
			throws FileNotFoundException, IOException {
		if (!f.exists()) {
			return new PropertyTool();
		}
		Properties def = new Properties();
		if (!usingXML) {
			def.load(new FileReader(f));
		} else {
			def.loadFromXML(new FileInputStream(f));
		}
		return new PropertyTool(def);
	}

	public static PropertyTool LoadFromFile(File f)
			throws FileNotFoundException, IOException {
		return LoadFromFile(f, false);
	}

	public static PropertyTool LoadFromFile(String file)
			throws FileNotFoundException, IOException {
		return LoadFromFile(new File(file), false);
	}

}
