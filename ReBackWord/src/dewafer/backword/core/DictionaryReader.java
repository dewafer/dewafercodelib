package dewafer.backword.core;

public interface DictionaryReader {

	static final String DICTINFO_AUTHOR = "#author";
	static final String DICTINFO_NAME = "#name";
	static final String DICTINFO_DESCRIPTION = "#description";
	static final String DICT_SPLITER = ",";

	int count();

	boolean open();

	void close();

	boolean hasNext();

	void reset();

	DictionaryWordEntity readWord();

	DictionaryWordEntity readWordAt(int line);

	DictionaryInfoEntity getDicInfo();

}
