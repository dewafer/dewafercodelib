package dewafer.wyq.games.sudu.test;

import dewafer.wyq.games.sudu.EventHandler;
import dewafer.wyq.games.sudu.TheGame;

public class TheGameTest implements EventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TheGame game = new TheGame();
		game
				.addEventListener(TheGame.EVENT_GAME_INITIALIZED,
						new TheGameTest());
		game.init();
		println(game);
	}

	@Override
	public void eventHandler(Object e, Object[] args) {
		println("game initialize...");
		println("e:" + e);
		println("args:" + args);
	}

	private static void println(Object o) {
		System.out.println(o);
	}

}
