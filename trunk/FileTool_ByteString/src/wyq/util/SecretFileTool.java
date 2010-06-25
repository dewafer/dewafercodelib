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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author dewafer
 * @version 2010/6/25 v1.2
 */
public class SecretFileTool {

	public static final int INBUFF_LENGTH = 1024;
	private static final String DEFAULT_PREFIX = "_output_{0}.txt";
	public static final long DEFAULT_SPLIT = 100000L;
	private File f;
	private File[] fs;
	private Compressor compressor = new FileCompressor();

	private Comparator<File> comparator;

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

	public void toTextFile(long split) throws Exception {

		// file split count
		long current = 0L;
		int i = 0;

		// input stream
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f));

		// output stream
		File toFile = new File(f.getAbsolutePath() + getDefaultFileName(i));
		BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));

		// file buffer
		StringBuilder outputBuff = new StringBuilder();

		// buff length count
		long f_length = f.length();
		int inbuff_length;
		if (f_length < INBUFF_LENGTH) {
			inbuff_length = (int) f_length;
		} else {
			inbuff_length = INBUFF_LENGTH;
		}
		byte[] inbuff = new byte[inbuff_length];

		// read and compress
		while ((bis.read(inbuff) != -1)) {
			outputBuff
					.append(compressor.compressLine(bytesToHexString(inbuff)));
			outputBuff.append(System.getProperty("line.separator"));
			f_length -= inbuff.length;
			if (f_length < INBUFF_LENGTH && f_length > 0) {
				inbuff_length = (int) (f_length);
				inbuff = new byte[inbuff_length];
			}
		}

		// compress whole file
		String compressed = compressor.compressFile(outputBuff.toString());

		// write file and split
		BufferedReader reader = new BufferedReader(new StringReader(compressed));
		String tmp;
		while ((tmp = reader.readLine()) != null) {
			writer.write(tmp);
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

		// close stream and reader
		reader.close();
		bis.close();
		writer.close();

	}

	public void toByteFile() throws IOException {
		// buff
		String outbuffStr;
		BufferedReader reader;
		StringBuilder sbBuff = new StringBuilder();

		// output stream
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(f));

		// read all file
		for (File from : fs) {
			reader = new BufferedReader(new FileReader(from));
			while ((outbuffStr = compressor.decompressLine(reader.readLine())) != null) {
				sbBuff.append(outbuffStr);
				sbBuff.append(System.getProperty("line.separator"));
			}
			reader.close();
		}

		// decompress all file
		outbuffStr = compressor.decompressFile(sbBuff.toString());

		// out put file
		reader = new BufferedReader(new StringReader(outbuffStr));
		while ((outbuffStr = reader.readLine()) != null) {
			bos.write(hexStringToBytes(outbuffStr));
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