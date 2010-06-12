package dewafer.backword.core;

public class DictionaryEntity {

	private DictionaryInfoEntity info;

	private List<DictionaryWordEntity> words;

	/**
	 * @return the info
	 */
	public DictionaryInfoEntity getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(DictionaryInfoEntity info) {
		this.info = info;
	}

	/**
	 * @return the words
	 */
	public List<DictionaryWordEntity> getWords() {
		return words;
	}

	/**
	 * @param words
	 *            the words to set
	 */
	public void setWords(List<DictionaryWordEntity> words) {
		this.words = words;
	}

}
