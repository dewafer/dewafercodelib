package wyq.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(encode("tori001","SHA"));
	}

	public static String encode(String txt, String method) {
		String result = txt;
		try {
			MessageDigest md = MessageDigest.getInstance(method);
			byte[] input = txt.getBytes();
			byte[] output = md.digest(input);
			result = getHex(output);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return txt;
	}

	static final String HEXES = "0123456789ABCDEF";

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

}
