package util.mousePositionUtil;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JPanel implements MouseMoveListener {

    /**
     * 
     */
    private static final long serialVersionUID = 4989644345412478241L;

    private JLabel            label_xy         = new JLabel();

    private JLabel            label_pre_xy     = new JLabel();

    private JLabel            label_speed      = new JLabel();

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        GUI gui = new GUI();
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //                frame.pack();
        frame.setSize(450, 50);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        new MousePositionWatcher(gui);
    }

    public GUI() {
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout());
        this.add(label_xy);
        this.add(label_pre_xy);
        this.add(label_speed);
    }

    @Override
    public void mouseMoved(MouseMoveEvent pE) {
        // TODO Auto-generated method stub
        label_xy.setText("X:" + pE.getX() + ", Y:" + pE.getY());
        label_pre_xy.setText("pre_X:" + pE.getPre_x() + ", pre_Y:" + pE.getPre_y());
        label_speed.setText("speed: " + calcSpeed(pE.getPre_x(), pE.getPre_y(), pE.getX(), pE.getY()) + " pix/s");

    }

    public static String calcSpeed(int pre_x, int pre_y, int x, int y) {
        int delt_x = pre_x - x;
        int delt_y = pre_y - y;
        double delt_l = Math.sqrt(delt_x * delt_x + delt_y * delt_y);
        return String.valueOf(Math.round(delt_l * 1000 / MousePositionWatcher.SLEEPTIME));
    }
}
