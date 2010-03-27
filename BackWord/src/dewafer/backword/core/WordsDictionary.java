/**
 * 
 */
package dewafer.backword.core;

import java.util.List;

/**
 * @author dewafer
 *
 */
public class WordsDictionary {

	private List<Word> words;
	private int overtime;
	private int count;
	private String name;
	private String author;
	private String description;
	/**
	 * @return the words
	 */
	public List<Word> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(List<Word> words) {
		this.words = words;
	}
	/**
	 * @return the overtime
	 */
	public int getOvertime() {
		return overtime;
	}
	/**
	 * @param overtime the overtime to set
	 */
	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
