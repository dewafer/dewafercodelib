package dewafer.backword.core;

public class PaperFactory {

	private PaperFactory(){}
	
	public static Paper getPaper(){
		Paper p = new Paper();
//		for(int i=0;i<100;i++){
//			Quiz q =new Quiz(p);
//			q.setCorrentAnswerNumber(correntAnswerNumber)
//		}
		return p;
	}
}
