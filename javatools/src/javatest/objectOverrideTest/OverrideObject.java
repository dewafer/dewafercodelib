package javatest.objectOverrideTest;

public class OverrideObject {

    public OverrideObject(String pName, int value) {
        this.name = pName;
        this.value = value;
    }

    private String name;

    private int    value = 0;

    public OverrideObject() {
        super();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("finalize():" + name + " , value:" + value);
        super.finalize();
    }

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {

        final OverrideObject o = new OverrideObject("o1", 0);
        new OverrideObject("o2", 0);
        Thread.sleep(2000);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    o.value++;
                    System.out.println(o.value);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };

        t.setDaemon(false);
        t.start();

        System.runFinalizersOnExit(true);
        //        OverrideObject o2 = (OverrideObject)o.clone();

        //        System.gc();
        Thread.sleep(2000);
        //        System.gc();
        Thread.sleep(2000);
        //        System.gc();
        System.out.println("end");
        System.exit(0);
    }
}
