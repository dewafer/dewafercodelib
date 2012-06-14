package wyq.test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Random;

public class TestSerializable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361524103550097391L;

	private String strValue = "this is a String value line";

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	private int intValue = 99;

	private int randValue = new Random().nextInt();

	public int getRandValue() {
		return randValue;
	}

	public void setRandValue(int randValue) {
		this.randValue = randValue;
	}

	public void save() throws FileNotFoundException {
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream("object1.xml"));
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();
	}

	public static Object load() throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new FileInputStream("object2.xml"));
		Object o = decoder.readObject();
		decoder.close();
		return o;
	}

	@Override
	public String toString() {
		return "TestSerializable [strValue=" + strValue + ", intValue="
				+ intValue + ", randValue=" + randValue + "]";
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		TestSerializable s = new TestSerializable();
		System.out.println(s);
		s.setIntValue(100);
		s.setStrValue("no!");
		s.save();
		System.out.println(s);
		s = (TestSerializable) TestSerializable.load();
		System.out.println(s);
	}

}
