/**
 * 
 */
package dewafer.wyq.games.sudu;

/**
 * 该对象拥有id号，并且可以通过id号进行排序。
 * 对象id号默认为0。
 * @author dewafer
 *
 */
public class IdObject implements Comparable<IdObject> {

	protected int id = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(IdObject o) {
		return id - o.getId();
	}



}
