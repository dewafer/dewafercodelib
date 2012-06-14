package wyq.test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestSerializable2 implements Serializable {

	private Map map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "TestSerializable2 [map=" + map + "]";
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		TestSerializable2 sb = new TestSerializable2();
		sb.setMap(new HashMap());
		sb.getMap().put("k1", "v1");
		System.out.println(sb);
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(
				"testSerializable2.xml"));
		encoder.writeObject(sb);
		encoder.flush();
		encoder.close();
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(
				"testSerializable2.xml"));
		sb = (TestSerializable2) decoder.readObject();
		System.out.println(sb);
	}

}
