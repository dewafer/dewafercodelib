package dewafer.wyq.games.sudu;

import java.util.ArrayList;
import java.util.List;

public class TheGame extends EventDispatcher {

	private List<Cell> cells;
	private List<Row> rows;
	private List<Column> columns;
	private List<SquareGroup> squareGroups;

	public TheGame() {
		super();
		init();
	}

	private void init() {
		cells = new ArrayList<Cell>(81);
		rows = new ArrayList<Row>(9);
		columns = new ArrayList<Column>(9);
		squareGroups = new ArrayList<SquareGroup>(9);

		Row row;
		Column col;
		SquareGroup sqGrp;

		for (int i = 0; i < 81; i++) {
			Cell c = new Cell(NumberEnum.EMPTY);
			List<NumberEnum> possibleValues = new ArrayList<NumberEnum>();
			for (NumberEnum ne : NumberEnum.values()) {
				if (ne != NumberEnum.EMPTY)
					possibleValues.add(ne);
			}
			c.setPossibleValues(possibleValues);

			if (i < 9) {
				rows.add(new Row());
				columns.add(new Column());
				squareGroups.add(new SquareGroup());
			}

			row = rows.get(i / 9);
			List<Cell> cells = row.getCells();
			if (cells == null) {
				cells = new ArrayList<Cell>();
				row.setCells(cells);
			}
			cells.add(c);
			c.setContainingRow(row);

			col = columns.get(i % 9);
			cells = col.getCells();
			if (cells == null) {
				cells = new ArrayList<Cell>();
				col.setCells(cells);
			}
			cells.add(c);
			c.setContainingColumn(col);
			
			// TODO
		}
	}
}
