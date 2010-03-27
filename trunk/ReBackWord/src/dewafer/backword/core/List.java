package dewafer.backword.core;

import java.util.Iterator;

public interface List<E> {

	boolean add(E arg0);

	boolean contains(Object arg0);

	E get(int arg0);

	Iterator<E> iterator();

	boolean remove(Object arg0);

	int size();

	E[] toArray(E[] arg0);

}
