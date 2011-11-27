package wyq.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import wyq.android.MyService.MyBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyServiceActivity extends Activity {
    private MyService myService;

    private ServiceConnection mySvcConn = new ServiceConnection() {

	@Override
	public void onServiceDisconnected(ComponentName name) {
	    myService = null;
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
	    myService = ((MyBinder) service).getService();
	}
    };
    TextView textView1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	// bind service
	bindService();
	textView1 = (TextView) findViewById(R.id.textView1);
	showSvcSts();
    }

    private void bindService() {
	bindService(new Intent(this, MyService.class), mySvcConn,
		BIND_NOT_FOREGROUND);
    }

    private void unbindService() {
	unbindService(mySvcConn);
    }

    @Override
    protected void onResume() {
	super.onResume();
	showSvcSts();
    }

    private void showSvcSts() {
	if (myService == null) {
	    textView1.setText(getLog("myService is null!"));
	} else {
	    textView1.setText(getLog(myService.toString()));
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	// unbind service
	unbindService();
    }

    public void startService(View view) {
	toast("startService(" + view + ")");
	startService(new Intent(this, MyService.class));
	showSvcSts();
    }

    public void stopService(View view) {
	toast("stopService(" + view + ")");
	stopService(new Intent(this, MyService.class));
	showSvcSts();
    }

    private void toast(Object txt) {
	Toast.makeText(this, txt.toString(), Toast.LENGTH_SHORT).show();
    }

    private String getLog(String txt) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd hh:mm:ss:SSS");
	String now = format.format(Calendar.getInstance().getTime());
	return "[" + now + "]" + txt;
    }
}