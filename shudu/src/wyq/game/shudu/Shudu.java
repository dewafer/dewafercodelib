package wyq.game.shudu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import wyq.appengine.component.file.TextFile;

public class Shudu {

	private static List<int[][]> gameStack = new LinkedList<int[][]>();
	private static List<Set<Possible>> possibleStack = new LinkedList<Set<Possible>>();
	private static final int MAX_TRY = 50;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[][] arg = new int[9][9];
		String[] lines = new TextFile(Shudu.class, "1.txt").readAll().split(
				TextFile.LINE_SEP);
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				arg[x][y] = Integer.parseInt(lines[y].split(",")[x].trim());
			}
		}

		Game g = new Game(arg);
		println(g);
		solve(g);
		// println(g);
	}

	public static int[][] peek() {
		return gameStack.get(gameStack.size() - 1);
	}

	public static int[][] pop() {
		int[][] r = gameStack.remove(gameStack.size() - 1);
		return r;
	}

	public static void solve(Game g) {
		gameStack.add(g.toArray());
		boolean solved = g.isSolved();
		int tryCount = 0;
		Set<Possible> triedPossible = new HashSet<Possible>();
		possibleStack.add(triedPossible);
		while (!solved && tryCount < MAX_TRY) {
			tryCount++;
			if (gameStack.isEmpty())
				break;
			Game theGame = new Game(peek());
			triedPossible = possibleStack.get(possibleStack.size() - 1);
			theGame.calc();
			Possible[] ps = theGame.getPossibilities();
			if (ps.length == 0 || triedPossible.containsAll(Arrays.asList(ps))) {
				pop();
				possibleStack.remove(possibleStack.size() - 1);
				if (gameStack.isEmpty()) {
					break;
				}
				continue;
			}
			Iterator<Possible> itr = Arrays.asList(ps).iterator();
			while (itr.hasNext()) {
				Possible p = itr.next();
				if (triedPossible.contains(p))
					continue;
				theGame.get(p.x, p.y).value = p.value;
				theGame.allPossibleNum[p.value - 1] -= 1;
				theGame.calc();
				println(tryCount + ":" + p);
				triedPossible.add(p);
				boolean valid = theGame.isValid();
				println(valid);
				println(triedPossible);
				println(theGame);
				if (valid) {
					solved = theGame.isSolved();
					if (!solved) {
						possibleStack.add(new HashSet<Possible>());
						gameStack.add(theGame.toArray());
					}
					break;
				} else {
					if (itr.hasNext()) {
						theGame = new Game(peek());
					} else {
						possibleStack.remove(possibleStack.size() - 1);
						pop();
					}
				}
			}
		}
	}

	public static void println(Object o) {
		System.out.println(o);
	}
}
