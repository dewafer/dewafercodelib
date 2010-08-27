package dnd.homework;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

public class DragDropOnJFrame extends JFrame {

    private static final long serialVersionUID = -750709524024728989L;

    private List<File>        l                = new ArrayList<File>();

    private JTextPane         output           = new JTextPane();

    public DragDropOnJFrame() {
        super("DragDropOnJFrame");
        setTransferHandler(handler);

        output.setTransferHandler(handler);
        //        output.setDropMode(DropMode.ON);
        output.setDragEnabled(false);
        output.setEditable(false);
        output.setText("no files now");
        getContentPane().add(output);
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI(args);
            }
        });
    }

    private static void createAndShowGUI(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        DragDropOnJFrame test = new DragDropOnJFrame();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setLocationRelativeTo(null);
        test.pack();
        test.setVisible(true);
    }

    private TransferHandler handler = new TransferHandler() {
                                        private static final long serialVersionUID = -3635549271503491226L;

                                        public boolean canImport(TransferHandler.TransferSupport support) {
                                            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                                return false;
                                            }

                                            boolean copySupported = (COPY & support.getSourceDropActions()) == COPY;

                                            if (!copySupported) {
                                                return false;
                                            }

                                            support.setDropAction(COPY);

                                            return true;
                                        }

                                        public boolean importData(TransferHandler.TransferSupport support) {
                                            if (!canImport(support)) {
                                                return false;
                                            }

                                            Transferable t = support.getTransferable();

                                            try {
                                                Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
                                                l = (List<File>)o;
                                                output.setText(l.toString());

                                            } catch (UnsupportedFlavorException e) {
                                                return false;
                                            } catch (IOException e) {
                                                return false;
                                            }

                                            return true;
                                        }
                                    };
}
