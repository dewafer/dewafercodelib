package dewafer.backword.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class DictionaryReaderImpl {

	private String path;
	private List<String> lines = new ArrayList<String>();
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private int linecount = 0;
	private int current = 0;

	public DictionaryReaderImpl(String path) {
		this.path = path;
	}

	public boolean open() {
		boolean result = false;
		if (path == null)
			return result;
		File f = new File(path);
		if (f.isDirectory())
			return result;
		if (!f.exists())
			return result;
		try {
			fileReader = new FileReader(f);
			bufferedReader = new BufferedReader(fileReader);
			String tmp;
			while((tmp = bufferedReader.readLine())!=null){
				lines.add(tmp);
				linecount++;
			}
			result = true;
			bufferedReader.close();
			fileReader.close();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}finally{
			fileReader = null;
			bufferedReader = null;
		}
		return result;
	}

	public void close() {
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufferedReader = null;
		}
	}

	public boolean hasNext() {
		if (bufferedReader == null)
			return false;
		return false;
	}

	public String read() {
		if(current >= linecount)
		{
			return null;
		}
		String tmp = lines.get(current);
		current++;
		return tmp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
