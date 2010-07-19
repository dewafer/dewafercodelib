package javatest.pipeTest;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringReader;

public class PipeTest {

    private static PipedReader reader;

    private static PipedWriter writer;
    static {
        reader = new PipedReader();
        writer = new PipedWriter();
        try {
            reader.connect(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String      STRING = "this is a long sentence.";

    /**
     * @param args
     */
    public static void main(String[] args) {
        Thread wt = new Thread(new WriterThread());
        Thread rt = new Thread(new ReaderThread());
        wt.start();
        rt.start();
    }

    public static class WriterThread implements Runnable {

        private StringReader strReader = new StringReader(STRING);

        @Override
        public void run() {
            int tmp = -1;
            try {
                while ((tmp = strReader.read()) != -1) {
                    writer.write(tmp);
                    Thread.sleep(550);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ReaderThread implements Runnable {

        @Override
        public void run() {
            char[] c = new char[1];
            try {
                while (reader.read(c) != -1) {
                    System.out.print(c[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
