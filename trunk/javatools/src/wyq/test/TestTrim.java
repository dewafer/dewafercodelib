package wyq.test;

public class TestTrim {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub

	String tmp = " A b ";
	String tmp2 = null;
	Long tmp3 = 123L;
	println("before trim:" + tmp);
	println("after trim:" + trim(tmp));
	println("trim tmp2:" + trim(tmp2));
	println("trim tmp3:" + trim(tmp3));
    }
    
    public static void println(Object o){
	System.out.println(o);
    }
    
    private static Object trim(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return ((String)value).trim();
        } else {
            return value;
        }
    }

}
