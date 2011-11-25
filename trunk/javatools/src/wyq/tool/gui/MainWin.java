package wyq.tool.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import wyq.infrastructure.SmartExtClasspathLoader;
import wyq.tool.logic.LogicBean;
import wyq.tool.util.Processor;

public class MainWin implements Processor {

    private JFrame frame;
    private static String[] startArgs;
    private JLabel lblNewLabel_1;
    private JTextPane textPane;
    private JTextPane textPane_1;

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
	frame.setBounds(100, 100, 492, 340);
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

	JButton btnLoadJar = new JButton("load jar");
	btnLoadJar.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser(textPane.getText());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = chooser.getSelectedFile();
		    textPane.setText(selectedFile.getAbsolutePath());
		    String classpath = textPane.getText();
		    List<String> nameList = new ArrayList<String>();
		    SmartExtClasspathLoader.loadClasspath(classpath, nameList);
		    StringBuilder sb = new StringBuilder();
		    for (String clz : nameList) {
			sb.append(clz);
			sb.append(System.getProperty("line.separator"));
		    }
		    textPane_1.setText(sb.toString());
		}
	    }
	});

	textPane = new JTextPane();

	JScrollPane scrollPane = new JScrollPane();
	GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
	groupLayout
		.setHorizontalGroup(groupLayout
			.createParallelGroup(Alignment.TRAILING)
			.addGroup(
				Alignment.LEADING,
				groupLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						groupLayout
							.createParallelGroup(
								Alignment.LEADING)
							.addComponent(
								scrollPane,
								GroupLayout.DEFAULT_SIZE,
								422,
								Short.MAX_VALUE)
							.addComponent(
								lblNewLabel_1)
							.addComponent(
								lblNewLabel)
							.addGroup(
								groupLayout
									.createSequentialGroup()
									.addComponent(
										textPane,
										GroupLayout.DEFAULT_SIZE,
										260,
										Short.MAX_VALUE)
									.addPreferredGap(
										ComponentPlacement.RELATED)
									.addComponent(
										btnLoadJar)
									.addPreferredGap(
										ComponentPlacement.RELATED)
									.addComponent(
										btnLoadProp)))
					.addContainerGap()));
	groupLayout
		.setVerticalGroup(groupLayout
			.createParallelGroup(Alignment.TRAILING)
			.addGroup(
				groupLayout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(
						ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(18)
					.addComponent(scrollPane,
						GroupLayout.DEFAULT_SIZE, 168,
						Short.MAX_VALUE)
					.addPreferredGap(
						ComponentPlacement.UNRELATED)
					.addGroup(
						groupLayout
							.createParallelGroup(
								Alignment.TRAILING)
							.addGroup(
								groupLayout
									.createParallelGroup(
										Alignment.BASELINE)
									.addComponent(
										btnLoadProp)
									.addComponent(
										btnLoadJar))
							.addComponent(
								textPane,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addContainerGap()));

	textPane_1 = new JTextPane();
	scrollPane.setViewportView(textPane_1);
	frame.getContentPane().setLayout(groupLayout);
    }

    @Override
    public void process(String[] args) throws Exception {
	startArgs = args;
	EventQueue.invokeAndWait(new Runnable() {
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

}
