package wyq.tool.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.swing.text.JTextComponent;

public class InputStreamWrapper extends InputStream {

    String lastCmd;
    JTextComponent textPane;
    StringReader reader;

    public InputStreamWrapper(JTextComponent txtPane) {
	this.textPane = txtPane;
    }

    @Override
    public int read() throws IOException {
	if (lastCmd == null || lastCmd.length() == 0) {
	    lastCmd = textPane.getText();
	    reader = new StringReader(lastCmd);
	}
	int read = reader.read();
	if (read == -1) {
	    lastCmd = null;
	    textPane.setText("");
	}
	return read;
    }
}
