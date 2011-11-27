package wyq.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    private Thread runner;

    public class MyBinder extends Binder {
	public MyService getService() {
	    return MyService.this;
	}
    }

    @Override
    public IBinder onBind(Intent intent) {
	return mBinder;
    }

    @Override
    public void onCreate() {
	toast("onCreate()");
	runner = new Thread() {
	    @Override
	    public void run() {
		// do something...
	    }
	};
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	toast("onStartCommand(" + intent + "," + flags + "," + startId);
	runner.start();
	return START_STICKY;
    };

    @Override
    public void onDestroy() {
	toast("onDestroy()");
	// notify.cancel(myServiceId);
    };

    private final IBinder mBinder = new MyBinder();

    private void toast(Object txt) {
	Toast.makeText(MyService.this, txt.toString(), Toast.LENGTH_SHORT)
		.show();
    }

    private String getLog(String txt) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd hh:mm:ss:SSS");
	String now = format.format(Calendar.getInstance().getTime());
	return "[" + now + "]" + txt;
    }

}
