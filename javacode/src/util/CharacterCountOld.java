package util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

/**
 * 字符串中字符计算器。 该类不直接初始化，请使用{@link CharacterCountOld#count(String, Set)}或
 * {@link CharacterCountOld#count(String)}方法。
 * 
 * @author dewafer
 * @version v0.1
 * @since 2010/05/29
 */
public class CharacterCountOld {

	private Map<Character, Integer> data = new HashMap<Character, Integer>();

	private Set<Character> excludeCharsSet;

	private SortedSet<Entry<Character, Integer>> charCountSortedSet;

	private SortedSet<Entry<Integer, Set<Character>>> countCharSortedSet;

	private CharacterCountOld() {
		// no
	}

	/**
	 * 计算字符串中的字符并返回CharacterCount类。
	 * 
	 * @param text
	 *            用来计算的字符串
	 * @param exclude
	 *            不做计算的字符集
	 * @return {@link CharacterCountOld}
	 */
	public static CharacterCountOld count(String text, Set<Character> exclude) {
		CharacterCountOld c = new CharacterCountOld();
		if (exclude == null) {
			exclude = new HashSet<Character>();
		}
		c.excludeCharsSet = exclude;

		for (char key : text.toCharArray()) {
			if (c.excludeCharsSet.contains(key)) {
				continue;
			}
			if (!c.data.containsKey(key)) {
				c.data.put(key, 1);
			} else {
				int value = c.data.get(key) + 1;
				c.data.put(key, value);
			}
		}

		return c;
	}

	/**
	 * 等同于CharacterCount.count(text,null)
	 * 
	 * @param text
	 * @return {@link CharacterCountOld}
	 * @see CharacterCountOld#count(String, Set)
	 */
	public static CharacterCountOld count(String text) {
		return count(text, null);
	}

	/**
	 * 计算后的data。data为一个Map，key为字符，value为该字符在字符串中出现的次数。
	 * 
	 * @return the data {@code Map<Character, Integer>}
	 */
	public Map<Character, Integer> getMap() {
		return data;
	}

	/**
	 * 字符-出现次数排序集合，请传入比较器。 如果比较器为null，则默认为字符出现次数降序。
	 * 
	 * @param comparator
	 *            比较器
	 * @return {@code SortedSet<Entry<Character, Integer>> }
	 */
	public SortedSet<Entry<Character, Integer>> getCharCountSortedSet(
			Comparator<Entry<Character, Integer>> comparator) {
		if (charCountSortedSet == null) {
			charCountSortedSet = calcCharCountSortedSet(comparator);
		}
		return charCountSortedSet;
	}

	/**
	 * 计算字符-出现次数排序集合
	 * 
	 * @param comparator比较器
	 * @return {@code SortedSet<Entry<Character, Integer>> }
	 * @see CharacterCountOld#getCharCountSortedSet(Comparator)
	 */
	private SortedSet<Entry<Character, Integer>> calcCharCountSortedSet(
			Comparator<Entry<Character, Integer>> comparator) {
		if (comparator == null) {
			comparator = new Comparator<Entry<Character, Integer>>() {

				@Override
				public int compare(Entry<Character, Integer> arg0,
						Entry<Character, Integer> arg1) {
					return arg1.getValue().compareTo(arg0.getValue());
				}
			};
		}
		TreeSet<Entry<Character, Integer>> set = new TreeSet<Entry<Character, Integer>>(
				comparator);
		set.addAll(data.entrySet());
		return set;
	}

	/**
	 * 等同于<code>getCharCountSortedSet(null)</code>
	 * 
	 * @see #getCharCountSortedSet(Comparator)
	 * @return {@code SortedSet<Entry<Character, Integer>> }
	 */
	public SortedSet<Entry<Character, Integer>> getCharCountSortedSet() {
		return getCharCountSortedSet(null);
	}

	/**
	 * 出现次数-字符排序集，请传入比较器。如果比较器为null，则默认为字符出现出现次数降序。
	 * 
	 * @param comparator
	 *            比较器
	 * @return {@code SortedSet<Entry<Integer, Set<Character>>> }
	 */
	public SortedSet<Entry<Integer, Set<Character>>> getCountCharSortedSet(
			Comparator<Entry<Integer, Set<Character>>> comparator) {
		if (countCharSortedSet == null) {
			countCharSortedSet = calcCountCharSortedSet(comparator);
		}
		return countCharSortedSet;
	}

	/**
	 * 计算出现次数-字符排序集
	 * 
	 * @param comparator
	 *            比较器
	 * @return {@code Set<Entry<Integer, Set<Character>>> }
	 * @see CharacterCountOld#getCountCharSortedSet(Comparator)
	 */
	private SortedSet<Entry<Integer, Set<Character>>> calcCountCharSortedSet(
			Comparator<Entry<Integer, Set<Character>>> comparator) {
		Map<Integer, Set<Character>> map = new HashMap<Integer, Set<Character>>();
		Set<Entry<Character, Integer>> sets = data.entrySet();
		for (Entry<Character, Integer> entry : sets) {
			Set<Character> chars;
			if (!map.containsKey(entry.getValue())) {
				chars = new HashSet<Character>();
				chars.add(entry.getKey());
				map.put(entry.getValue(), chars);
			} else {
				chars = map.get(entry.getValue());
				chars.add(entry.getKey());
			}
		}
		if (comparator == null) {
			comparator = new Comparator<Entry<Integer, Set<Character>>>() {

				@Override
				public int compare(Entry<Integer, Set<Character>> arg0,
						Entry<Integer, Set<Character>> arg1) {
					Integer arg0_v = arg0.getValue().size();
					Integer arg1_v = arg1.getValue().size();
					return arg1_v.compareTo(arg0_v);
				}
			};
		}
		TreeSet<Entry<Integer, Set<Character>>> set = new TreeSet<Entry<Integer, Set<Character>>>(
				comparator);
		set.addAll(map.entrySet());
		return set;
	}

	/**
	 * 等同于<code>getCountCharSortedSet(null)</code>
	 * 
	 * @see #getCountCharSortedSet(Comparator)
	 * @return {@code SortedSet<Entry<Character, Integer>> }
	 */
	public SortedSet<Entry<Integer, Set<Character>>> getCountCharSortedSet() {
		return getCountCharSortedSet(null);
	}

}
