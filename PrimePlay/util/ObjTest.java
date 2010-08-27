package util;

public class ObjTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		Object pojo = new Object();
		/*
		 * you can't do this in java!
		pojo.Myname = "abc";
		pojo.number =999;
		*/
		pojo.wait(1000); 
	}

}
