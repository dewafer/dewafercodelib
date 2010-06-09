package util.test;

import java.io.IOException;

import util.CharacterCountOld;
import util.TxtFileReader;

public class CharacterCountOldTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		println("start read file...");
		CharacterCountOld cc = CharacterCountOld.count(TxtFileReader
				.readAll("/home/dewafer/test.txt"));
		println("read file done. start calc...");
		cc.getCharCountSortedSet();
		cc.getCountCharSortedSet();
		println("calc done.");

		println("cc.getMap().entrySet().size():");
		println(cc.getMap().entrySet().size());
		println("cc.getMap().entrySet():");
		println(cc.getMap().entrySet());

		println("cc.getCharCountSortedSet().size():");
		println(cc.getCharCountSortedSet().size());
		println("cc.getCharCountSortedSet():");
		println(cc.getCharCountSortedSet());

		println("cc.getCountCharSortedSet().size():");
		println(cc.getCountCharSortedSet().size());
		println("cc.getCountCharSortedSet():");
		println(cc.getCountCharSortedSet());

	}

	public static void println(Object o) {
		System.out.println(o);
	}
}
