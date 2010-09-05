package wyq.infrastructure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class FileTool extends WorkSpaceTool {

	public static void copyFile(File srcFile, File tarFile) throws IOException {
		BufferedInputStream in = getBufferedInputStream(srcFile);
		BufferedOutputStream out = getBufferedOutputStream(tarFile);
		int buff = -1;
		while ((buff = in.read()) != -1) {
			out.write(buff);
		}
		in.close();
		out.flush();
		out.close();
	}

	public static void copyFile(String srcFileName, String tarFileName)
			throws IOException {
		copyFile(getFileFromWorkspace(srcFileName),
				getFileFromWorkspace(tarFileName));
	}

	public static void copyResFileToWorkspace(Class<?> rootClz,
			String rscFileName) throws IOException {
		URL res = rootClz.getResource(rscFileName);
		if (res == null) {
			throw new FileNotFoundException();
		}
		copyFile(getFile(res.getFile()), getFileFromWorkspace(rscFileName));
	}

	public static void copyResFileToWorkspace(String srcFileName)
			throws IOException {
		copyResFileToWorkspace(FileTool.class, srcFileName);
	}

	public static BufferedReader getBufferedReader(File txtFile)
			throws FileNotFoundException {
		return new BufferedReader(new FileReader(txtFile));
	}

	public static BufferedInputStream getBufferedInputStream(File byteFile)
			throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(byteFile));
	}

	public static BufferedWriter getBufferedWriter(File txtFile)
			throws IOException {
		return new BufferedWriter(new FileWriter(txtFile));
	}

	public static BufferedOutputStream getBufferedOutputStream(File byteFile)
			throws FileNotFoundException {
		return new BufferedOutputStream(new FileOutputStream(byteFile));
	}

}
