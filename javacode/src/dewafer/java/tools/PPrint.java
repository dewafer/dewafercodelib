package dewafer.java.tools;

import java.util.Arrays;
import java.util.Collection;

/**
 * @deprecated <i>PPrint</i> have been merged with <code> Print </code>. Use Print instead.
 * @author AIR
 *
 */
public class PPrint {

	public static String pformat(Collection<?> c) {

		if (c.size() == 0)
			return "[]";

		StringBuilder result = new StringBuilder("[");

		for (Object elem : c) {

			if (c.size() != 1)
				result.append("\n  ");

			result.append(elem);

		}

		if (c.size() != 1)
			result.append("\n");

		result.append("]");

		return result.toString();
	}

	public static void pprint(Collection<?> c) {
		System.out.println(pformat(c));
	}

	public static void pprint(Object[] c) {
		System.out.println(pformat(Arrays.asList(c)));
	}
}
