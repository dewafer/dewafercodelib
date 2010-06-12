package dewafer.backword.core.demo;

import java.util.Scanner;

import dewafer.backword.core.DictionaryReaderImpl;
import dewafer.backword.core.Paper;
import dewafer.backword.core.PaperFactory;
import dewafer.backword.core.Quiz;

public class DemoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pntmemuseage();
		PaperFactory.initialize(new DictionaryReaderImpl(
				DictionaryReaderImpl.class.getResource("/demodict.csv")
						.getFile()));
		Paper p = PaperFactory.getPaper();
		pntmemuseage();
		for (Quiz q : p) {
			pntmemuseage();
			print("quiz:" + q.getQuestion());
			String[] ansList = q.getAnswersList();
			print("1." + ansList[0]);
			print("2." + ansList[1]);
			print("3." + ansList[2]);
			print("4." + ansList[3]);
			print("5.abort");
			print("what's your choice?");
			int in = input();
			if (in == 5) {
				q.abandon();
			} else {
				q.answer(in - 1);
			}
			print("correct?" + q.isCorrect());
			print("abandon?" + q.isAbandoned());
			pntmemuseage();
		}
		pntmemuseage();
		input();
	}

	private static void print(Object o) {
		System.out.println(o);
	}

	private static int input() {
		Scanner in = new Scanner(System.in);
		return in.nextInt();

	}

	private static void pntmemuseage() {
		long use = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		print(toReadable(use));
		// print(use);
	}

	private static String toReadable(long n) {
		double kbyte = (double) n / 1024;
		double mbyte = kbyte / 1024;
		if (mbyte > 1)
			return mbyte + "m";
		else if (kbyte > 1)
			return kbyte + "k";
		else
			return n + "b";
	}

}
