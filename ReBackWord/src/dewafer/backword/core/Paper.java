package dewafer.backword.core;

import java.util.Iterator;


/**
 * @author dewafer
 * 
 */
public class Paper implements Iterable<Quiz> {
	// public class Paper {

	private List<Quiz> unfinishedQuizList;
	private List<Quiz> finishedQuizList;
	private List<Quiz> finishedWrongQuizList;
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
	protected Paper(List<Quiz> unfinishedQuizList, List<Quiz> finishedQuizList,
			List<Quiz> finishedWrongQuizList, String name, String author,
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
			if (finishedQuizList != null)
				finishedQuizList.add(q);
		} else {
			if (finishedWrongQuizList != null)
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
	public List<Quiz> getFinishedQuizList() {
		return finishedQuizList;
	}

	/**
	 * @return the finishedWrongQuizList
	 */
	public List<Quiz> getFinishedWrongQuizList() {
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
	public List<Quiz> getUnfinishedQuizList() {
		return unfinishedQuizList;
	}

	@Override
	public Iterator<Quiz> iterator() {
		return new Iterator<Quiz>() {

			Iterator<Quiz> itr = unfinishedQuizList.iterator();

			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}

			@Override
			public Quiz next() {
				Quiz q = itr.next();
				itr.remove();
				return q;
			}

			@Override
			public void remove() {
				itr.remove();
			}

		};
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
	protected void setFinishedQuizList(List<Quiz> finishedQuizList) {
		this.finishedQuizList = finishedQuizList;
	}

	/**
	 * @param finishedWrongQuizList
	 *            the finishedWrongQuizList to set
	 */
	protected void setFinishedWrongQuizList(List<Quiz> finishedWrongQuizList) {
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
	protected void setUnfinishedQuizList(List<Quiz> unfinishedQuizList) {
		this.unfinishedQuizList = unfinishedQuizList;
	}

}
