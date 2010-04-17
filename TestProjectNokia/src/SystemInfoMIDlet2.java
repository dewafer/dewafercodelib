import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class SystemInfoMIDlet2 extends MIDlet {

	public SystemInfoMIDlet2() {
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
		try {
		

		InfoClass info = new InfoClass(this);
		
		System.out.println(info);
		
	} catch (Exception e) {
		System.out.println("Exception in the midlet" + e);
	}
	}

}
