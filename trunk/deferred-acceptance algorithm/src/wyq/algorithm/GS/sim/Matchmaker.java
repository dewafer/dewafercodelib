package wyq.algorithm.GS.sim;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Matchmaker implements Runnable {

	private Random rand = new Random();
	private Executor exec;
	private TheGame world;

	public Matchmaker(Executor exec, TheGame world) {
		this.exec = exec;
		this.world = world;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Participator p = next();
				println("Matchmaker introducing freshman " + p
						+ " to everyone...");
				for (Participator other : world.getEveryone()) {
					if (!p.getClass().equals(other.getClass())) {
						other.meet(p);
						p.meet(other);
					}
				}
				world.getEveryone().add(p);
				exec.execute(p);
				TimeUnit.MILLISECONDS.sleep(250 + rand.nextInt(300));
			}
			println("Matchmaker End.");
		} catch (InterruptedException e) {
			println("Matchmaker interrupted.");
		}
	}

	private Class<?>[] classLst = { Boy.class, Girl.class };
	private int[] count = { 0, 0 };
	private boolean printLog = true;

	protected Participator next() {
		int index = rand.nextInt(classLst.length);
		Class<?> clz = classLst[index];
		Participator p = null;
		try {
			p = (Participator) clz.getConstructor(String.class).newInstance(
					(clz.getSimpleName() + count[index]++));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return p;
	}

	protected void println(Object o) {
		if (printLog)
			System.out.println(o);
	}

	protected void print(Object o) {
		if (printLog)
			System.out.print(o);
	}

}
