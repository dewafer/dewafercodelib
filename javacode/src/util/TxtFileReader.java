package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * txt文件阅读器
 * 
 * @author dewafer
 * @version 0.1
 */
public class TxtFileReader {

	/**
	 * 将指定txt文件读为字符串。
	 * 
	 * @param f
	 *            要读的文件
	 * @return String
	 * @throws IOException
	 */
	public static String readAll(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * 等同于readAll(new File(path))
	 * 
	 * @see TxtFileReader#readAll(File)
	 * @param path
	 * @return String
	 * @throws IOException
	 */
	public static String readAll(String path) throws IOException {
		File f = new File(path);
		return readAll(f);
	}

}
