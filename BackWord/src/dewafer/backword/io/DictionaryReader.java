package dewafer.backword.io;

import java.util.ArrayList;
import java.util.List;

import dewafer.backword.core.Word;
import dewafer.backword.core.WordsDictionary;

public class DictionaryReader {

	public static WordsDictionary read() {
		// XXX: this is just a demo
		WordsDictionary dict = new WordsDictionary();
		dict.setName("a demo dict");
		dict.setAuthor("by dewafer");
		dict.setDescription("demo");
		dict.setCount(50);
		dict.setOvertime(30000);
		List<Word> wordlist = new ArrayList<Word>();
		for (int i = 0; i < 50; i++) {
			Word w = new Word();
			w.setWord("w" + i);
			w.setExplanation("exp" + i);
			wordlist.add(w);
		}
		dict.setWords(wordlist);
		return dict;
	}
}
