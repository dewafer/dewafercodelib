package dewafer.wyq.games.sudu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheGame extends EventDispatcher {

	private List<Cell> cells;
	private List<Row> rows;
	private List<Column> columns;
	private List<SquareGroup> squareGroups;

	public static final String EVENT_GAME_INITIALIZED = "EVENT_GAME_INITIALIZED";

	public TheGame() {
		super();
		init();
	}

	public void init() {
		cells = new ArrayList<Cell>(81);
		rows = new ArrayList<Row>(9);
		columns = new ArrayList<Column>(9);
		squareGroups = new ArrayList<SquareGroup>(9);

		Row row;
		Column col;
		SquareGroup sqGrp;

		// initialize
		for (int i = 0; i < 81; i++) {

			// initialize cell
			Cell c = new Cell(NumberEnum.EMPTY);
			List<NumberEnum> possibleValues = new ArrayList<NumberEnum>();
			for (NumberEnum ne : NumberEnum.values()) {
				if (ne != NumberEnum.EMPTY)
					possibleValues.add(ne);
			}
			c.setPossibleValues(possibleValues);

			// initialize rows, columns and squareGroups
			if (i < 9) {
				row = new Row();
				row.setId(i);
				col = new Column();
				col.setId(i);
				sqGrp = new SquareGroup();
				sqGrp.setId(i);
				rows.add(row);
				columns.add(col);
				squareGroups.add(sqGrp);
			}

			// set row
			row = rows.get(i / 9);
			List<Cell> theCells = row.getCells();
			if (theCells == null) {
				theCells = new ArrayList<Cell>();
				row.setCells(theCells);
			}
			theCells.add(c);
			c.setContainingRow(row);

			// set column
			col = columns.get(i % 9);
			theCells = col.getCells();
			if (theCells == null) {
				theCells = new ArrayList<Cell>();
				col.setCells(theCells);
			}
			theCells.add(c);
			c.setContainingColumn(col);

			// set square group
			// (i ÷ 3) % 3 + i ÷ 27 × 3
			int pos = (i / 3) % 3 + i / 27 * 3;
			sqGrp = squareGroups.get(pos);
			theCells = sqGrp.getCells();
			if (theCells == null) {
				theCells = new ArrayList<Cell>();
				sqGrp.setCells(theCells);
			}
			theCells.add(c);
			c.setContainingSquareGroup(sqGrp);

			// add cell to cells
			cells.add(c);
		}
		// initialize finished
		this.dispatchEvent(EVENT_GAME_INITIALIZED, this, null);
	}
	
	public void validate() throws InvalidatedException{
		// TODO implements this
		throw new InvalidatedException();
	}
	
	private void validateRows() throws InvalidatedException{
		for(Row row : rows){
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (rows != null) {
			for (Row r : rows) {
				sb.append(r);
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
}
