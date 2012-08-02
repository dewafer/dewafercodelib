package wyq.other.tool;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import other.tool.Compiler;
import other.tool.Runner;
import wyq.appengine.component.file.TextFile;
import wyq.appengine.component.template.TemplateEngine;

public class Script {

	public void script() {
		try {
//			String scriptClassPath = this.getClass()
//					.getResource(this.getClass().getSimpleName() + ".class")
//					.getFile();
//			String classFolder = new File(scriptClassPath).getParent();
//			TextFile outputJavaScriptFile = new TextFile(classFolder,
//					"JavaScript.java");

			String script = "System.out.println(\"This is a test\");";
			String template = TemplateEngine.get()
					.getTemplate(ScriptTemplate.class).defaultTemplate(script);

//			if (!outputJavaScriptFile.exists()) {
//				outputJavaScriptFile.createNewFile();
//			}
//
//			outputJavaScriptFile.writeAll(template, false);

			Compiler c = new Compiler();

//			c.compileFile(outputJavaScriptFile.getPath(), "bin/");
			c.compileJava("wyq.other.tool.JavaScript", template);
			Class<?> class1 = Class.forName("wyq.other.tool.JavaScript");
			Object newInstance = class1.newInstance();
			Method method = class1.getMethod("main");
			method.invoke(newInstance);

//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runner.run(Script.class);
	}

}
