package wyq.tool.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import wyq.tool.logic.LogicBean;

public class MainWin {

	private JFrame frame;
	private static String[] startArgs;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		startArgs = args;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin window = new MainWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				lblNewLabel_1.setText(startArgs.toString());
			}
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Args:");

		lblNewLabel_1 = new JLabel("New label");

		JButton btnLoadProp = new JButton("load prop");
		btnLoadProp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogicBean b = new LogicBean();
				lblNewLabel_1.setText(b.getLabelText());
			}
		});
		btnLoadProp.setVerticalAlignment(SwingConstants.BOTTOM);
		btnLoadProp.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										groupLayout
												.createParallelGroup(
														Alignment.LEADING)
												.addComponent(lblNewLabel_1)
												.addComponent(lblNewLabel))
								.addContainerGap(386, Short.MAX_VALUE))
				.addGroup(
						Alignment.TRAILING,
						groupLayout.createSequentialGroup()
								.addContainerGap(343, Short.MAX_VALUE)
								.addComponent(btnLoadProp).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel_1)
						.addPreferredGap(ComponentPlacement.RELATED, 187,
								Short.MAX_VALUE).addComponent(btnLoadProp)
						.addContainerGap()));
		frame.getContentPane().setLayout(groupLayout);
	}
}
