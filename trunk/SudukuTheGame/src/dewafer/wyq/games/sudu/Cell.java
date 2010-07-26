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

	public Cell(NumberEnum empty) {
		value = empty;
	}

	public NumberEnum getValue() {
		return value;
	}

	public void setValue(NumberEnum value) {
		this.value = value;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Row getContainingRow() {
		return containingRow;
	}

	public void setContainingRow(Row containingRow) {
		this.containingRow = containingRow;
	}

	public Column getContainingColumn() {
		return containingColumn;
	}

	public void setContainingColumn(Column containingColumn) {
		this.containingColumn = containingColumn;
	}

	public List<NumberEnum> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<NumberEnum> possibleValues) {
		this.possibleValues = possibleValues;
	}
	
}
