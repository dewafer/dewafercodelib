package i18n;
import java.util.*;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceBundle bundle = ResourceBundle.getBundle("nice",Locale.getDefault());
		
		System.out.format(bundle.getString("let.me.tell.you"),"this is {0}","1","2");
		
	}

}
