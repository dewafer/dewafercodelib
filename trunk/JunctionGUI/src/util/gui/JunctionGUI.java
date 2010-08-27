package util.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

public class JunctionGUI extends JPanel implements ActionListener, Runnable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JFileChooser      fileChooser      = new JFileChooser();

    private JButton           button1          = new JButton();

    private JButton           button2          = new JButton();

    private JButton           button3          = new JButton();

    private JButton           button4          = new JButton();

    private JLabel            out              = new JLabel();

    private JRadioButton      makeButton       = new JRadioButton();

    private JRadioButton      delButton        = new JRadioButton();

    private ButtonGroup       actionButtonGrp  = new ButtonGroup();

    private String            dir1;

    private String            dir2;

    private static JFrame     main;

    private Process           p;

    private int               exitValue;

    public static void main(String[] args) {
        main = new JFrame();
        main.add(new JunctionGUI());
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main.setResizable(false);
        main.pack();
        main.setVisible(true);
    }

    public JunctionGUI() throws HeadlessException {
        super();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        button1.setText("Choose folder 1...");
        button1.setActionCommand("1");
        button1.addActionListener(this);

        button2.setText("Choose folder 2...");
        button2.setActionCommand("2");
        button2.addActionListener(this);

        button3.setText("Run junction");
        button3.setActionCommand("3");
        button3.addActionListener(this);

        button4.setText("clear");
        button4.setActionCommand("clear");
        button4.addActionListener(this);

        makeButton.setText("make");
        makeButton.setActionCommand("make");
        makeButton.addActionListener(this);

        delButton.setText("del");
        delButton.setActionCommand("del");
        delButton.addActionListener(this);

        actionButtonGrp.add(makeButton);
        actionButtonGrp.add(delButton);
        actionButtonGrp.setSelected(makeButton.getModel(), true);

        setText();

        add(makeButton);
        add(delButton);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(out);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        if (pE.getActionCommand() == "1") {
            int result = fileChooser.showDialog(this, "choose dir 1");
            if (result == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                if (actionButtonGrp.getSelection().getActionCommand() == "make" && f.listFiles() != null
                    && f.listFiles().length > 0) {
                    JOptionPane.showMessageDialog(this, "dir not null!");
                    dir1 = null;
                } else {
                    dir1 = f.getAbsolutePath();
                }
                setText();
                main.pack();
            }
        } else if (pE.getActionCommand() == "2") {
            int result = fileChooser.showDialog(this, "choose dir 2");
            if (result == JFileChooser.APPROVE_OPTION) {
                dir2 = fileChooser.getSelectedFile().getAbsolutePath();
                setText();
                main.pack();
            }
        } else if (pE.getActionCommand() == "3") {
            runCommand();
        } else {
            dir1 = null;
            dir2 = null;
            setText();
            main.pack();
        }
    }

    private void runCommand() {
        try {
            p = Runtime.getRuntime().exec(getCommandString());
            new Thread(this).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
            e.printStackTrace();
        }
    }

    private void setText() {
        out.setText(getCommandString());
    }

    private String getCommandString() {
        String makeCmd = "junction.exe " + "\"" + dir1 + "\" " + "\"" + dir2 + "\"";
        String delCmd = "junction.exe -d \"" + dir1 + "\"";
        if (actionButtonGrp.getSelection().getActionCommand() == "make") {
            return makeCmd;
        } else {
            return delCmd;
        }
    }

    @Override
    public void run() {
        boolean error;
        do {
            error = false;
            try {
                exitValue = p.exitValue();
            } catch (IllegalThreadStateException e) {
                error = true;
            } finally {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(this, e);
                    e.printStackTrace();
                }
            }
        } while (error);
        JOptionPane.showMessageDialog(this, "Done " + exitValue);

    }
}
