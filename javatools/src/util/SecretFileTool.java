package util;

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

public class SecretFileTool {

    public static final int     INBUFF_LENGTH  = 1024;

    private static final String DEFAULT_PREFIX = "_output_{0}.txt";

    public static final long    DEFAULT_SPLIT  = 100000L;

    private File                f;

    private File[]              fs;

    private Compressor          comp           = new Compressor() {

                                                   // default compressor do nothing
                                                   @Override
                                                   public String compress(String pInput) {
                                                       return pInput.replace("000000", " ");
                                                   }

                                                   @Override
                                                   public String decompress(String pInput) {
                                                       return pInput.replace(" ", "000000");
                                                   }
                                               };

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //        byte[] src = {'a', 'b', 'c', 'd'};
        //        printBytes(src);
        //        String hex = bytesToHexString(src);
        //        println(hex);
        //        byte[] converted = hexStringToBytes(hex);
        //        printBytes(converted);
        //        println(src.equals(converted));

        //        toTextFile("D:\\p.jpg", "D:\\p.txt");
        //        toByteFile("D:\\p.txt", "D:\\p2.jpg");
        //        println("Done.");

        //        toTextFile("D:\\XJad.rar", "D:\\XJad.rar.txt");
        //        toByteFile("D:\\XJad.rar.txt", "D:\\XJad2.rar");
        //        println("Done.");

        SecretFileTool tool1 = new SecretFileTool("D:\\p.jpg");
        tool1.toTextFile(100 * 1024);
        SecretFileTool tool2 =
            new SecretFileTool("D:\\p22.jpg", new String[]{"D:\\p.jpg_output_0.txt", "D:\\p.jpg_output_1.txt",
                "D:\\p.jpg_output_2.txt", "D:\\p.jpg_output_3.txt", "D:\\p.jpg_output_4.txt"});
        tool2.toByteFile();
        println("Done.");
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
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        File toFile = new File(f.getAbsolutePath() + getDefaultFileName(i));
        BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
        byte[] inbuff = new byte[INBUFF_LENGTH];

        while ((bis.read(inbuff) != -1)) {
            writer.write(comp.compress(bytesToHexString(inbuff)));
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
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
        for (File from : fs) {

            reader = new BufferedReader(new FileReader(from));
            while ((outbuff = hexStringToBytes(comp.decompress(reader.readLine()))) != null) {
                bos.write(outbuff);
            }

            reader.close();
        }
        bos.close();
    }

    private String getDefaultFileName(int i) {
        return DEFAULT_PREFIX.replace("{0}", Integer.toString(i));
    }

    public static void toTextFile(File from, File to) throws IOException {

        byte[] inbuff = new byte[INBUFF_LENGTH];

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(from));
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
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(to));

        while ((outbuff = hexStringToBytes(reader.readLine())) != null) {
            bos.write(outbuff);
        }

        reader.close();
        bos.close();

    }

    public static void toByteFile(String from, String to) throws IOException {
        toByteFile(new File(from), new File(to));
    }

    private static void println(Object o) {
        System.out.println(o);
    }

    private static void printBytes(byte[] bs) {
        for (byte b : bs) {
            System.out.print(b);
        }
        System.out.println();
    }

    /**  
    
     * Convert byte[] to hex string.这里我们可以将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。  
     * @param src byte[] data  
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
     * @param hexString the hex string  
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
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**  
     * Convert char to byte  
     * @param c char  
     * @return byte  
     */

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    public Compressor getComp() {
        return this.comp;
    }

    public void setComp(Compressor pComp) {
        this.comp = pComp;
    }
}
