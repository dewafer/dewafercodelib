package dewafer.backword.core;

import dewafer.backword.core.mobiadd.Iterator;
import dewafer.backword.core.mobiadd.List;

/**
 * @author dewafer
 * 
 */
public class Paper {
//	public class Paper {

	
	private List unfinishedQuizList;
	private List finishedQuizList;
	private List finishedWrongQuizList;
	private String name;
	private String author;
	private String description;

	protected Paper() {
	}

	/**
	 * @param unfinishedQuizList
	 * @param finishedQuizList
	 * @param finishedWrongQuizList
	 * @param name
	 * @param author
	 * @param description
	 */
	protected Paper(List unfinishedQuizList, List finishedQuizList,
			List finishedWrongQuizList, String name, String author,
			String description) {
		super();
		this.unfinishedQuizList = unfinishedQuizList;
		this.finishedQuizList = finishedQuizList;
		this.finishedWrongQuizList = finishedWrongQuizList;
		this.name = name;
		this.author = author;
		this.description = description;
	}

	protected void finishQuiz(Quiz q) {
		if (!q.isFinished() || !unfinishedQuizList.contains(q)) {
			return;
		}
		if (q.isCorrect() && !q.isAbandoned()) {
			finishedQuizList.add(q);
		} else {
			finishedWrongQuizList.add(q);
		}
		unfinishedQuizList.remove(q);
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the finishedQuizList
	 */
	public List getFinishedQuizList() {
		return finishedQuizList;
	}

	/**
	 * @return the finishedWrongQuizList
	 */
	public List getFinishedWrongQuizList() {
		return finishedWrongQuizList;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the unfinishedQuizList
	 */
	public List getUnfinishedQuizList() {
		return unfinishedQuizList;
	}


	public Iterator iterator() {
		// TODO Auto-generated method stub
		return unfinishedQuizList.iterator();
	}

	/**
	 * @param author
	 *            the author to set
	 */
	protected void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param finishedQuizList
	 *            the finishedQuizList to set
	 */
	protected void setFinishedQuizList(List finishedQuizList) {
		this.finishedQuizList = finishedQuizList;
	}

	/**
	 * @param finishedWrongQuizList
	 *            the finishedWrongQuizList to set
	 */
	protected void setFinishedWrongQuizList(List finishedWrongQuizList) {
		this.finishedWrongQuizList = finishedWrongQuizList;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @param unfinishedQuizList
	 *            the unfinishedQuizList to set
	 */
	protected void setUnfinishedQuizList(List unfinishedQuizList) {
		this.unfinishedQuizList = unfinishedQuizList;
	}

}
