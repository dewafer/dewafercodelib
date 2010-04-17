public class ResourceEntity {

	private String name;
	private String msg;

	private void print_before() {
		// System.out.println("before:");
		// System.out.println(name);
		// System.out.println(msg);
	}

	private void print_after() {
		// System.out.println("after:");
		// System.out.println(name);
		// System.out.println(msg);
	}

	public synchronized void print() {
		println(name);
		println(msg);
		println("=====================");
	}

	/**
	 * @return the res
	 */
	public synchronized String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the res to set
	 */
	public synchronized void setName(String name) {
		print_before();
		this.name = name;
		print_after();
	}

	/**
	 * @return the msg
	 */
	public synchronized String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public synchronized void setMsg(String msg) {
		print_before();
		this.msg = msg;
		print_after();
	}

	private void println(Object o) {
		System.out.println(o);
	}

}
