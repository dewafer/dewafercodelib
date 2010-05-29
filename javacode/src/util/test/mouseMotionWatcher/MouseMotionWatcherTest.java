package util.test.mouseMotionWatcher;

import util.mouseMotionWatcher.MouseMotionWatcher;
import util.mouseMotionWatcher.MouseMoveEvent;
import util.mouseMotionWatcher.MouseMoveListener;

public class MouseMotionWatcherTest {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		MouseMotionWatcher w = new MouseMotionWatcher();
		w.addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseMoveEvent e) {
				// TODO Auto-generated method stub
				println(e);
			}

		});
		Thread.sleep(5000);
		w.stop();
		Thread.sleep(5000);
		w.start();
		Thread.sleep(5000);
	}

	private static void println(Object o) {
		System.out.println(o);
	}

}
