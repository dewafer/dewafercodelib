package wyq.entity;

public interface Answer {

	public abstract String getText();

	public abstract void setText(String text);

	public abstract boolean isCorrect();

	public abstract void setCorrect(boolean correct);

}