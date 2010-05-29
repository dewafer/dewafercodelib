/**
 * 
 */
package util.mouseMotionWatcher;

/**
 * @author dewafer
 * 
 */
public abstract class MouseMotionEvent {

	private int _x;
	private int _y;
	private int pre_x;
	private int pre_y;

	/**
	 * @return the _x
	 */
	public int get_x() {
		return _x;
	}

	/**
	 * @param x
	 *            the _x to set
	 */
	public void set_x(int x) {
		_x = x;
	}

	/**
	 * @return the _y
	 */
	public int get_y() {
		return _y;
	}

	/**
	 * @param y
	 *            the _y to set
	 */
	public void set_y(int y) {
		_y = y;
	}

	/**
	 * @return the pre_x
	 */
	public int getPre_x() {
		return pre_x;
	}

	/**
	 * @param preX
	 *            the pre_x to set
	 */
	public void setPre_x(int preX) {
		pre_x = preX;
	}

	/**
	 * @return the pre_y
	 */
	public int getPre_y() {
		return pre_y;
	}

	/**
	 * @param preY
	 *            the pre_y to set
	 */
	public void setPre_y(int preY) {
		pre_y = preY;
	}

}
