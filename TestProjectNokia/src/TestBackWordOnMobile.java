import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import dewafer.backword.core.Paper;
import dewafer.backword.core.PaperFactory;

public class TestBackWordOnMobile extends MIDlet implements CommandListener {

	private Form form;
	private Command exitCmd;

	 private Paper paper;

	public TestBackWordOnMobile() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		paper = PaperFactory.getPaper();
		form = new Form("Test Back Word");
		 form.append("Try new a paper:" + paper);
		exitCmd = new Command("Exit", Command.EXIT, 1);
		form.addCommand(exitCmd);
		form.setCommandListener(this);
		Display.getDisplay(this).setCurrent(form);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		if (arg0 == exitCmd) {
			this.notifyDestroyed();
		}
	}

}
