package wyq;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import wyq.infrastructure.FileTool;

public class Infrastructure {

	private FileTool _ftool = new FileTool();

	/**
	 * @param srcFile
	 * @param tarFile
	 * @throws IOException
	 * @see wyq.infrastructure.FileTool#copyFile(java.io.File, java.io.File)
	 */
	public void copyFile(File srcFile, File tarFile) throws IOException {
		_ftool.copyFile(srcFile, tarFile);
	}

	/**
	 * @param srcFileName
	 * @param tarFileName
	 * @throws IOException
	 * @see wyq.infrastructure.FileTool#copyFile(java.lang.String, java.lang.String)
	 */
	public void copyFile(String srcFileName, String tarFileName)
			throws IOException {
		_ftool.copyFile(srcFileName, tarFileName);
	}

	/**
	 * @param rootClz
	 * @param rscFileName
	 * @throws IOException
	 * @see wyq.infrastructure.FileTool#copyResFileToWorkspace(java.lang.Class, java.lang.String)
	 */
	public void copyResFileToWorkspace(Class<?> rootClz, String rscFileName)
			throws IOException {
		_ftool.copyResFileToWorkspace(rootClz, rscFileName);
	}

	/**
	 * @param srcFileName
	 * @throws IOException
	 * @see wyq.infrastructure.FileTool#copyResFileToWorkspace(java.lang.String)
	 */
	public void copyResFileToWorkspace(String srcFileName) throws IOException {
		_ftool.copyResFileToWorkspace(srcFileName);
	}

	/**
	 * @param fileName
	 * @return
	 * @see wyq.infrastructure.WorkSpaceTool#existsFile(java.lang.String)
	 */
	public boolean existsFile(String fileName) {
		return _ftool.existsFile(fileName);
	}

	/**
	 * @param byteFile
	 * @return
	 * @throws FileNotFoundException
	 * @see wyq.infrastructure.FileTool#getBufferedInputStream(java.io.File)
	 */
	public BufferedInputStream getBufferedInputStream(File byteFile)
			throws FileNotFoundException {
		return _ftool.getBufferedInputStream(byteFile);
	}

	/**
	 * @param byteFile
	 * @return
	 * @throws FileNotFoundException
	 * @see wyq.infrastructure.FileTool#getBufferedOutputStream(java.io.File)
	 */
	public BufferedOutputStream getBufferedOutputStream(File byteFile)
			throws FileNotFoundException {
		return _ftool.getBufferedOutputStream(byteFile);
	}

	/**
	 * @param txtFile
	 * @return
	 * @throws FileNotFoundException
	 * @see wyq.infrastructure.FileTool#getBufferedReader(java.io.File)
	 */
	public BufferedReader getBufferedReader(File txtFile)
			throws FileNotFoundException {
		return _ftool.getBufferedReader(txtFile);
	}

	/**
	 * @param txtFile
	 * @return
	 * @throws IOException
	 * @see wyq.infrastructure.FileTool#getBufferedWriter(java.io.File)
	 */
	public BufferedWriter getBufferedWriter(File txtFile) throws IOException {
		return _ftool.getBufferedWriter(txtFile);
	}

	/**
	 * @param fileName
	 * @param fromWorkSpace
	 * @return
	 * @see wyq.infrastructure.WorkSpaceTool#getFile(java.lang.String, boolean)
	 */
	public File getFile(String fileName, boolean fromWorkSpace) {
		return _ftool.getFile(fileName, fromWorkSpace);
	}

	/**
	 * @param fileName
	 * @return
	 * @see wyq.infrastructure.WorkSpaceTool#getFile(java.lang.String)
	 */
	public File getFile(String fileName) {
		return _ftool.getFile(fileName);
	}

	/**
	 * @param fileName
	 * @return
	 * @see wyq.infrastructure.WorkSpaceTool#getFileFromWorkspace(java.lang.String)
	 */
	public File getFileFromWorkspace(String fileName) {
		return _ftool.getFileFromWorkspace(fileName);
	}

	/**
	 * @param regex
	 * @return
	 * @see wyq.infrastructure.WorkSpaceTool#getFilesFromWorkspace(java.lang.String)
	 */
	public File[] getFilesFromWorkspace(String regex) {
		return _ftool.getFilesFromWorkspace(regex);
	}

	/**
	 * @param o
	 * @see wyq.infrastructure.PrintTool#println(java.lang.Object)
	 */
	public void println(Object o) {
		_ftool.println(o);
	}

	/**
	 * @param path
	 * @see wyq.infrastructure.WorkSpaceTool#setWorkspace(java.lang.String)
	 */
	public void setWorkspace(String path) {
		_ftool.setWorkspace(path);
	}
	
}
