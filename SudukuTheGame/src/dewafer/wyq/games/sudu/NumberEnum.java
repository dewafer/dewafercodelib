/**
 * 
 */
package dewafer.wyq.games.sudu;

/**
 * @author dewafer
 * 
 */
public enum NumberEnum {

	EMPTY(0), NUMBER_1(1), NUMBER_2(2), NUMBER_3(3), NUMBER_4(4), NUMBER_5(5), NUMBER_6(
			6), NUMBER_7(7), NUMBER_8(8), NUMBER_9(9);
	private int value;

	NumberEnum(int i) {
		this.value = i;
	}

	public int value() {
		return value;
	}
}
