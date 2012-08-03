import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Script1 {

	public void run() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int next = 0;
				while (next < 980) {
					Calendar instance = Calendar.getInstance();
					SimpleDateFormat format = new SimpleDateFormat(
							"HH:mm:SS sss");
					System.out.println(format.format(instance.getTime()));
					try {
						next = new Random().nextInt(1000);
						System.out.println(next);
						Thread.sleep(next);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).run();
	}
}
