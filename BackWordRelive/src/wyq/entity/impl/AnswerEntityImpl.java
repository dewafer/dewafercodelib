package wyq.entity.impl;

import wyq.entity.Answer;

public class AnswerEntityImpl implements Answer {

	private String text;

	private boolean correct;

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Answer#getText()
	 */
	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Answer#setText(java.lang.String)
	 */
	public void setText(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Answer#isCorrect()
	 */
	public boolean isCorrect() {
		return correct;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wyq.entity.Answer#setCorrect(boolean)
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
