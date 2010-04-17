


import javax.microedition.midlet.*;


/**
 * 
 * SystemInfoMIDlet.java MIDlet can be used to test the System.getProperty API and its implemetation. <br>
 * This class extends the class javax.microedition.midlet.MIDlet. This class is the entry point in the example application.
 * This midlet creates a reference to Infoclass and sets displayable to that class.
 *
 */
public class SystemInfoMIDlet extends MIDlet  {



	public SystemInfoMIDlet() {
		
	}
	/**
	 * this method is called when the midlet is executed. This is the entry point for the control.
	 */
	public void startApp() throws MIDletStateChangeException {

//		try {
//			
//
//			InfoClass info = new InfoClass(this);
//			
//			System.out.println(info);
//			
//		} catch (Exception e) {
//			System.out.println("Exception in the midlet" + e);
//		}
	}

	/**
	 * pauseApp in class javax.microedition.midlet.MIDlet
	 */
	public void pauseApp() {
		//do nothing   
	}

	
	/**
	 * destroyApp in class javax.microedition.midlet.MIDlet
	 */
	
	public void destroyApp(boolean bool) throws MIDletStateChangeException {
		
		//do nothing
	}

}
