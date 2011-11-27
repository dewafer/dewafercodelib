// proxy:DynamicProxyDemo.java
// Broken in JDK 1.4.1_01
package javatest.dynamicProxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Foo {
    void f(String s);

    void g(int i);

    String h(int i, String s);
}

interface Foo2 {
    void f(String s);
}

public class DynamicProxyDemo {
    public static void main(String[] clargs) {
	Foo prox = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(),
		new Class[] { Foo2.class, Foo.class }, new InvocationHandler() {
		    public Object invoke(Object proxy, Method method,
			    Object[] args) {
			System.out.println("InvocationHandler called:"
				+ "\n\tMethod = " + method);
			if (args != null) {
			    System.out.println("\targs = ");
			    for (int i = 0; i < args.length; i++)
				System.out.println("\t\t" + args[i]);
			}
			System.out.println("\r\rdeclaringClass:"
				+ method.getDeclaringClass());
			return null;
		    }
		});
	prox.f("hello");
	prox.g(47);
	prox.h(47, "hello");
	Class<?>[] interfaces = ProxyObject.class.getInterfaces();
	Object object = Proxy.newProxyInstance(
		ProxyObject.class.getClassLoader(), interfaces,
		new InvocationHandler() {

		    @Override
		    public Object invoke(Object proxy, Method method,
			    Object[] args) throws Throwable {
			System.out.println("InvocationHandler called:"
				+ "\n\tMethod = " + method);
			if (args != null) {
			    System.out.println("\targs = ");
			    for (int i = 0; i < args.length; i++)
				System.out.println("\t\t" + args[i]);
			}
			System.out.println("\r\rdeclaringClass:"
				+ method.getDeclaringClass());
			return null;
		    }
		});
	System.out.println(object);
    }
} // /:~
