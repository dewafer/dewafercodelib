package wyq.util.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import wyq.util.SecretFileTool;

public class MainGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();

		// toTxtFile
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Choose a byte file...");
		chooser.setMultiSelectionEnabled(false);
		int dialogResult = chooser.showOpenDialog(null);
		if (dialogResult == JFileChooser.APPROVE_OPTION) {
			SecretFileTool tool = new SecretFileTool(chooser.getSelectedFile());
			try {
				tool.toTextFile(SecretFileTool.DEFAULT_SPLIT);
				JOptionPane.showMessageDialog(null, "Done.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
			}
		}

		// toByteFile
		chooser.setSelectedFile(null);
		chooser.setSelectedFiles(null);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Choose some txt files...");
		chooser.setMultiSelectionEnabled(true);
		dialogResult = chooser.showOpenDialog(null);
		if (dialogResult == JFileChooser.APPROVE_OPTION) {
			File[] files = chooser.getSelectedFiles();

			chooser.setSelectedFile(null);
			chooser.setSelectedFiles(null);
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setDialogTitle("save as...");
			chooser.setMultiSelectionEnabled(false);
			dialogResult = chooser.showSaveDialog(null);
			if (dialogResult == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				SecretFileTool tool = new SecretFileTool(f, files);
				try {
					tool.sortFs();
					tool.toByteFile();
					JOptionPane.showMessageDialog(null, "Done.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getStackTrace());
				}
			}
		}
	}

}
