package dewafer.wyq.games.sudu;

import java.util.Collections;
import java.util.List;

public abstract class CellsContainer extends IdObject {

	protected List<Cell> cells;

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return cells.toString();
	}

	public boolean containsSameCell() {
		for (Cell cell : cells) {
			int times = Collections.frequency(cells, cell);
			if (times > 1) {
				return true;
			}
		}
		return false;
	}
	
}
