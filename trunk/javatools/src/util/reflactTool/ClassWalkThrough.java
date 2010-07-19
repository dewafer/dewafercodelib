package util.reflactTool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClassWalkThrough {

    private Class<?>      clazz;

    private Set<Class<?>> walked = new HashSet<Class<?>>();

    private Set<Class<?>> childs = new HashSet<Class<?>>();

    private ClassWalkThrough() {}

    public ClassWalkThrough(Class<?> pClazz) {
        this.clazz = pClazz;
        walkFields(clazz);
    }

    public static ClassWalkThrough walkThrough(Class<?> clazz) {
        return new ClassWalkThrough(clazz);
    }

    private void walkFields(Class<?> root) {
        println("class:" + root.getCanonicalName());
        for (Field p : root.getDeclaredFields()) {
//            println("Field:" + p);
            childs.add(p.getType());
        }

        for (Method m : root.getDeclaredMethods()) {
//            println("Method:" + m);
            childs.add(m.getReturnType());
            Collections.addAll(childs, m.getParameterTypes());
            for (Annotation an : m.getAnnotations()) {
                Collections.addAll(childs, an.annotationType());
            }
        }

        for (Class<?> dc : root.getDeclaredClasses()) {
//            println("Declared classes:" + dc);
            childs.add(dc);
        }

        println("================================================================");

        for (Class<?> child : childs) {
            if (!walked.contains(child)) {
                walked.add(child);
                walkFields(child);
            }
        }
    }

    private void println(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        ClassWalkThrough.walkThrough(ClassWalkThrough.class);
    }
}
