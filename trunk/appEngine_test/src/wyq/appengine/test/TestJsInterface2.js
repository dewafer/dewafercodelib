function test() {
	println("This is TestJsInterface2.test function.");
}
function test2() {
	println("This is TestJsInterface2.test2 function.");
}
function test3(test) {
	println("This is TestJsInterface2.test3 function."+ test);
}
function f(name) {
	println("This is TestJsInterface2.f:" + name);
	if (name.isFile != null) {
		println("isFile?" + name.isFile());
	}else{
		println("not File");
	}
	return "f_reslut";
}