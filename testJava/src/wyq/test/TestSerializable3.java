package wyq.test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestSerializable3 implements Serializable {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map = new HashMap();
		map.put("key1", "value1");
		System.out.println(map);
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(
				"testSerializable3.xml"));
		encoder.writeObject(map);
		encoder.flush();
		encoder.close();
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(
				"testSerializable3.xml"));
		map = (Map) decoder.readObject();
		System.out.println(map);
	}

}
