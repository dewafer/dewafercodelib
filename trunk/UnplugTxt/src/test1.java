import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class test1 {

	/**
	 * 转换格式
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// JAR中的文本
		// String PATH = "C:\\Users\\dewafer\\Desktop\\电书\\传记\\姚明自传\\1.txt";
		String PATH = "C:\\Users\\dewafer\\Desktop\\电书\\科幻\\圣斗士冥王篇\\1";
		// 正确导出的文本
		// String PATH2 = "C:\\Users\\dewafer\\Desktop\\电书\\传记\\j\\1.txt";
		String PATH2 = "C:\\Users\\dewafer\\Desktop\\电书\\科幻\\圣斗士冥王篇y - 副本\\1.txt";
		// 输出转换后的文本
		// String OUTPATH = "C:\\test.txt";
		String OUTPATH = "C:\\Users\\dewafer\\Desktop\\电书\\科幻\\圣斗士冥王篇y\\1.txt";
		long length1;
		long length2;
		long length3;
		File f1 = new File(PATH);
		File f2 = new File(PATH2);
		File f3 = new File(OUTPATH);
		File of = new File(OUTPATH);
		length1 = f1.length();
		length2 = f2.length();
		length3 = f3.length();
		byte[] buff1 = new byte[(int) length1];
		byte[] buff2 = new byte[(int) length2];
		byte[] buff3 = new byte[(int) length3];
		FileInputStream fis1 = new FileInputStream(f1);
		FileInputStream fis2 = new FileInputStream(f2);
		FileInputStream fis3 = new FileInputStream(f3);
		FileOutputStream fos = new FileOutputStream(of);
		// 跳过头上两个错误字符
		fis1.skip(2);
		fis1.read(buff1);
		fis2.read(buff2);
		// UTF-8 头默认为-1,-2
		// 错误的为72,66
		fos.write(-1);
		fos.write(-2);
		fos.write(buff1);
		fos.flush();
		fos.close();
		fis3.read(buff3);
		// 比较结果
		print2buffs(buff2, buff3);
		fis1.close();
		fis2.close();
		fis3.close();
	}

	static void println(Object o) {
		System.out.println(o);
	}

	static void print(Object o) {
		System.out.print(o);
	}

	static void println() {
		System.out.println();
	}

	static void print2buffs(byte[] b1, byte[] b2) {
		for (int i = 0; i < min(b1.length, b2.length); i++) {
			print(b1[i] + ":" + b2[i] + "?" + (b1[i] == b2[i]) + "|");
			if (i % 10 == 0) {
				println();
			}
		}
	}

	static int min(int a, int b) {
		return (a < b) ? a : b;
	}

}
