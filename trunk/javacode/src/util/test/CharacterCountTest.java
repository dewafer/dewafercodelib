package util.test;

import java.io.IOException;

import util.CharacterCount;
import util.TxtFileReader;

public class CharacterCountTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		println("start read file...");
		String tmp = TxtFileReader.readAll("/home/dewafer/test.txt");
		CharacterCount cc = CharacterCount.count(tmp);
		println("read file done. start calc...");

		// println("cc.getCharCountMap():");
		// println(cc.getCharCountMap());

		println("cc.getCountCharMap():");
		println(cc.getCountCharMap());

		println("total:");
		println(cc.getSize());

		// println("cc.getCharCountSortedByCountList()");
		// println(cc.getCharCountSortedByCountList());
	}

	public static void println(Object o) {
		System.out.println(o);
	}
}
