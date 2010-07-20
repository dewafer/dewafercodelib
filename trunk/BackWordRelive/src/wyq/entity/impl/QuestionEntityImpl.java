package wyq.entity.impl;

import java.util.List;

import wyq.entity.Answer;
import wyq.entity.Question;

public class QuestionEntityImpl implements Question {

	private String text;

	private List<Answer> answers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Question#getText()
	 */
	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Question#setText(java.lang.String)
	 */
	public void setText(String description) {
		this.text = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Question#getAnswers()
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Question#setAnswers(java.util.List)
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
