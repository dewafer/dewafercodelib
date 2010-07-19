package util.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTool {

    public static String FILE = "abc.txt";

    private static File  f    = new File(FILE);

    public static void save(Properties p, String comments) throws FileNotFoundException, IOException {
        p.store(new FileOutputStream(f), comments);
    }

    public static Properties load() throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(f));
        return p;
    }

    public static void saveXML(Properties p, String comments) throws FileNotFoundException, IOException {
        p.storeToXML(new FileOutputStream(f), comments);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        p.put("key1", "novalue");
        p.put("key2", "value2");
        save(p, "comment1");
        println(p);
        println(p.getProperty("key1"));
        println("===================");
        Properties p2 = load();
        println(p2);
        println(p2.getProperty("key1"));
        println("===================");
        println("equals?" + p.equals(p2));
        //saveXML(p, "xml properties<br/>");
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}
