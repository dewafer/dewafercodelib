package util.LinerUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import jp.co.isid.fine.io.filemap.annotation.Position;
//import jp.co.isid.fine.io.filemap.annotation.impl.FilemapAnnotationReaderImpl;
//import jp.co.isid.fine.io.filemap.reader.CsvReader;
//import jp.co.isid.fine.io.filemap.reader.impl.CsvReaderImpl;
//import jp.co.isid.fine.io.filemap.reader.impl.LineProcessorFactoryImpl;

public class LinerUtil {

    public static String BASE = "D:\\Lo25617\\";

    static {
        File base = new File(BASE);
        if (!base.exists()) {
            base.mkdirs();
        }
    }

    //    private static CsvReader csvReader;

    //    static {
    //        CsvReaderImpl cr = new CsvReaderImpl();
    //        cr.setFilemapAnnotationReader(new FilemapAnnotationReaderImpl());
    //        cr.setLineProcessorFactory(new LineProcessorFactoryImpl());
    //        csvReader = cr;
    //    }

    //    public static <T> List<T> readLiner(String spl, Class<? extends T> clazz) {
    //        File csvFile = new File(BASE + spl);
    //
    //        List<T> lines = new ArrayList<T>();
    //        lines.addAll(csvReader.readCsv(csvFile, clazz).getLines());
    //        return lines;
    //    }

    //    public static <T> T readHeader(String spl, Class<? extends T> header, Class<?> liner) {
    //
    //        File csvFile = new File(BASE + spl);
    //
    //        T head = (T)csvReader.readHeaderedCsv(csvFile, header, liner).getHeader();
    //        return head;
    //    }

    private static void write(String path, String context) throws IOException {
        BufferedWriter bfw = new BufferedWriter(new FileWriter(path));
        bfw.write(context);
        bfw.flush();
        bfw.close();
    }

    public static void writeFile(String path, String context) throws IOException {
        write(path, context);
    }

    public static <T> void write(List<T> lines, Class<? extends T> clzOfLine, String fileName) {
        StringBuilder sb = new StringBuilder();

        Class<? extends T> clazz = clzOfLine;
        List<Field> fields = new ArrayList<Field>();
        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            Field f = clazz.getDeclaredFields()[i];
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                fields.add(f);
            }
        }
        //        Collections.sort(fields, new Comparator<Field>() {
        //
        //            @Override
        //            public int compare(Field pO1, Field pO2) {
        //                if (pO1.getAnnotations().length > 0) {
        //                    int pos1 = pO1.getAnnotation(Position.class).value();
        //                    int pos2 = pO2.getAnnotation(Position.class).value();
        //                    if (pos1 > pos2)
        //                        return -1;
        //                    else if (pos1 < pos2)
        //                        return 1;
        //                    else
        //                        return 0;
        //                } else {
        //                    return pO1.getName().compareTo(pO2.getName());
        //                }
        //            }
        //        });

        for (Field f : fields) {
            sb.append(f.getName());
            if (fields.indexOf(f) != fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(System.getProperty("line.separator"));

        for (T line : lines) {
            for (Field f : fields) {
                try {
                    f.setAccessible(true);
                    sb.append("\"");
                    sb.append(f.get(line));
                    sb.append("\"");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    if (fields.indexOf(f) != fields.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            sb.append(System.getProperty("line.separator"));
        }
        try {
            write(BASE + fileName + ".csv", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void write(T line, Class<? extends T> clzOfLine, String fileName) {
        List<T> lists = new ArrayList<T>();
        lists.add(line);
        write(lists, clzOfLine, fileName);
    }

    public static void setBASE(String path) {
        if (!path.trim().endsWith("\\") && !path.trim().endsWith("/")) {
            path = path.trim() + "\\";
        }
        BASE = path;
    }

    public static void copyFields(final Object from, final Object to) {
        copyFields(from, to, null);
    }

    public static void copyFields(final Object from, final Object to, String[] fields) {
        Class<?> fromClzz = from.getClass();
        Class<?> toClzz = to.getClass();
        List<String> fieldslist = null;
        if (fields != null) {
            fieldslist = new ArrayList<String>();
            Collections.addAll(fieldslist, fields);
        }
        for (Field f : fromClzz.getDeclaredFields()) {
            if (fieldslist != null && !fieldslist.contains(f.getName())) {
                continue;
            }
            if (!isFinal(f) && !isStatic(f)) {
                try {
                    findAndSet(f, toClzz.getDeclaredFields(), from, to);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void findAndSet(Field from, Field[] targets, Object from_o, Object to_o)
        throws IllegalArgumentException, IllegalAccessException {
        for (Field to : targets) {
            if (to.getName().equals(from.getName())) {
                if (to.getType().equals(from.getType())) {
                    to.setAccessible(true);
                    from.setAccessible(true);
                    to.set(to_o, from.get(from_o));
                    break;
                }
            }
        }
    }

    private static boolean isFinal(Field f) {
        return Modifier.isFinal(f.getModifiers());
    }

    private static boolean isStatic(Field f) {
        return Modifier.isStatic(f.getModifiers());
    }

    public static void printObject(Object o) {
        Class<?> c = o.getClass();
        println(o.toString());
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            print(Modifier.toString(f.getModifiers()));
            // if(isStatic(f)){
            // print(" static ");
            // }
            // if(isFinal(f)){
            // print(" final ");
            // }
            print(" ");
            print(f.getType().getName());
            print(" ");
            print(f.getName());
            print(" = ");
            try {
                print(f.get(o));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                print(" null;");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                print(" [unknown];");
            }
            println();
        }
        println();
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void println() {
        System.out.println();
    }

    public static void print(Object o) {
        System.out.print(o);
    }

//    public static void main(String[] args) {
//        LinerUtil u = new LinerUtil();
//    }

}
