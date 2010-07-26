package dewafer.wyq.games.sudu;

import java.util.List;

public abstract class CellsContainer extends IdObject {

	protected List<Cell> cells;

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
