package wyq.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author dewafer
 * @version 2010/6/21 v1.1
 */
public class SecretFileTool {

	public static final int INBUFF_LENGTH = 1024;
	private static final String DEFAULT_PREFIX = "_output_{0}.txt";
	public static final long DEFAULT_SPLIT = 100000L;
	private File f;
	private File[] fs;
	private Compressor compressor = new Compressor() {

		private final String[][] compressMap = { { "00", "g" }, { "11", "h" },
				{ "22", "i" }, { "33", "j" }, { "44", "k" }, { "55", "l" },
				{ "66", "m" }, { "77", "n" }, { "88", "o" }, { "99", "p" },
				{ "aa", "q" }, { "bb", "r" }, { "cc", "s" }, { "dd", "t" },
				{ "ee", "u" }, { "ff", "v" }, { "gg", "w" }, { "vv", "x" } };

		// default compressor do nothing
		@Override
		public String compress(String pInput) {
			if (pInput == null)
				return "";
			for (int i = 0; i < compressMap.length; i++) {
				pInput = pInput.replace(compressMap[i][0], compressMap[i][1]);
			}
			return pInput;
		}

		@Override
		public String decompress(String pInput) {
			if (pInput == null)
				return "";
			for (int i = compressMap.length - 1; i >= 0; i--) {
				pInput = pInput.replace(compressMap[i][1], compressMap[i][0]);
			}
			return pInput;
		}
	};

	private Comparator<File> comparator;

	// public static void main(String[] args) throws IOException {
	// toTextFile("/home/dewafer/reBackWordCore_1.0.jar","/home/dewafer/test.txt");
	// SecretFileTool t = new SecretFileTool("");
	// BufferedReader r = new BufferedReader(new FileReader(new File(
	// "/home/dewafer/test.txt")));
	// StringBuilder sb = new StringBuilder();
	// String tmp;
	// while ((tmp = r.readLine()) != null) {
	// sb.append(tmp);
	// sb.append(System.getProperty("line.separator"));
	// }
	// String txt = sb.toString();
	// String result = t.comp.compress(txt);
	// String deres = t.comp.decompress(result);
	// System.out.println(result);
	// System.out.println(deres);
	// System.out.println(txt);
	// System.out.println(txt.equals(deres));
	// }

	public void sortFs(Comparator<File> comp) {
		setComparator(comp);
		sortFs();
	}

	public void sortFs() {
		if (comparator == null) {
			comparator = new Comparator<File>() {

				@Override
				public int compare(File arg0, File arg1) {
					return arg0.getName().compareTo(arg1.getName());
				}
			};
		}
		List<File> files = new ArrayList<File>();
		Collections.addAll(files, fs);
		Collections.sort(files, comparator);
		fs = files.toArray(fs);
	}

	public SecretFileTool(File byteFile) {
		this.f = byteFile;
	}

	public SecretFileTool(String f) {
		this.f = new File(f);
	}

	public SecretFileTool(File byteFile, File[] txtFiles) {
		this.f = byteFile;
		this.fs = txtFiles;
	}

	public SecretFileTool(String byteFile, String[] txtFiles) {
		this.f = new File(byteFile);
		this.fs = new File[txtFiles.length];
		for (int i = 0; i < fs.length; i++) {
			fs[i] = new File(txtFiles[i]);
		}
	}

	public void toTextFile(long split) throws IOException {

		long current = 0L;
		int i = 0;

		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f));
		File toFile = new File(f.getAbsolutePath() + getDefaultFileName(i));
		BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
		byte[] inbuff = new byte[INBUFF_LENGTH];

		while ((bis.read(inbuff) != -1)) {
			writer.write(compressor.compress(bytesToHexString(inbuff)));
			writer.newLine();
			current += inbuff.length;
			if (current >= split) {
				i++;
				writer.close();
				toFile = new File(f.getAbsolutePath() + getDefaultFileName(i));
				writer = new BufferedWriter(new FileWriter(toFile));
				current = 0;
			}
		}

		bis.close();
		writer.close();

	}

	public void toByteFile() throws IOException {
		byte[] outbuff;
		BufferedReader reader;
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(f));
		for (File from : fs) {

			reader = new BufferedReader(new FileReader(from));
			while ((outbuff = hexStringToBytes(compressor.decompress(reader
					.readLine()))) != null) {
				bos.write(outbuff);
			}

			reader.close();
		}
		bos.close();
	}

	private String getDefaultFileName(int i) {
		return DEFAULT_PREFIX.replace("{0}", String.format("%1$04d", i));
	}

	public static void toTextFile(File from, File to) throws IOException {

		byte[] inbuff = new byte[INBUFF_LENGTH];

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				from));
		BufferedWriter writer = new BufferedWriter(new FileWriter(to));

		while ((bis.read(inbuff) != -1)) {
			writer.write(bytesToHexString(inbuff));
			writer.newLine();
		}

		bis.close();
		writer.close();

	}

	public static void toTextFile(String from, String to) throws IOException {
		toTextFile(new File(from), new File(to));
	}

	public static void toByteFile(File from, File to) throws IOException {

		byte[] outbuff;

		BufferedReader reader = new BufferedReader(new FileReader(from));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(to));

		while ((outbuff = hexStringToBytes(reader.readLine())) != null) {
			bos.write(outbuff);
		}

		reader.close();
		bos.close();

	}

	public static void toByteFile(String from, String to) throws IOException {
		toByteFile(new File(from), new File(to));
	}

	/**
	 * 
	 * Convert byte[] to hex string.这里我们可以将byte转换成int，
	 * 然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	protected static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return "";
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();

	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	protected static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public Compressor getCompressor() {
		return this.compressor;
	}

	public void setCompressor(Compressor pComp) {
		this.compressor = pComp;
	}

	public Comparator<File> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<File> comparator) {
		this.comparator = comparator;
	}
}