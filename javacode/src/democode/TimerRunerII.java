package democode;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRunerII {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(), 1000, 1000);
		
//		do
//		{
//			System.out.println(new Date());
//		}while(true);
	}
	
	public static class MyTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(new Date());
		}

	}

}
