package dewafer.backword.core;


import java.util.Random;

public class PaperFactory {

	private PaperFactory(){}
	
	private static DictionaryReader reader;
	private static Random rand;
	private static final int ANSWER_NUMBER = 4;
	private static final int TIMEOUT = 10000;
	
	private static void destory()
	{
		reader = null;
		rand = null;
	}
	
	public static Paper getPaper(){
		if(reader == null)
			return null;
		reader.open();
		Paper paper = new Paper();
		List<Quiz> qs = new ArrayList<Quiz>();
		int max = reader.count();
		for(int i=0;i<max;i++)
		{
			Quiz q = new Quiz();
			String[] ansList = new String[ANSWER_NUMBER];
			int crtNum = getRandInt(-1,ANSWER_NUMBER);
			String[] tmp = reader.read();
			ansList[crtNum] = tmp[DictionaryReader.EXPLAIN];
			for(int j=0;j<ANSWER_NUMBER;j++)
			{
				if(j==crtNum)
					continue;
				ansList[j] = reader.read(getRandInt(i,max))[DictionaryReader.EXPLAIN];
			}
			q.setAnswersList(ansList);
			q.setCorrentAnswerNumber(crtNum);
			q.setOvertime(TIMEOUT);
			q.setTimeoutEvent(null);
			q.setPaper(paper);
			qs.add(q);
		}
		String[] dictInfo = reader.getDicInfo();
		paper.setAuthor(dictInfo[DictionaryReader.DICT_INFO_AUTHOR]);
		paper.setDescription(dictInfo[DictionaryReader.DICT_INFO_DESC]);
		paper.setName(dictInfo[DictionaryReader.DICT_INFO_NAME]);
		paper.setUnfinishedQuizList(qs);
		paper.setFinishedQuizList(new ArrayList<Quiz>());
		paper.setFinishedWrongQuizList(new ArrayList<Quiz>());
		destory();
		return paper;
	}
	
	private static int getRandInt(int not,int under)
	{
		if(rand==null)
			rand = new Random();
		int tmp = not;
		do{
			tmp = rand.nextInt(under);
		}while(tmp==not);
		return tmp;
		
	}
	
	public static int initialize(DictionaryReader preader)
	{
		reader = preader;
		// TODO init here
		return -1;
	}
	
/*	test use method
   private static String[] getAns(int count,int pos,String c)
	{
		List<String> ans = new ArrayList<String>();
		for(int i=0;i<count;i++)
		{
			if(i==pos)
			{
				ans.add(c);
				continue;
			}
			ans.add(getRandWord());
		}
		return ans.toArray(new String[0]);
	}
	
	private static String[] rndWordsList = 
	{ "a","b","c","d","e","f","g","h","i","j","k","l","m","n",
			"o","p","q","r","s","t","u","v","w","x","y","z",
			"apple","abstract","abandon","abort","arrange",
			"ah","air","asize"
	};
	private static String getRandWord()
	{
		return rndWordsList[rand.nextInt(rndWordsList.length)];
	}*/

}
