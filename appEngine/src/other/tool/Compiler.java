package other.tool;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class Compiler {

	public boolean compileFile(String fullFileName, String output)
			throws IOException {
		boolean result = false;
		// get compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// define the diagnostic object, which will be used to save the
		// diagnostic information
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		// get StandardJavaFileManager object, and set the diagnostic for the
		// object
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);

		// set class output location
		Location location = StandardLocation.CLASS_OUTPUT;
		try {
			fileManager.setLocation(location,
					Arrays.asList(new File[] { new File(output) }));

			// get JavaFileObject object, it will specify the java source file.
			Iterable<? extends JavaFileObject> itr = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(new File(
							fullFileName)));

			// compile the java source code by using CompilationTask's call
			// method
			result = compiler.getTask(null, fileManager, diagnostics, null,
					null, itr).call();

			// print the Diagnostic's information
			for (Diagnostic<? extends JavaFileObject> diag : diagnostics
					.getDiagnostics()) {
				System.out.println("Error on line: " + diag.getLineNumber()
						+ "; URI: " + diag.getSource().toString());
			}
		} catch (IOException e) {
			// exception process
			System.out.println("IO Exception: " + e);
			throw e;
		} finally {
			// close file manager
			if (null != fileManager) {
				fileManager.close();
			}
		}
		return result;
	}
}