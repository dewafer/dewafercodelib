package dewafer.backword.core;

public interface DictionaryReader {

	static final int WORD = 0;
	static final int EXPLAIN = 1;
	
	static final int DICT_INFO_AUTHOR = 1;
	static final int DICT_INFO_NAME = 0;
	static final int DICT_INFO_DESC = 2;
	
	
	int count();
	
	void open();
	
	void close();
	
	boolean hasNext();
	
	void reset();
	
	String[] read();
	
	String[] read(int line);
	
	String[] getDicInfo();
	
}
