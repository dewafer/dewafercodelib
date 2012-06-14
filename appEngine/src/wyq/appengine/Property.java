package wyq.appengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property extends Properties implements Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323409154240652900L;

	public Property(Properties p) {
		super(p);
	}

	public Property(String p) {
		super();
		loadPropertyFile(p);
	}

	public Property() {
		super();
		loadPropertyFile(null);
	}

	protected void loadPropertyFile(String name) {
		Class<?> clazz = this.getClass();
		if (name == null) {
			name = clazz.getSimpleName();
		}
		InputStream resourceAsStream = clazz.getResourceAsStream(name);
		if (resourceAsStream != null) {
			try {
				loadAction(resourceAsStream);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void load(String file) {
		loadPropertyFile(file);
	}

	protected void loadAction(InputStream in) throws IOException {
		load(in);
	}
}
