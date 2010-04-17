import java.util.Random;

public class ThreadEntity extends Thread {

	private static final int MAX_AGE = 99;
	private static final Random random = new Random();
	private static int count = 0;
	private String name;
	private int age = 0;
	private final int life_dead_line;
	private final ResourceEntity resource;
	private final long lazy;

	public ThreadEntity(ResourceEntity res) {
		super();
		name = "Thread[" + (count++) + "]";
		life_dead_line = random.nextInt(MAX_AGE);
		resource = res;
		lazy = (long) (random.nextInt(10) + age) * 1000 + 1000;
		print("ThreadEntity: " + this.toString()
				+ " born and going to be started.");
		this.start();
	}

	@Override
	public void run() {
		while (age <= life_dead_line) {
			if (resource.getName() != name) {
				resource.setName(name);
				resource.setMsg("write at age:" + age);
				resource.print();
				age++;
			}
			try {
				super.sleep(lazy);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		print("ThreadEntity: " + this.toString() + " is going to dead.");
	}

	@Override
	public String toString() {
		return name + ":" + age + "[" + life_dead_line + "," + lazy + "]";
	}

	private static void print(Object o) {
		System.out.println(o);
	}
}
