package wyq.infrastructure;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Load jar files or class files under the classpath
 * 
 * @version 0.1 2011/11/25
 * @author dewafer
 * 
 */
public class SmartExtClasspathLoader {
    public interface ClassLoaderFilter {
	public abstract boolean accept(String packageName, String className,
		boolean isInJarFile, String jarFileName);

	public abstract boolean accept(Class<?> clazz);
    }

    /** URLClassLoader的addURL方法 */
    private static Method addURL = initAddMethod();

    private static URLClassLoader classloader = (URLClassLoader) ClassLoader
	    .getSystemClassLoader();

    /**
     * 初始化addUrl 方法.
     * 
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
	try {
	    Method add = URLClassLoader.class.getDeclaredMethod("addURL",
		    new Class[] { URL.class });
	    add.setAccessible(true);
	    return add;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * 通过filepath加载文件到classpath。
     * 
     * @param filePath
     *            文件路径
     * @return URL
     * @throws Exception
     *             异常
     */
    private static void addURL(File file) {
	try {
	    addURL.invoke(classloader, new Object[] { file.toURI().toURL() });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static List<File> getSubClassFile(File dir) {
	List<File> classFilesLst = new ArrayList<File>();
	File[] classFiles = dir.listFiles(new FileFilter() {

	    @Override
	    public boolean accept(File arg0) {
		return arg0.getName().endsWith(".class");
	    }
	});
	if (classFiles != null && classFiles.length > 0) {
	    Collections.addAll(classFilesLst, classFiles);
	}
	File[] subdirs = dir.listFiles(new FileFilter() {

	    @Override
	    public boolean accept(File pathname) {
		return pathname.isDirectory();
	    }
	});
	for (File sub : subdirs) {
	    classFilesLst.addAll(getSubClassFile(sub));
	}
	return classFilesLst;
    }

    private static List<File> getSubJarFile(File f) {
	List<File> jarFiles = new ArrayList<File>();
	File[] files = f.listFiles();
	for (File subf : files) {
	    if (subf.isDirectory()) {
		jarFiles.addAll(getSubJarFile(subf));
	    } else if (isJarFile(subf)) {
		jarFiles.add(subf);
	    }
	}
	return jarFiles;
    }

    private static boolean isJarFile(File f) {
	return f.isFile()
		&& (f.getName().endsWith(".jar") || f.getName()
			.endsWith(".zip"));
    }

    private static Class<?> loadClass(Entry entry, ClassLoaderFilter filter) {
	Class<?> loadedClass = null;
	String packageName = entry.packageName;
	String className = entry.className;
	if (filter.accept(packageName, className, entry.isInJar,
		entry.jarFileName)) {
	    try {
		String fullName;
		if (packageName.length() > 0) {
		    fullName = packageName + "." + className;
		} else {
		    fullName = className;
		}
		loadedClass = classloader.loadClass(fullName);
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    } catch (NoClassDefFoundError e) {
		e.printStackTrace();
	    }
	}
	return loadedClass;
    }

    private static Set<Entry> getClassNamesInJar(File jarFile) {
	// 第一个class类的集合
	Set<Entry> classes = new LinkedHashSet<Entry>();
	JarFile jar;
	String packageName = "";
	boolean recursive = true;
	try {
	    // 获取jar
	    jar = new JarFile(jarFile);
	    // 从此jar包 得到一个枚举类
	    Enumeration<JarEntry> entries = jar.entries();
	    // 同样的进行循环迭代
	    while (entries.hasMoreElements()) {
		// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
		JarEntry entry = entries.nextElement();
		String name = entry.getName();
		// 如果是以/开头的
		if (name.charAt(0) == '/') {
		    // 获取后面的字符串
		    name = name.substring(1);
		}
		int idx = name.lastIndexOf('/');
		// 如果以"/"结尾 是一个包
		if (idx != -1) {
		    // 获取包名 把"/"替换成"."
		    packageName = name.substring(0, idx).replace('/', '.');
		}
		// 如果可以迭代下去 并且是一个包
		if ((idx != -1) || recursive) {
		    // 如果是一个.class文件 而且不是目录
		    if (name.endsWith(".class") && !entry.isDirectory()) {
			// 去掉后面的".class" 获取真正的类名
			String className = name.substring(
				packageName.length() + 1, name.length() - 6);
			Entry e = new Entry(packageName, className, true,
				jar.getName());
			classes.add(e);
		    }
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return classes;
    }

    public static Set<Class<?>> loadClasspath(String path,
	    ClassLoaderFilter filter) {
	File file = new File(path);
	Set<Class<?>> clzzSet = new LinkedHashSet<Class<?>>();
	Set<Entry> clzzNameSet = new LinkedHashSet<Entry>();
	if (file.isDirectory()) {
	    List<File> classFile = getSubClassFile(file);
	    if (classFile.size() > 0) {
		addURL(file);
		String classPath = file.getAbsolutePath();
		String separator = System.getProperty("file.separator");
		for (File clzzFile : classFile) {
		    String fullName = clzzFile.getAbsolutePath();
		    String clsFileName = clzzFile.getName();
		    String clsName = clsFileName.substring(0,
			    clsFileName.length() - 6);
		    int startPos = classPath.length() + 1;
		    int endPost = fullName.length() - clsFileName.length() - 1;
		    String packageName = (startPos < endPost) ? fullName
			    .substring(startPos, endPost) : "";
		    packageName = packageName.replace(separator, ".");
		    Entry entry = new Entry(packageName, clsName, false, null);
		    clzzNameSet.add(entry);

		}
	    }
	    List<File> jarFiles = getSubJarFile(file);
	    for (File jarFile : jarFiles) {
		addURL(jarFile);
		clzzNameSet.addAll(getClassNamesInJar(jarFile));
	    }
	} else if (isJarFile(file)) {
	    addURL(file);
	    clzzNameSet.addAll(getClassNamesInJar(file));
	}
	// lazy load
	for (Entry clzzName : clzzNameSet) {
	    if (filter.accept(clzzName.packageName, clzzName.className,
		    clzzName.isInJar, clzzName.jarFileName)) {
		System.out.println(clzzName.toString());
		Class<?> loadedClass = null;
		loadedClass = loadClass(clzzName, filter);
		if (loadedClass != null && filter.accept(loadedClass)) {
		    clzzSet.add(loadedClass);
		}
	    }
	}
	return clzzSet;
    }

    public static Set<Class<?>> loadClasspath(String path) {
	return loadClasspath(path, new ClassLoaderFilter() {

	    @Override
	    public boolean accept(String packageName, String className,
		    boolean isInJarFile, String jarFileName) {
		// accept all
		return true;
	    }

	    @Override
	    public boolean accept(Class<?> clazz) {
		// accept all
		return true;
	    }
	});
    }

    public static void main(String[] args) {
	Set<Class<?>> clzs = loadClasspath("C:\\test");
	for (Class<?> clz : clzs) {
	    System.out.println(clz);
	}
    }

    private static class Entry {
	private String packageName;
	private String className;
	private boolean isInJar;
	private String jarFileName;

	public Entry(String packageName, String className, boolean isInJar,
		String jarFileName) {
	    this.packageName = packageName;
	    this.className = className;
	    this.isInJar = isInJar;
	    this.jarFileName = jarFileName;
	}

	public String getFullName() {
	    String fullName;
	    if (packageName != null && packageName.length() > 0) {
		fullName = packageName + "." + className;
	    } else {
		fullName = className;
	    }
	    return fullName;
	}

	@Override
	public String toString() {
	    return getFullName()
		    + (isInJar ? "[inJarFile:" + jarFileName + "]" : "");
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof Entry) {
		String fullName = getFullName();
		if (fullName != null) {
		    Entry e = (Entry) obj;
		    return fullName.equals(e.getFullName())
			    && isInJar == e.isInJar;
		} else {
		    return false;
		}
	    } else {
		return false;
	    }

	}
    }
}
