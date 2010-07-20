package wyq.entity.impl;

import java.util.List;

import wyq.entity.Paper;
import wyq.entity.Question;

public class PaperEntityImpl implements Paper {

	public List<Question> questions;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
