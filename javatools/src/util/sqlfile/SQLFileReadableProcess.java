package util.sqlfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SQLFileReadableProcess {

    static String inFile       = "";

    static String outFile      = "";

    static String tableDefFile = "";

    public static void main(String[] args) throws IOException {

        Map<String, String> words = new HashMap<String, String>();
        BufferedReader f = new BufferedReader(new FileReader(inFile));
        Scanner scan = new Scanner(f);
        String tmp, value;
        while (scan.hasNext()) {
            tmp = scan.next();
            if (!words.containsKey(tmp)) {
                value = findValue(tmp);
                words.put(tmp, value);
            }
        }
        f.reset();

        BufferedWriter w = new BufferedWriter(new FileWriter(outFile));
        while ((tmp = f.readLine()) != null) {
            tmp = replaceWords(words, tmp);
            w.write(tmp);
            w.write(System.getProperty("line.separator"));
        }

    }

    private static String replaceWords(Map<String, String> pWords, String pTmp) {
        for (Entry<String, String> e : pWords.entrySet()) {
            pTmp.replace(e.getKey(), e.getValue());
        }
        return pTmp;
    }

    private static String findValue(String pTmp) throws IOException {
        FileInputStream input = new FileInputStream(new File(tableDefFile));
        HSSFWorkbook book = new HSSFWorkbook(input);
//        book.get
        return null;
    }
}
