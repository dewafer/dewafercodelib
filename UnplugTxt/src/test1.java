import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class test1 {

	/**
	 * ת����ʽ
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// JAR�е��ı�
		// String PATH = "C:\\Users\\dewafer\\Desktop\\����\\����\\Ҧ���Դ�\\1.txt";
		String PATH = "C:\\Users\\dewafer\\Desktop\\����\\�ƻ�\\ʥ��ʿڤ��ƪ\\1";
		// ��ȷ�������ı�
		// String PATH2 = "C:\\Users\\dewafer\\Desktop\\����\\����\\j\\1.txt";
		String PATH2 = "C:\\Users\\dewafer\\Desktop\\����\\�ƻ�\\ʥ��ʿڤ��ƪy - ����\\1.txt";
		// ���ת������ı�
		// String OUTPATH = "C:\\test.txt";
		String OUTPATH = "C:\\Users\\dewafer\\Desktop\\����\\�ƻ�\\ʥ��ʿڤ��ƪy\\1.txt";
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
		// ����ͷ�����������ַ�
		fis1.skip(2);
		fis1.read(buff1);
		fis2.read(buff2);
		// UTF-8 ͷĬ��Ϊ-1,-2
		// �����Ϊ72,66
		fos.write(-1);
		fos.write(-2);
		fos.write(buff1);
		fos.flush();
		fos.close();
		fis3.read(buff3);
		// �ȽϽ��
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
