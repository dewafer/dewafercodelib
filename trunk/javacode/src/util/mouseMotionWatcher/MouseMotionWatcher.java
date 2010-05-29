/**
 * 
 */
package util.mouseMotionWatcher;

import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author dewafer
 * 
 */
public class MouseMotionWatcher {

	private static final int WATCHDOG_SLEEPTIME = 125;

	private List<MouseMoveListener> moveListeners = new ArrayList<MouseMoveListener>();

	private int _x = 0;
	private int _y = 0;

	private int pre_x = 0;
	private int pre_y = 0;

	private boolean stopFlg = false;

	private WatchDog watchDog;

	private void init() {
		if (watchDog == null || !watchDog.isAlive()) {
			watchDog = new WatchDog();
		}
		watchDog.setDaemon(true);
		watchDog.start();
	}

	public MouseMotionWatcher() {
		init();
	}

	public MouseMotionWatcher(MouseMoveListener ml) {
		this.moveListeners.add(ml);
		init();
	}

	public void addMouseMoveListener(MouseMoveListener l) {
		moveListeners.add(l);
	}

	public void addMouseMoveListener(MouseMoveListener[] ls) {
		Collections.addAll(moveListeners, ls);
	}

	public void addMouseMoveListener(Collection<? extends MouseMoveListener> ls) {
		moveListeners.addAll(ls);
	}

	public void start() {
		init();
	}

	public final void stop() {
		stopFlg = true;
	}

	private class WatchDog extends Thread {
		@Override
		public void run() {
			while (!stopFlg) {
				int tmpx = MouseInfo.getPointerInfo().getLocation().x;
				int tmpy = MouseInfo.getPointerInfo().getLocation().y;
				if (tmpx != _x || tmpy != _y) {
					pre_x = _x;
					pre_y = _y;
					_x = tmpx;
					_y = tmpy;
					if (moveListeners.size() != 0) {
						MouseMoveEvent e = new MouseMoveEvent();
						e.set_x(_x);
						e.set_y(_y);
						e.setPre_x(pre_x);
						e.setPre_y(pre_y);
						for (MouseMoveListener l : moveListeners) {
							l.mouseMove(e);
						}
					}

				}
				try {
					Thread.sleep(WATCHDOG_SLEEPTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopFlg = false;
		}
	}

}
