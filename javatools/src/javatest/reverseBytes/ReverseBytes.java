package javatest.reverseBytes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReverseBytes {

    public static final String INPUT_FILE  = "D:\\0023ae8f98230dd480934b.jpg";

    public static final String OUTPUT_FILE = "D:\\0023ae8f98230dd480934b_R.jpg";

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        File inf = new File(INPUT_FILE);
        File outf = new File(OUTPUT_FILE);
        byte[] buff = new byte[(int)inf.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inf));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outf));
        while (bis.read(buff) != -1) {
            bos.write(reverse(buff));
        }
        bis.close();
        bos.flush();
        bos.close();
    }

    private static byte[] reverse(byte[] pBuff) {
        byte[] tmp = new byte[pBuff.length];
        for (int i = 0; i < pBuff.length; i++) {
            tmp[i] = reverse(pBuff[i]);
        }
        return tmp;
    }

    public static byte reverse(byte b) {
        return (byte)(b ^ 0xFF);
    }

    public static void println(Object o) {
        System.out.println(o);
    }

}
