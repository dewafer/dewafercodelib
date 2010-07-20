package wyq.entity.impl;

import wyq.entity.Word;

public class WordEntityImpl implements Word {

	private String key;

	private String value;

	/* (non-Javadoc)
	 * @see wyq.entity.Word#getKey()
	 */
	public String getKey() {
		return key;
	}

	/* (non-Javadoc)
	 * @see wyq.entity.Word#setKey(java.lang.String)
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/* (non-Javadoc)
	 * @see wyq.entity.Word#getValue()
	 */
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see wyq.entity.Word#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
