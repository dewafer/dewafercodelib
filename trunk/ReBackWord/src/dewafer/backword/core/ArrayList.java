package dewafer.backword.core;

import java.util.Iterator;

public class ArrayList<E>  implements List<E> {

	private java.util.List<E> list = new java.util.ArrayList<E>();
	/**
	 * extends java.util.ArrayList<E>
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(E arg0) {
		return list.add(arg0);
	}


	@Override
	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	@Override
	public E get(int arg0) {
		return list.get(arg0);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public E[] toArray(E[] arg0) {
		return list.toArray(arg0);
	}

}
