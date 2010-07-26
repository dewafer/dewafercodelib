/**
 * 
 */
package dewafer.wyq.games.sudu;

import java.util.List;

/**
 * @author dewafer
 * 
 */
public class Cell extends IdObject {

	private NumberEnum value;
	private boolean isEmpty = true;
	private Row containingRow;
	private Column containingColumn;
	private List<NumberEnum> possibleValues;

	public NumberEnum getValue() {
		return value;
	}

	public void setValue(NumberEnum value) {
		this.value = value;
	}

}
