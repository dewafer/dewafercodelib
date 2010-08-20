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
	private SquareGroup containingSquareGroup;
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

	public SquareGroup getContainingSquareGroup() {
		return containingSquareGroup;
	}

	public void setContainingSquareGroup(SquareGroup containingSquareGroup) {
		this.containingSquareGroup = containingSquareGroup;
	}

	public List<NumberEnum> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<NumberEnum> possibleValues) {
		this.possibleValues = possibleValues;
	}

	@Override
	public String toString() {
		return String.valueOf(value.value());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Cell) {
			Cell c = (Cell) o;
			return this.value == c.value;
		} else {
			return super.equals(o);
		}
	}

}
