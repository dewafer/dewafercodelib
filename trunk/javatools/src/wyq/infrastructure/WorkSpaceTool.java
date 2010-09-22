/**
 * 
 */
package wyq.infrastructure;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * Workspace tool.
 * Create the workspace automatically.
 * @author dewafer
 * @version 2010/09/22 v1.0
 */
public class WorkSpaceTool extends PrintTool {

	
	private static final String TMP_WORKSPACE = "TEMP";
	private static String WORKSPACE = "";
	public static File workspace;

	static {
		File[] drives = File.listRoots();
		WORKSPACE = drives[0].getAbsolutePath() + "/" + TMP_WORKSPACE;
		createWorkspace();
	}

	/**
	 * If the folder of WORKSPACE dosen't exist, create the folders.
	 */
	public static void createWorkspace() {
		workspace = new File(WORKSPACE);
		if (!workspace.exists()) {
			workspace.mkdirs();
		}
	}

	/**
	 * Set the workspace, and create the folders.
	 * @param path
	 */
	public void setWorkspace(String path) {
		WORKSPACE = path;
		createWorkspace();
	}

	/**
	 * Get files in the workspace.
	 * @param regex
	 * @return
	 */
	public File[] getFilesFromWorkspace(final String regex) {

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

	/**
	 * Get a file. If is <code>fromWorkSpace</code>, return the file in workspace.
	 * @param fileName
	 * @param fromWorkSpace
	 * @return
	 */
	public File getFile(String fileName, boolean fromWorkSpace) {

		if (fromWorkSpace) {
			return new File(workspace, fileName);
		} else {
			return new File(fileName);
		}
	}

	
	/**
	 * Check the file's existence.
	 * @param fileName
	 * @return
	 */
	public boolean existsFile(String fileName) {
		File f = getFileFromWorkspace(fileName);
		return f.exists();
	}

	public File getFile(String fileName) {
		return getFile(fileName, false);
	}

	public File getFileFromWorkspace(String fileName) {
		return getFile(fileName, true);
	}
}
