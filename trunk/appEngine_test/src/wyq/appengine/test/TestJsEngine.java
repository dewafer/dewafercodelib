package wyq.appengine.test;

import java.io.File;

import wyq.appengine.component.javascript.JsEngine;

public class TestJsEngine {

	public static void main(String[] args) {
		TestJsInterface face = JsEngine.get().getJsDelegate(
				TestJsInterface.class);
		face.test();
		face.test2();
		face.test3();
		System.out.println(face.f("TestJsInterface"));
		System.out.println(face.f(new File("bin/TestJsInterface.js")));
		
		TestJsInterface2 face2 = JsEngine.get().getJsDelegate(TestJsInterface2.class);
		face2.test();
		face2.test2();
		face2.test3();
		System.out.println(face2.f("TestJsInterface2"));
		System.out.println(face2.f(new File("bin/wyq/appengine/test/TestJsInterface2.js")));
	}

}
