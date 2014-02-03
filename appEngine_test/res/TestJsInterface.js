function test() {
	println("This is TestJsInterface.test function.");
}
function test2() {
	println("This is TestJsInterface.test2 function.");
}
function test3(test) {
	println("This TestJsInterface.is test3 function."+ test);
}
function f(name) {
	println("This is TestJsInterface.f:" + name);
	if (name.isFile != null) {
		println("isFile?" + name.isFile());
	}else{
		println("not File");
	}
	return "f_reslut";
}