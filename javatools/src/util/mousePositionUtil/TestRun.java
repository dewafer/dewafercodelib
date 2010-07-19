package util.mousePositionUtil;

public class TestRun {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MousePositionWatcher watcher = new MousePositionWatcher();
        watcher.setMouseMoveListener(new MouseMoveListener() {

            @Override
            public void mouseMoved(MouseMoveEvent pE) {
                println("mouseMoved!");
                println("pre_x:" + pE.getPre_x() + ", pre_y:" + pE.getPre_y());
                println("x:" + pE.getX() + ", y:" + pE.getY());
                println("speed:" + calcSpeed(pE.getPre_x(), pE.getPre_y(), pE.getX(), pE.getY()) + "pix/s");
                println();
            }

        });

    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void println() {
        System.out.println();
    }

    public static String calcSpeed(int pre_x, int pre_y, int x, int y) {
        int delt_x = pre_x - x;
        int delt_y = pre_y - y;
        double delt_l = Math.sqrt(delt_x * delt_x + delt_y * delt_y);
        return String.valueOf(delt_l * 1000 / MousePositionWatcher.SLEEPTIME);
    }
}
