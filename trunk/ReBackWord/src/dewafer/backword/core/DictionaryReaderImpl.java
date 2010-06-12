package dewafer.backword.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryReaderImpl implements DictionaryReader {

	private String path;
	private List<DictionaryWordEntity> words = new ArrayList<DictionaryWordEntity>();
	private DictionaryInfoEntity info = new DictionaryInfoEntity();
	private BufferedReader bufferedReader;
	private FileReader fileReader;
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
			String[] splited;
			while ((tmp = bufferedReader.readLine()) != null) {
				splited = tmp.split(DICT_SPLITER);
				splited[0] = splited[0].replace("\"", "");
				splited[1] = splited[1].replace("\"", "");
				if (splited[0].equalsIgnoreCase(DICTINFO_AUTHOR)) {
					info.setAuthor(splited[1]);
					continue;
				} else if (splited[0].equalsIgnoreCase(DICTINFO_DESCRIPTION)) {
					info.setDescription(splited[1]);
					continue;
				} else if (splited[0].equalsIgnoreCase(DICTINFO_NAME)) {
					info.setName(splited[1]);
					continue;
				} else {
					DictionaryWordEntity word = new DictionaryWordEntity();
					word.setWord(splited[0]);
					word.setExplain(splited[1]);
					words.add(word);
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public void close() {
		try {
			if (bufferedReader != null)
				bufferedReader.close();
			if (fileReader != null)
				fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufferedReader = null;
			fileReader = null;
			words = null;
			info = null;
			current = 0;
			path = null;
		}
	}

	public boolean hasNext() {
		return current < words.size();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DictionaryReader reader = new DictionaryReaderImpl(
				DictionaryReaderImpl.class.getResource("/demodict.csv")
						.getFile());
		if (reader.open()) {
			println(reader);
			println(reader.count());
			println(reader.getDicInfo());
			println(reader.hasNext());
			println(reader.readWordAt(0));
			println(reader.readWordAt(1));
			println(reader.readWordAt(2));
			println(reader.readWord());
			println(reader.readWord());
			println(reader.readWord());
			reader.reset();
			println(reader.readWord());
		} else {
			println("open failed");
		}
	}

	private static void println(Object o) {
		System.out.println(o);
	}

	@Override
	public int count() {
		return words.size();
	}

	@Override
	public DictionaryInfoEntity getDicInfo() {
		return info;
	}

	@Override
	public DictionaryWordEntity readWord() {
		if (current < words.size())
			return words.get(current++);
		else
			return null;
	}

	@Override
	public DictionaryWordEntity readWordAt(int line) {
		if (line < 0 || line >= words.size())
			return null;
		else
			return words.get(line);
	}

	@Override
	public void reset() {
		current = 0;
	}

}
