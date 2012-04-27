package wyq.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import wyq.test.TestBean2;

public class AppWin1 {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWin1 window = new AppWin1();
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
	public AppWin1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		// List<Object> dataList = new ArrayList<Object>();
		// for (int i = 0; i < 5; i++) {
		//
		// Object bean = new Object();
		// dataList.add(bean);
		// }
		List<TestBean2> dataList = new ArrayList<TestBean2>();
		for (int i = 0; i < 5; i++) {

			TestBean2 bean = new TestBean2();
			bean.setValue1("value" + i);
			bean.setValue2(i);
			dataList.add(bean);
		}
		MyTableModel tm = new MyTableModel(dataList);
		table.setModel(tm);
		frame.getContentPane().add(table, BorderLayout.CENTER);
	}
}
