package wyq.entity;

import java.util.List;

public interface Paper {

	public abstract List<Question> getQuestions();

	public abstract void setQuestions(List<Question> questions);

}
