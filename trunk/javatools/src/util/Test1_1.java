package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class Test1_1 {

    private static final long   BYTE_LENGTH   = 102400L;

    private static final long   RETURN_LENGTH = 1000L;

    private static final char[] WORDS         =
                                                  {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
        'd', 'e', 'f'                             };

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String file = "D:\\XJad.rar.txt";
                createFile(file);
//        creatFileCount(file);
        System.out.println(file + " done.");
        //        String file = "D:\\testbytefile.txt";
        //        createFile(file);
        //        creatFileCount(file);
        //        System.out.println(file + " done.");
    }

    private static void createFile(String file) throws IOException {
        File f = new File(file);
        Random rand = new Random();
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        long i = 0;
        long j = 0;
        for (; i < BYTE_LENGTH; i++) {
            writer.write(WORDS[rand.nextInt(WORDS.length)]);
            if (j >= RETURN_LENGTH) {
                writer.newLine();
                j = 0;
            }
            j++;
        }
        writer.close();
    }

    private static void creatFileCount(String file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file + "_count.txt")));

        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        StringBuilder all = new StringBuilder();
        String line;
        int linecount = 1;
        while ((line = reader.readLine()) != null) {
            all.append(line);
            //            all.append(System.getProperty("line.separator"));

            writer.append("Line:" + linecount);
            writer.newLine();
            writer.append(count(line));
            writer.append("=============================");
            writer.newLine();

            linecount++;
        }

        writer.newLine();
        writer.append("whole file:");
        writer.newLine();
        writer.append(count(all.toString()));
        writer.close();
        reader.close();
    }

    private static Map<String, Integer> countMap;

    private static String count(String content) {
        if (countMap == null) {
            countMap = new HashMap<String, Integer>();
        }
        char[] chs = content.toCharArray();
        StringBuilder bytestr = new StringBuilder();

        // count
        for (char ch : chs) {
            bytestr.append(ch);
            if (bytestr.length() == 2) {
                int count = 0;
                if (countMap.containsKey(bytestr.toString())) {
                    count = countMap.get(bytestr.toString());
                }
                countMap.put(bytestr.toString(), ++count);
                bytestr.delete(0, 1);
            }
        }

        // sort
        List<Entry<String, Integer>> lists = new ArrayList<Entry<String, Integer>>();
        lists.addAll(countMap.entrySet());
        Collections.sort(lists, new Comparator<Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> pO1, Entry<String, Integer> pO2) {
                return pO2.getValue().compareTo(pO1.getValue());
            }
        });

        // output
        StringBuilder out = new StringBuilder();
        for (Entry<String, Integer> e : lists) {
            out.append(e.getKey());
            out.append(":");
            out.append(e.getValue());
            out.append(System.getProperty("line.separator"));
        }

        countMap = null;
        return out.toString();
    }
}
