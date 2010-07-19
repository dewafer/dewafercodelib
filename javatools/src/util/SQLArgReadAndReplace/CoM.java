package util.SQLArgReadAndReplace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoM {
    public static String process(String SQLcontext, List<String[]> argMap) {
        String _buff = SQLcontext;
        String replaceArgEx;
        String toValue = "";
        //        Collections.sort(argMap, new Comparator<String[]>() {
        //
        //            @Override
        //            public int compare(String[] o1, String[] o2) {
        //                return o2[0].length() - o1[0].length();
        //            }
        //
        //        });
        for (int i = 0; i < argMap.size(); i++) {
            //            _buff = _buff.replace(argMap.get(i)[0], argMap.get(i)[1]);
            String[] ArgValuePair = argMap.get(i);
            replaceArgEx = "\\" + ArgValuePair[0] + "\\b";
            if (ArgValuePair.length > 1) {
                toValue = ArgValuePair[1];
            } else {
                toValue = "";
            }
            _buff = _buff.replaceAll(replaceArgEx, toValue);
        }
        return _buff;
    }

    public static List<String[]> initArgMap(String ARGMAP) throws IOException {
        String line;
        String[] tmp;
        ArrayList<String[]> ARGMAPLIST = new ArrayList<String[]>();
        BufferedReader bfr = new BufferedReader(new FileReader(ARGMAP));
        while ((line = bfr.readLine()) != null) {
            tmp = line.split(",");
            ARGMAPLIST.add(tmp);
        }
        bfr.close();
        return ARGMAPLIST;
    }

    //    public static List<String> guessArg(String line) throws IOException {
    //        List<String> args = new ArrayList<String>();
    //        StringBuilder arg = new StringBuilder();
    //        int tmp;
    //        boolean isArg = false;
    //        StringReader sr = new StringReader(line);
    //        while ((tmp = sr.read()) != -1) {
    //            if (tmp == (int)'$') {
    //                isArg = true;
    //            }
    //            if (isArg) {
    //                if (isWords(tmp)) {
    //                    arg.append((char)tmp);
    //                } else {
    //                    isArg = false;
    //                    String tmparg = arg.toString();
    //                    if (!args.contains(tmparg)) {
    //                        args.add(tmparg);
    //                    }
    //                    arg = new StringBuilder();
    //                }
    //            }
    //        }
    //        return args;
    //    }

    public static List<String> guessArg(String line) throws IOException {
        List<String> args = new ArrayList<String>();
        String arg;
        String regex = "\\$[A-Z]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        while (m.find()) {
            arg = m.group();
            if (!args.contains(arg)) {
                args.add(m.group());
            }
        }
        return args;
    }

    public static String toStr(List<String> argList) {
        StringBuilder sb = new StringBuilder();
        for (String line : argList) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    //    public static boolean isWords(int value) {
    //        boolean result = false;
    //        if (value < 122 && value > 97) {
    //            result = true;
    //        }
    //        if (value < 90 && value > 65) {
    //            result = true;
    //        }
    //        if (value == (int)'$') {
    //            result = true;
    //        }
    //        return result;
    //    }

    public static String removeBeginEnd(String context) {
        String tmp = context.replaceAll("#begin", "");
        tmp = tmp.replaceAll("#end", "");
        return tmp;

    }

    public static String read(String path) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = new BufferedReader(new FileReader(path));
        while ((line = bfr.readLine()) != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        bfr.close();
        return sb.toString();
    }

    public static void write(String path, String context) throws IOException {
        BufferedWriter bfw = new BufferedWriter(new FileWriter(path));
        bfw.write(context);
        bfw.flush();
        bfw.close();
    }

    public static String[] readLines(String path) throws IOException {
        String line;
        List<String> lines = new ArrayList<String>();
        BufferedReader bfr = new BufferedReader(new FileReader(path));
        while ((line = bfr.readLine()) != null) {
            if (!line.startsWith("#")) {
                lines.add(line);
            }
        }
        bfr.close();
        return lines.toArray(new String[0]);
    }

}
