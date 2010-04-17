

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
/**
 * This is a class which extends Form class and displays the required properties.
 * This class extends java.lang.System. Which is used to get the system properties.
 * 
 * 
 *
 */
public class InfoClass extends Form implements CommandListener {
	
	/**
	 * Local object of the Midlet
	 */
	SystemInfoMIDlet2 infoMidlet;
	
	/**
	 * Forms to be used in the example application
	 */
	Form mainForm;
	
	/**
	 * command to display the properties.
	 */
	private Command DisplayProperties = null;
	/**
	 * command to exit the application
	 */
	private Command ExitApp = null;
	
	/**
	 * Constructor being passed the instance of the MIDlet
	 * @param infoMidlet
	 */	
	InfoClass(SystemInfoMIDlet2 infoMidlet)
	{
		super("System Information");
		this.infoMidlet = infoMidlet;
		Display.getDisplay( this.infoMidlet).setCurrent(this);
		initUI();
	}


	/**
	 * This method is used to initilise the form and the required commands.
	 */
	protected void initUI()
	{
		

		addCommand();
		 mainForm = new Form("System Properties");
		
		
	}
	
	/**
	 * Adds the required commands to the form. 
	 * 1.DisplayProperties
	 * 2.ExitApp
	 */
	protected void addCommand()
	{
		DisplayProperties = new Command("DisplayProperties" ,Command.OK,1);
		ExitApp = new Command("Exit" ,Command.EXIT,1);
		
		this.addCommand(DisplayProperties);
		this.addCommand(ExitApp);
		this.setCommandListener(this);
	}
	

	/**
	 * handles the command selection.
	 * @param c the command
	 * @param dis the displayable
	 */
	public void commandAction(Command c, Displayable dis)
	{
		if (c.equals(this.DisplayProperties)) {
			
			 displaySystemProp();
			 mainForm.addCommand(ExitApp);
			 mainForm.setCommandListener(this);
			 

		} else if (c.equals(this.ExitApp)) {
			this.infoMidlet.notifyDestroyed();
		}
	}
	
	/**
	 * This method acctually displays all the properties like IMEI no Video support of the device.
	 * It actually calls System.getProperty method to get these properties.The jad/jar has to be signed
	 * to get some of these properties. If not they return null.
	 */
	
	public void displaySystemProp()
	{
		
		mainForm.append("Free RamMemory: " +System.getProperty("com.nokia.memoryramfree") +"\n");
		mainForm.append("Battery Level: " +System.getProperty("com.nokia.mid.batterylevel")+"\n");
		mainForm.append("Present Time format type: " +System.getProperty("com.nokia.mid.timeformat")+"\n");
		mainForm.append("Present Date format type: " +System.getProperty("com.nokia.mid.dateformat")+"\n");
		mainForm.append("IMEI no of the device: " +System.getProperty("com.nokia.mid.imei")+"\n");
		mainForm.append("Is Network available: " +System.getProperty("com.nokia.mid.networkavailability")+"\n");
		mainForm.append("Supports Video capture: " +System.getProperty("supports.video.capture")+"\n");
		
		Display.getDisplay( this.infoMidlet).setCurrent(mainForm);
	}
	
}
