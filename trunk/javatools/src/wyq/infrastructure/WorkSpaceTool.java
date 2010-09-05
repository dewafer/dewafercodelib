/**
 * 
 */
package wyq.infrastructure;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * @author dewafer
 * 
 */
public class WorkSpaceTool extends PrintTool {

	private static String WORKSPACE = "";
	public static File workspace;

	static {
		createWorkspace();
	}

	public static void createWorkspace() {
		workspace = new File(WORKSPACE);
		if (!workspace.exists()) {
			workspace.mkdirs();
		}
	}

	public static void setWorkspace(String path) {
		WORKSPACE = path;
		createWorkspace();
	}

	public static File[] getFilesFromWorkspace(final String regex) {

		if (workspace == null) {
			createWorkspace();
		}

		return workspace.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return Pattern.matches(regex, name);
			}
		});
	}

	public static File getFile(String fileName, boolean fromWorkSpace) {

		if (fromWorkSpace) {
			return new File(workspace, fileName);
		} else {
			return new File(fileName);
		}
	}

	public static File getFile(String fileName) {
		return getFile(fileName, false);
	}

	public static File getFileFromWorkspace(String fileName) {
		return getFile(fileName, true);
	}
}
