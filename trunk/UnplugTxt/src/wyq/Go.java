package wyq;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Go {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length >= 2) {
			new Go().process(args[0], args[1]);
		} else {
			System.out.println("useage: Go jarFile outputFolder");
		}

//		System.out.println("Start");
//		new Go().process("C:\\Users\\dewafer\\Desktop\\电书\\科幻\\圣斗士冥王篇.jar",
//				"C:\\Users\\dewafer\\Desktop\\电书\\科幻\\圣斗士冥王篇y");

		// new
		// Go().process("C:\\Users\\dewafer\\Desktop\\电书\\古典\\罗贯中：三国演义1.jar",
		// "C:\\Users\\dewafer\\Desktop\\电书\\古典\\不是姚明自传y");
//		System.out.println("Done");
	}

	public void process(String in, String out) throws IOException {
		File outDir = new File(out);
		outDir.mkdirs();
		JarFile jf = new JarFile(in);
		for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements();) {
			JarEntry entry = e.nextElement();
			if (isTxtContext(jf, entry)) {
				giveMeFiles(jf, entry, outDir);
			}
		}
	}

	public boolean isTxtContext(JarFile file, JarEntry entry)
			throws IOException {
		if (entry.isDirectory())
			return false;
		int errcode1, errcode2;
		InputStream is = file.getInputStream(entry);
		errcode1 = is.read();
		errcode2 = is.read();
		if (errcode1 == 72 && errcode2 == 66) {
			return true;
		}
		if (errcode1 == -1 && errcode2 == -2){
			return true;
		}
		return false;
	}

	// public void giveMeFiles(JarFile file, JarEntry entry, File outDir)
	// throws IOException {
	// File nf = new File(outDir.getPath() + File.separator + entry.getName()
	// + ".txt");
	// BufferedOutputStream out = new BufferedOutputStream(
	// new FileOutputStream(nf));
	// BufferedInputStream in = new BufferedInputStream(file
	// .getInputStream(entry));
	// in.skip(2);
	// out.write(-1);
	// out.write(-2);
	// int tmp = -1;
	// while ((tmp = in.read()) != -1) {
	// out.write(tmp);
	// }
	// out.flush();
	// out.close();
	// in.close();
	// }

	public void giveMeFiles(JarFile file, JarEntry entry, File outDir)
			throws IOException {
		File nf = new File(outDir.getPath() + File.separator + entry.getName()
				+ ".txt");
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(nf));
		boolean needfix = true;
		BufferedInputStream in = new BufferedInputStream(file
				.getInputStream(entry));
		int buffsize = in.available();
		byte[] buff = new byte[buffsize];
		while (in.available() > 0) {
			if (in.available() != buffsize) {
				buffsize = in.available();
				buff = new byte[buffsize];
			}
			in.read(buff);
			if (needfix) {
				buff[0] = -1;
				buff[1] = -2;
				needfix = false;
			}
			out.write(buff);
		}
		out.flush();
		out.close();
		in.close();
	}
}
