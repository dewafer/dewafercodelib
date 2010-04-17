package wyq.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("test\r\n");
		JFrame frame = new JFrame("test abc\r\ndev");
		frame.setVisible(true);
		frame.setSize(300, 500);
		JButton but = new JButton("abc\r\ndec");
		JPanel pan = new JPanel();
		pan.add(but);
		frame.add(pan);
		but.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.print("test2\r\n");
			}
		});
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.print("keyTyped:" + e.getKeyChar() + "\r\n");
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.print("keyReleased:" + e.getKeyChar() + "\r\n");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.print("keyPressed:" + e.getKeyChar() + "\r\n");
			}
		});

	}

}
