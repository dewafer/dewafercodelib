package javatest.interfacesImplementsTest;

import java.lang.reflect.Method;

public class FaceABImpl implements Facea, Faceb {

    @Override
    public void a() {
        // TODO Auto-generated method stub
        System.out.println("fun void a() called;");
    }

    @Override
    public void b(int pI) {
        // TODO Auto-generated method stub
        System.out.println("fun void b(int) called;");
        System.out.println("\tint arg=" + pI);
    }

    @Override
    public void b(String pI) {
        // TODO Auto-generated method stub
        System.out.println("fun void b(String) called;");
        System.out.println("\tString arg=" + pI);
    }

    @Override
    public Object c(String calledBy) {
        // TODO Auto-generated method stub
        System.out.println("fun void c(String) called;");
        System.out.println("\tString arg=" + calledBy);
        Class clz = this.getClass();
        Method m = null;
        try {
            m = clz.getMethod("c", String.class);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("\tmethod c.getDeclaringClass:" + m.getDeclaringClass());
        return null;
    }

    public static void main(String args[]) {
        FaceABImpl impl = new FaceABImpl();
        Facea a = (Facea)impl;
        Faceb b = (Faceb)impl;
        a.a();
        a.b(0);
        a.c("(Facea)a.c");
        b.a();
        b.b("string");
        b.c("(Faceb)b.c");
    }
}
