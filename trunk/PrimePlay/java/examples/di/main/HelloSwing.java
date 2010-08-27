package examples.di.main;

import javax.swing.*;
import java.awt.*;


public class HelloSwing {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Hello,Swing!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel jp = new JPanel();
		frame.setSize(new Dimension(550, 360));
		int i = 0;
		while (i < 100) {
			if (i < 10){
				JButton jb = new JButton("0" + i + "\n");
				jb.setBounds(new Rectangle(57, 196, 130, 50));
				jb.setSize(new Dimension(100,50));
				jp.add(jb);
			}
			else{
				JButton jb = new JButton(i + "\n");
				jb.setBounds(new Rectangle(57, 196, 130, 50));
				jb.setSize(new Dimension(100,50));
				jp.add(jb);
			}
			i++;
		}
		frame.add(jp);
		frame.setVisible(false);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Ž©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
			e.printStackTrace();
		}
		frame.setVisible(true);
		frame.repaint();
	}
}
