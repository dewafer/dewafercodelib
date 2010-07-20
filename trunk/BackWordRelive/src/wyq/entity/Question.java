package wyq.entity;

import java.util.List;


public interface Question {

	public abstract String getText();

	public abstract void setText(String description);

	public abstract List<Answer> getAnswers();

	public abstract void setAnswers(List<Answer> answers);

}