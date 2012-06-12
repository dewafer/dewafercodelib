package wyq.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyTest {

	private Properties p = new Properties();

	public void testWriteXMLproperites() throws IOException {
		p.setProperty("key1", "value1");
		OutputStream os = new FileOutputStream("propertyTest.xml");
		p.storeToXML(os, "this is comment 1");
	}

	public void testStore() throws IOException {
		p.setProperty("key2", "value2");
		p.store(new FileWriter("property1.properties"), "this is comment2");
	}

	public void testLoad() throws FileNotFoundException, IOException {
		p.load(new FileReader("property2.properties"));
		println(p);
	}

	public void testLoadFromXML() throws InvalidPropertiesFormatException,
			FileNotFoundException, IOException {
		p.loadFromXML(new FileInputStream("propertyTest1.xml"));
		println(p);
	}

	private static void println(Object o) {
		System.out.println(o);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PropertyTest p = new PropertyTest();
		p.testWriteXMLproperites();
		p.testStore();
		p.testLoad();
		p.testLoadFromXML();
	}
}
