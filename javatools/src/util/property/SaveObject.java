package util.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

import util.property.testuse.ObjectBean;

public class SaveObject {

    /**
     * @param args
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws FileNotFoundException 
     * @throws IllegalArgumentException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws InvocationTargetException 
     * @throws InterruptedException 
     */
    public static void main(String[] args)
        throws IllegalArgumentException, FileNotFoundException, IllegalAccessException, IOException,
        ClassNotFoundException, InstantiationException, InvocationTargetException, InterruptedException {
        ObjectBean ob = new ObjectBean();
        save(ob, "D:\\save_OB.txt");

        ObjectBean ob2 = (ObjectBean)load("D:\\save_OB.txt");

        println("ob:" + ob);
        println("ob2:" + ob2);
        println("equals?" + ob.equals(ob2));

        Thread.sleep(2000);
        save(ob2, "D:\\save_OB2.txt");

    }

    private static void println(Object o) {
        System.out.println(o);
    }

    public static void save(Object o, String file)
        throws IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException {
        Properties p = new Properties();
        saveToProp(p, o, "object.save.root");

        File f = new File(file);
        //        p.storeToXML(new FileOutputStream(f), "- no comment -");
        p.store(new FileOutputStream(f), "- no comment -");
    }

    public static Object load(String file)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException,
        IOException, IllegalArgumentException, InvocationTargetException {
        Properties p = new Properties();
        File f = new File(file);
        p.load(new FileInputStream(f));
        Object o = loadFromProp(p, "object.save.root");
        return o;
    }

    private static void saveToProp(Properties p, Object o, String key)
        throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = o.getClass();
        p.put(key, clazz.getCanonicalName());
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
                f.setAccessible(true);
                if (f.getType().isPrimitive() || f.getType().equals(String.class)) {
                    p.put(clazz.getCanonicalName() + "." + f.getName(), String.valueOf(f.get(o)));
                } else {
                    saveToProp(p, f.get(o), clazz.getCanonicalName() + "." + f.getName());
                }
            }
        }
    }

    private static Object loadFromProp(Properties p, String key)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException {
        String type = p.getProperty(key);
        Class<?> clazz = Class.forName(type);
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
                f.setAccessible(true);
                if (f.getType().isPrimitive() || f.getType().equals(String.class)) {
                    Object value = p.getProperty(clazz.getCanonicalName() + "." + f.getName());
                    f.set(obj, convert(value, f.getType()));
                } else {
                    f.set(obj, loadFromProp(p, clazz.getCanonicalName() + "." + f.getName()));
                }
            }
        }
        return obj;
    }

    private static Object convert(Object value, Class<?> type)
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException,
        ClassNotFoundException {
        if (type.equals(value.getClass())) {
            return value;
        }
        Object converted = null;
        type = manualBoxing(type);
        Method[] methods = type.getMethods();
        Method parseMethod = null;
        for (Method m : methods) {
            if (m.getName().startsWith("parse")) {
                parseMethod = m;
                break;
            }
        }
        if (parseMethod != null) {
            converted = parseMethod.invoke(null, value);
        }
        return converted;
    }

    private static Class<?> manualBoxing(Class<?> primitive) throws ClassNotFoundException {
        String name = primitive.getCanonicalName();
        if (name == "int") {
            name = "integer";
        } else if (name == "char") {
            name = "character";
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return Class.forName("java.lang." + name);
    }
}
