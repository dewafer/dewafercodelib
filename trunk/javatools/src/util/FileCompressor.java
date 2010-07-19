package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileCompressor {

    private Map<String, Integer>   countMap;

    private List<String>           topWords;

    private Map<String, Character> map;

    private static final char[]    WORDS =
                                             {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public String compress(String context) {

        context = context.toLowerCase();
        preprocess(context);
        StringBuilder mapLine = new StringBuilder();
        for (Entry<String, Character> e : map.entrySet()) {
            String key = e.getKey();
            String value = e.getValue().toString();
            context = context.replace(key, value);
            mapLine.append(key);
            mapLine.append(value);
        }
        mapLine.append(System.getProperty("line.separator"));

        StringBuilder out = new StringBuilder(context);
        out.insert(0, mapLine.toString());

        map = null;

        return out.toString();
    }

    public String decompress(String context) throws IOException {
        // process map line
        BufferedReader reader = new BufferedReader(new StringReader(context));
        String mapLine = reader.readLine();
        mapLineprocess(mapLine);

        // create content without mapline
        StringBuilder out = new StringBuilder();
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            out.append(tmp);
            out.append(System.getProperty("line.separator"));
        }

        context = out.toString();
        for (Entry<String, Character> e : map.entrySet()) {
            String key = e.getKey();
            String value = e.getValue().toString();
            context = context.replace(value, key);
        }

        return context;

    }

    private void mapLineprocess(String mapLine) {
        // create map for character replacement
        if (map == null) {
            map = new HashMap<String, Character>();
        }

        char[] line = mapLine.toCharArray();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < line.length; i += 3) {
            key.append(line[i]);
            key.append(line[i + 1]);
            map.put(key.toString(), line[i + 2]);
            key.setLength(0);
        }
    }

    private void preprocess(String content) {
        // count and get top used words
        count(content);

        // create map for character replacement
        if (map == null) {
            map = new HashMap<String, Character>();
        }
        for (int i = 0; i < WORDS.length && i < topWords.size(); i++) {
            map.put(topWords.get(i), WORDS[i]);
        }
        topWords = null;
    }

    private boolean isBreakLine(char ch) {
        char[] breakLine = System.getProperty("line.separator").toCharArray();
        for (char c : breakLine) {
            if (c == ch)
                return true;
        }
        return false;
    }

    private void count(String content) {
        if (countMap == null) {
            countMap = new HashMap<String, Integer>();
        }
        char[] chs = content.toCharArray();

        StringBuilder bytestr = new StringBuilder();

        // count
        for (char ch : chs) {
            if (isBreakLine(ch))
                continue;
            bytestr.append(ch);
            if (bytestr.length() == 2) {
                int count = 0;
                if (countMap.containsKey(bytestr.toString())) {
                    count = countMap.get(bytestr.toString());
                }
                countMap.put(bytestr.toString(), ++count);
                bytestr.setLength(0);
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

        // to top words
        if (topWords == null) {
            topWords = new ArrayList<String>();
        }
        for (int i = 0; i < WORDS.length && i < lists.size(); i++) {
            topWords.add(i, lists.get(i).getKey());
        }

        countMap = null;

    }

    public static void main(String[] args) throws IOException {

        String file = "D:\\XJad.rar.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file + "_out.txt"));
        StringBuilder content = new StringBuilder();
        String tmp;

        while ((tmp = reader.readLine()) != null) {
            content.append(tmp);
            content.append(System.getProperty("line.separator"));
        }

        FileCompressor f = new FileCompressor();
        writer.write(f.compress(content.toString()));

        writer.close();
        reader.close();

        reader = new BufferedReader(new FileReader(file + "_out.txt"));
        StringBuilder content2 = new StringBuilder();

        while ((tmp = reader.readLine()) != null) {
            content2.append(tmp);
            content2.append(System.getProperty("line.separator"));
        }

        String decompressed = f.decompress(content2.toString());
        String c = content.toString();
        System.out.println(decompressed.equals(c));
    }
}
