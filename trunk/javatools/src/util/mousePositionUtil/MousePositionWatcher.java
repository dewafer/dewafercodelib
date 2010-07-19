package util.mousePositionUtil;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;

public class MousePositionWatcher {

    public static final int     SLEEPTIME = 250;

    private int                 x         = 0;

    private int                 y         = 0;

    private DisplayMode         mode;

    private GraphicsEnvironment env;

    private GraphicsDevice      device;

    private Watchdog            watchdog;

    private MouseMoveListener   moveListener;

    public MousePositionWatcher(MouseMoveListener listener) {
        moveListener = listener;
        init();
    }

    public MousePositionWatcher() {
        init();
    }

    private void init() {
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
        mode = device.getDisplayMode();
        watchdog = new Watchdog();
        watchdog.start();
    }

    public int getScreenWidth() {
        return mode.getWidth();
    }

    public int getScreenHeight() {
        return mode.getHeight();
    }

    public void setMouseMoveListener(MouseMoveListener listener) {
        moveListener = listener;
    }

    public void removeMouseMoveListener() {
        moveListener = null;
    }

    public void start() {
        if (watchdog == null) {
            watchdog = new Watchdog();
        }
        watchdog.start();
    }

    public void stop() {
        if (watchdog != null) {
            watchdog.requestStop = true;
        }
    }

    private class Watchdog extends Thread {
        private int    temp_x;

        private int    temp_y;

        public boolean requestStop = false;

        @Override
        public void run() {
            while (!requestStop) {
                temp_x = MouseInfo.getPointerInfo().getLocation().x;
                temp_y = MouseInfo.getPointerInfo().getLocation().y;
                if (temp_x != x || temp_y != y) {
                    if (moveListener != null) {
                        MouseMoveEvent e = new MouseMoveEvent();
                        e.setPre_x(x);
                        e.setPre_y(y);
                        e.setX(temp_x);
                        e.setY(temp_y);
                        moveListener.mouseMoved(e);
                    }
                }
                x = temp_x;
                y = temp_y;
                try {
                    Thread.sleep(SLEEPTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            requestStop = false;
        }
    }
}
