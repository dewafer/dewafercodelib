package generics.thread;
import java.util.concurrent.*;

public class SimpleDaemons implements Runnable{

	private static void print(Object obj)
	{
		System.out.println(obj);
	}
	public void run(){
		try {
			while(true){
				TimeUnit.MICROSECONDS.sleep(100);
				print(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 * @throws Exception TODO
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		for(int i=0;i<10;i++){
			Thread daemon = new Thread(new SimpleDaemons());
			daemon.setDaemon(true);
			daemon.start();
		}
		print("ALL daemons started");
		TimeUnit.MICROSECONDS.sleep(175);
	}

}
