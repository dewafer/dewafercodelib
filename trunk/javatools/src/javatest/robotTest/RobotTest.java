package javatest.robotTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class RobotTest extends Robot {

    private static final int DEFAULT_DELAY = 75;

    public RobotTest() throws AWTException {
        super();
        // TODO Auto-generated constructor stub
    }

    private static RobotTest robot;

    static {
        try {
            robot = new RobotTest();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        robot.setAutoDelay(1000);
        robot.mouseMove(1233, 9);
        robot.leftClick();
        robot.mouseMove(398, 14);
        robot.leftClick();

        robot.mouseMove(924, 425);
        robot.leftDoubleClick();

        robot.mouseMove(1268, 8);
        robot.leftClick();

        robot.mouseMove(919, 584);
        robot.leftClick();

        robot.keyStrokeControlC();
        robot.keyStrokeControlV();

        robot.keyStrokeWinR();

        robot.keyStroke("notepad");
        robot.keyStroke(KeyEvent.VK_ENTER);

        robot.keyStroke("fuck");
        robot.keyStroke(KeyEvent.VK_SPACE);
        robot.keyStroke("you");
        robot.keyStroke2(new int[]{KeyEvent.VK_SHIFT, KeyEvent.VK_1});

        robot.keyStroke2(new int[]{KeyEvent.VK_CONTROL, KeyEvent.VK_A});

        robot.keyStrokeControlC();
        robot.setAutoDelay(0);
        for (int i = 0; i < 100; i++) {
            robot.keyStrokeControlV();
            robot.keyStroke(KeyEvent.VK_ENTER);
        }

        robot.setAutoDelay(1000);
        robot.keyStrokeWinR();
        robot.keyStroke("cmd");

        robot.keyStrokeEnter();

    }

    public void leftClick() {
        LeftClick(this);
    }

    public void leftDoubleClick() {
        LeftDoubleClick(this);
    }

    public void keyStrokeControlC() {
        keyStroke2(new int[]{KeyEvent.VK_CONTROL, KeyEvent.VK_C});
    }

    public void keyStrokeControlV() {
        keyStroke2(new int[]{KeyEvent.VK_CONTROL, KeyEvent.VK_V});
    }

    public void keyStrokeWinR() {
        keyStroke2(new int[]{KeyEvent.VK_WINDOWS, KeyEvent.VK_R});
    }

    public void keyStrokeEnter() {
        keyStroke(KeyEvent.VK_ENTER);
    }

    public void keyStroke(int keyCode) {
        int tmp = robot.getAutoDelay();
        keyStrokeNoDelay(keyCode);
        this.delay(tmp);
    }

    private void keyStrokeNoDelay(int keyCode) {
        int tmp = robot.getAutoDelay();
        this.setAutoDelay(DEFAULT_DELAY);
        this.keyPress(keyCode);
        this.keyRelease(keyCode);
        this.setAutoDelay(tmp);
    }

    public void keyStroke(int[] keyCodes) {
        int tmp = robot.getAutoDelay();
        this.setAutoDelay(DEFAULT_DELAY);
        for (int keyCode : keyCodes) {
            this.keyPress(keyCode);
            this.keyRelease(keyCode);
        }
        this.setAutoDelay(tmp);
        this.delay(tmp);
    }

    public void keyStroke(String text) {
        text = text.trim();
        for (char c : text.toCharArray()) {
            Class<?> keyEventType = KeyEvent.class;
            Field keyField = null;
            try {
                keyField = keyEventType.getField("VK_" + Character.toUpperCase(c));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            int keyCode = 0;
            try {
                keyCode = (Integer)keyField.get(null);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            keyStrokeNoDelay(keyCode);
        }
    }

    public void keyStroke2(int[] keyCodes) {
        int tmp = robot.getAutoDelay();
        this.setAutoDelay(DEFAULT_DELAY);
        for (int i = 0; i < keyCodes.length; i++) {
            this.keyPress(keyCodes[i]);
        }
        for (int i = keyCodes.length - 1; i >= 0; i--) {
            this.keyRelease(keyCodes[i]);
        }

        this.setAutoDelay(tmp);
        this.delay(tmp);
    }

    private static void LeftClick(Robot robot) {
        int tmp = robot.getAutoDelay();
        robot.setAutoDelay(DEFAULT_DELAY);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.setAutoDelay(tmp);
    }

    private static void LeftDoubleClick(Robot robot) {
        int tmp = robot.getAutoDelay();
        robot.setAutoDelay(DEFAULT_DELAY);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.setAutoDelay(tmp);
    }

    //    /**
    //     * @param args
    //     */
    //    public static void main(String[] args) {
    //        // TODO Auto-generated method stub
    //        PointerInfo pointer = MouseInfo.getPointerInfo();
    //        Point point = pointer.getLocation();
    //        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
    //        GraphicsDevice device = genv.getDefaultScreenDevice();
    //        Window fullwin = new Window(null);
    //        Panel panel = new Panel();
    //        fullwin.add(panel);
    //        panel.setBackground(new Color(12, 13, 14));
    //        fullwin.addMouseListener(new MouseListener() {
    //
    //            @Override
    //            public void mouseClicked(MouseEvent pE) {
    //                // TODO Auto-generated method stub
    //                println(pE);
    //            }
    //
    //            @Override
    //            public void mouseEntered(MouseEvent pE) {
    //                // TODO Auto-generated method stub
    //                println(pE);
    //            }
    //
    //            @Override
    //            public void mouseExited(MouseEvent pE) {
    //                // TODO Auto-generated method stub
    //                println(pE);
    //            }
    //
    //            @Override
    //            public void mousePressed(MouseEvent pE) {
    //                // TODO Auto-generated method stub
    //                println(pE);
    //            }
    //
    //            @Override
    //            public void mouseReleased(MouseEvent pE) {
    //                // TODO Auto-generated method stub
    //                println(pE);
    //            }
    //        });
    //        device.setFullScreenWindow(fullwin);
    //    }

    //    private static void println(Object o) {
    //        System.out.println(new Date() + ":" + o);
    //    }

}
