package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 字符串中字符计算器。 该类不直接初始化，请使用{@link CharacterCount#count(String, Set)}或
 * {@link CharacterCount#count(String)}方法。
 * 
 * @author dewafer
 * @version v0.1
 * @since 2010/05/29
 */
public class CharacterCount {

	private Map<Character, Integer> charCountMap;

	private List<Entry<Character, Integer>> charCountSortedByCountList;

	private Map<Integer, List<Character>> countCharMap;

	private Set<Character> excludeCharsSet;

	private int size;

	private CharacterCount() {
		// no
	}

	/**
	 * 计算字符串中的字符并返回CharacterCount类。
	 * 
	 * @param text
	 *            用来计算的字符串
	 * @param exclude
	 *            不做计算的字符集
	 * @return {@link CharacterCount}
	 */
	public static CharacterCount count(String text, Set<Character> exclude,
			Comparator<Character> charCountComparator,
			Comparator<Integer> countCharComparator) {
		CharacterCount c = new CharacterCount();
		if (exclude == null) {
			exclude = new HashSet<Character>();
			for (char lc : System.getProperty("line.separator").toCharArray()) {
				exclude.add(lc);
			}
		}
		c.excludeCharsSet = exclude;

		if (countCharComparator == null) {
			countCharComparator = Collections.reverseOrder();
		}

		c.charCountMap = new TreeMap<Character, Integer>(charCountComparator);
		c.countCharMap = new TreeMap<Integer, List<Character>>(
				countCharComparator);

		char[] tmp = text.toCharArray();
		c.size = tmp.length;
		for (char key : tmp) {
			if (c.excludeCharsSet.contains(key)) {
				continue;
			}

			int lastAppearTimes = 0;
			int appearTimes;
			if (c.charCountMap.containsKey(key)) {
				lastAppearTimes = c.charCountMap.get(key);
			}
			appearTimes = lastAppearTimes + 1;
			c.charCountMap.put(key, appearTimes);

			List<Character> chars;
			if (c.countCharMap.containsKey(lastAppearTimes)) {
				chars = c.countCharMap.get(lastAppearTimes);
				if (chars.contains(key)) {
					chars.remove(chars.indexOf(key));
					if (chars.size() == 0) {
						c.countCharMap.remove(lastAppearTimes);
					}
				}
			}
			if (!c.countCharMap.containsKey(appearTimes)) {
				chars = new ArrayList<Character>();
				chars.add(key);
				c.countCharMap.put(appearTimes, chars);
			} else {
				chars = c.countCharMap.get(appearTimes);
				if (!chars.contains(key)) {
					chars.add(key);
				}
			}

		}

		return c;
	}

	/**
	 * 等同于CharacterCount.count(text,null)
	 * 
	 * @param text
	 * @return {@link CharacterCount}
	 * @see CharacterCount#count(String, Set)
	 */
	public static CharacterCount count(String text) {
		return count(text, null, null, null);
	}

	public Map<Character, Integer> getCharCountMap() {
		return charCountMap;
	}

	public Map<Integer, List<Character>> getCountCharMap() {
		return countCharMap;
	}

	public List<Entry<Character, Integer>> getCharCountSortedByCountList(
			Comparator<Entry<Character, Integer>> comparator) {
		if (charCountSortedByCountList == null) {
			if (comparator == null) {
				comparator = new Comparator<Entry<Character, Integer>>() {
					@Override
					public int compare(Entry<Character, Integer> o1,
							Entry<Character, Integer> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				};
			}
			charCountSortedByCountList = sortSet(charCountMap.entrySet(),
					Collections.reverseOrder(comparator));
		}
		return charCountSortedByCountList;
	}

	public List<Entry<Character, Integer>> getCharCountSortedByCountList() {
		return getCharCountSortedByCountList(null);
	}

	private <T1, T2> List<Entry<T1, T2>> sortSet(Set<Entry<T1, T2>> entrySet,
			Comparator<Entry<T1, T2>> comparator) {
		List<Entry<T1, T2>> list = new ArrayList<Entry<T1, T2>>(entrySet);
		Collections.sort(list, comparator);
		return list;
	}

	public int getSize() {
		return size;
	}
}
