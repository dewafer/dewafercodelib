package util.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Object2Serialize sb = new Object2Serialize();
        sb.setCount(99);
        sb.setName("vsviowev");
        println("before serialize:");
        println(sb);
        println("writing...");
        writeObject(sb, "D:\\sb.object");
        println("reading...");
        Object sb2 = readObject("D:\\sb.object");
        println("after serialize:");
        println(sb2);

    }

    public static void writeObject(Object o, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.close();
        fos.close();
    }

    public static void writeObject(Object o, String file) throws IOException {
        writeObject(o, new File(file));
    }

    public static Object readObject(File file) throws IOException, ClassNotFoundException {
        Object o;
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        o = ois.readObject();
        ois.close();
        fis.close();
        return o;
    }

    public static Object readObject(String file) throws IOException, ClassNotFoundException {
        return readObject(new File(file));
    }

    private static void println(Object o) {
        System.out.println(o);
    }

}
