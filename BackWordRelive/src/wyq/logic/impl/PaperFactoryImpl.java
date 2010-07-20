package wyq.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wyq.entity.Answer;
import wyq.entity.EntityImplGetter;
import wyq.entity.Paper;
import wyq.entity.Question;
import wyq.entity.Word;
import wyq.logic.PaperFactory;

public class PaperFactoryImpl implements PaperFactory {

	private static Random rand = new Random();

	private PaperFactoryImpl() {
	}

	public static Paper createPaper(List<Word> words) {
//		Paper paper = EntityImplGetter.getImpl(Paper.class);
//		List<Question> questions = new ArrayList<Question>();
//		paper.setQuestions(questions);
//		int pre_pointer = 0;
//		int current = 0;
//		for (int i = 0; i < words.size(); i++) {
//			pre_pointer = current;
//			current = next(pre_pointer, words.size());
//			Question q = EntityImplGetter.getImpl(Question.class);
//			Word w = words.get(current);
//			q.setText(w.getKey());
//			Answer ans = 
//		}
		return null;
	}

	private static int next(int notEqu, int max) {
		int tmp = rand.nextInt(max);
		if (notEqu > max) {
			return tmp;
		}
		while (tmp == notEqu) {
			tmp = rand.nextInt(max);
		}
		return tmp;
	}

}
