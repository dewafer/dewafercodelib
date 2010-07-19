package util.DtoCommentReplace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Run implements Iterable<Run.Entity> {

    private static String  BASE        = "D:/rtgsv6/eclipse/workspace";

    private static String  DTOFILE     =
                                           "/mitsubishi_base_java/src/main/java/jp/co/isid/rtgs/report/web/lo/lo25617/dto/Lo256173LinerDto.java";

    private static String  DTOFILE_OUT = "D:/test.java";

    private File           dtoFile;

    private File           dtoFile_out;

    private BufferedReader reader;

    private BufferedWriter writer;

    private Stack<Entity>  _stack      = new Stack<Entity>();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        System.out.println("start");
        Run r = new Run();
        String line = null;
        String comment = null;
        String property = null;
        List<String> context = new ArrayList<String>();
        List<String> temp = new ArrayList<String>();
        int getCount = 0;
        while ((line = r.readLine()) != null) {
            if (r.isComment(line)) {
                comment = line;
                getCount++;
            }
            if (r.isPosition(line)) {
                context.add(line);
                continue;
            }
            if (r.isProperty(line)) {
                property = line.substring(line.indexOf("private String") + 14, line.lastIndexOf(';')).trim();
                getCount++;
            }
            if (getCount >= 2 && comment != null && property != null) {
                Entity e = new Entity();
                e.setComment(comment);
                e.setProperty(property);
                r.push(e);
                getCount = 0;
                comment = null;
                property = null;
            }
            context.add(line);
        }
        for (Entity e : r) {
            for (String l : context) {
                comment = e.comment.trim().replaceAll("/", "");
                comment = comment.trim().replaceAll("\\*", "");
                comment = comment.trim();
                property = "#" + e.property.trim() + "#";
                temp.add(l.replaceAll(property, comment));
            }
            context.clear();
            context.addAll(temp);
            temp.clear();
        }
        for (String l : context) {
            r.writeLine(l);
        }
        r.close();
        System.out.println("done");
    }

    public Run() throws IOException {
        dtoFile = new File(BASE + DTOFILE);
        dtoFile_out = new File(DTOFILE_OUT);
        reader = new BufferedReader(new FileReader(dtoFile));
        writer = new BufferedWriter(new FileWriter(dtoFile_out));
    }

    /**
     * @return
     * @throws IOException
     * @see java.io.BufferedReader#readLine()
     */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * @throws IOException
     * @see java.io.BufferedReader#reset()
     */
    public void reset() throws IOException {
        reader.reset();
    }

    /**
     * @param pStr
     * @throws IOException
     * @see java.io.Writer#write(java.lang.String)
     */
    public void writeLine(String pStr) throws IOException {
        writer.write(pStr);
        writer.newLine();
    }

    public void close() throws IOException {
        writer.flush();
        writer.close();
        reader.close();
    }

    public boolean isComment(String str) {
        return str.trim().startsWith("/**") && str.trim().endsWith("*/");
    }

    public boolean isProperty(String str) {
        return str.trim().startsWith("private String") && str.trim().endsWith(";");
    }

    public boolean isPosition(String str) {
        return str.trim().startsWith("@Position(") && str.trim().endsWith(")");
    }

    /**
     * @return
     * @see java.util.Stack#peek()
     */
    public Entity peek() {
        return _stack.peek();
    }

    /**
     * @return
     * @see java.util.Stack#pop()
     */
    public Entity pop() {
        return _stack.pop();
    }

    /**
     * @param pItem
     * @return
     * @see java.util.Stack#push(java.lang.Object)
     */
    public Entity push(Entity pItem) {
        return _stack.push(pItem);
    }

    /**
     * @return
     * @see java.util.Vector#size()
     */
    public int size() {
        return _stack.size();
    }

    /**
     * @return
     * @see java.util.AbstractList#iterator()
     */
    public Iterator<Entity> iterator() {
        return _stack.iterator();
    }

    public static class Entity {
        private String comment;

        private String property;

        /**
         * #comment#を取得します。
         * @return #comment#
         */
        public String getComment() {
            return comment;
        }

        /**
         * #comment#を設定します。
         * @param pComment 設定する#comment#
         */
        public void setComment(String pComment) {
            this.comment = pComment;
        }

        /**
         * #property#を取得します。
         * @return #property#
         */
        public String getProperty() {
            return property;
        }

        /**
         * #property#を設定します。
         * @param pProperty 設定する#property#
         */
        public void setProperty(String pProperty) {
            this.property = pProperty;
        }
    }
}
