package dewafer.backword.core;

import java.util.Random;

public class PaperFactory {

	private PaperFactory() {
	}

	private static DictionaryReader reader;
	private static Random rand;
	private static final int ANSWER_NUMBER = 4;
	private static final int TIMEOUT = 10000;

	private static void destory() {
		reader = null;
		rand = null;
	}

	public static Paper getPaper() {
		return getPaper(reader);
	}

	public static Paper getPaper(DictionaryReader preader) {
		initialize(preader);
		if (reader == null)
			return null;
		if (!reader.open())
			return null;
		int max = reader.count();
		if (max < ANSWER_NUMBER)
			return null;
		Paper paper = new Paper();
		List<Quiz> qs = new ArrayList<Quiz>();
		for (int i = 0; i < max; i++) {
			Quiz q = new Quiz();
			String[] ansList = new String[ANSWER_NUMBER];

			int crtNum = getRandInt(null, ANSWER_NUMBER);

			DictionaryWordEntity word = reader.readWordAt(i);
			ansList[crtNum] = word.getExplain();
			q.setQuestion(word.getWord());

			int[] already = new int[ANSWER_NUMBER];
			for (int k = 0; k < ANSWER_NUMBER; k++) {
				if (k == crtNum)
					already[k] = i;
				else
					already[k] = -1;
			}

			for (int j = 0; j < ANSWER_NUMBER; j++) {
				if (j == crtNum)
					continue;
				already[j] = getRandInt(already, max);
				ansList[j] = reader.readWordAt(already[j]).getExplain();
			}
			q.setAnswersList(ansList);
			q.setCorrentAnswerNumber(crtNum);
			q.setOvertime(TIMEOUT);
			q.setTimeoutEvent(null);
			q.setPaper(paper);
			qs.add(q);
		}
		DictionaryInfoEntity dictInfo = reader.getDicInfo();
		paper.setAuthor(dictInfo.getAuthor());
		paper.setDescription(dictInfo.getDescription());
		paper.setName(dictInfo.getName());
		paper.setUnfinishedQuizList(qs);
		paper.setFinishedQuizList(new ArrayList<Quiz>());
		paper.setFinishedWrongQuizList(new ArrayList<Quiz>());
		reader.close();
		destory();
		return paper;
	}

	private static int getRandInt(int[] not, int under) {
		if (rand == null)
			rand = new Random();
		int tmp;
		if (not == null)
			not = new int[0];
		do {
			tmp = rand.nextInt(under);
		} while (contains(not, tmp));
		return tmp;
	}

	private static boolean contains(int[] numbs, int numb) {
		for (int i = 0; i < numbs.length; i++) {
			if (numbs[i] == numb)
				return true;
		}
		return false;
	}

	public static void initialize(DictionaryReader preader) {
		reader = preader;
	}

	/*
	 * test use method private static String[] getAns(int count,int pos,String
	 * c) { List<String> ans = new ArrayList<String>(); for(int i=0;i<count;i++)
	 * { if(i==pos) { ans.add(c); continue; } ans.add(getRandWord()); } return
	 * ans.toArray(new String[0]); }
	 * 
	 * private static String[] rndWordsList = {
	 * "a","b","c","d","e","f","g","h","i","j","k","l","m","n",
	 * "o","p","q","r","s","t","u","v","w","x","y","z",
	 * "apple","abstract","abandon","abort","arrange", "ah","air","asize" };
	 * private static String getRandWord() { return
	 * rndWordsList[rand.nextInt(rndWordsList.length)]; }
	 */

}
