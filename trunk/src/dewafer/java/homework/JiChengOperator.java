package dewafer.java.homework;
/**
 * 
 */

class Base{
	public void Function(){
		System.out.println("baseFunction()");
	}
	public String OverrideredFunction(){
		return "OverrideredFunction()";
	}
}

class Inherit extends Base{
	@Override public void Function(){
		System.out.println("inheritFunction()");
		super.Function();
	}
	//@Override public Intager OverrideredFunction(){
	//	return new Integer(10);
	//}
}
/**
 * @author AIR
 *
 */
public class JiChengOperator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Base a = new Inherit();
		a.Function();

	}

}
