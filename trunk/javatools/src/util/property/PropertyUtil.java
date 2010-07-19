package util.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyUtil extends Properties {

    private static final long   serialVersionUID = -1816689998864386622L;

    private static PropertyUtil pu;

    private static File         propertyFile;

    private OutputStream        out;

    private InputStream         in;

    private PropertyUtil() throws FileNotFoundException {}

    public static PropertyUtil getInstance(String file) throws FileNotFoundException {
        return getInstance(new File(file));
    }

    public static PropertyUtil getInstance(File f) throws FileNotFoundException {
        init(f);
        return pu;
    }

    public static PropertyUtil getInstance() throws IOException {
        if (pu != null) {
            return pu;
        } else {
            File f = new File(PropertyUtil.class.getSimpleName() + ".properties");
            if (!f.exists()) {
                f.createNewFile();
            }
            return getInstance(f);
        }
    }

    private static void init(File f) throws FileNotFoundException {
        if (f != propertyFile) {
            propertyFile = f;
        }
        if (pu == null) {
            pu = new PropertyUtil();
        }
    }

    public void save(String comments) throws IOException {
        if (out == null) {
            out = new FileOutputStream(propertyFile);
        }
        this.store(out, comments);
    }

    public void save() throws IOException {
        save(null);
    }

    public void load() throws IOException {
        if (in == null) {
            in = new FileInputStream(propertyFile);
        }
        this.load(in);
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        PropertyUtil pu = PropertyUtil.getInstance();
        //        pu.setProperty("key1", "value2");
        //        pu.save();
        //        pu.clear();
        pu.load();
        System.out.println(pu.getProperty("key1"));
        pu.setProperty("key1", "i've changed the value.");
        //        pu.save();
        System.out.println("Done.");
        PropertyUtil.getInstance("D:\\set.properties").save("other property file");
    }

}
