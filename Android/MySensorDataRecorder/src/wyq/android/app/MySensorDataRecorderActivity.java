package wyq.android.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import wyq.android.app.MySensorDataRecorderService.MyBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MySensorDataRecorderActivity extends Activity {
    Button button1;
    Button button2;
    TextView textView1;
    MySensorDataRecorderService myService;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	button1 = (Button) findViewById(R.id.button1);
	button2 = (Button) findViewById(R.id.button2);
	textView1 = (TextView) findViewById(R.id.textView1);
	textView1.setText(readFile());
	bindService(new Intent(this, MySensorDataRecorderService.class),
		mySvcConn, BIND_NOT_FOREGROUND);
	if (myService != null && myService.isStarted()) {
	    button1.setText("Stop");
	} else {
	    button1.setText("Start");
	}
	button2.setText("Refresh");
    }

    public void onButton1Clicked(View view) {
	String status = button1.getText().toString();
	if ("Start".equals(status)) {
	    startService(new Intent(this, MySensorDataRecorderService.class));
	    button1.setText("Stop");
	} else {
	    stopService(new Intent(this, MySensorDataRecorderService.class));
	    button1.setText("Start");
	}
    }

    public void onButton2Clicked(View view) {
	textView1.setText(readFile());
    }

    @Override
    protected void onResume() {
	super.onResume();
	textView1.setText(readFile());
    }

    @Override
    protected void onDestroy() {
	unbindService(mySvcConn);
	super.onDestroy();
    }

    private String readFile() {
	File dir = Environment.getExternalStoragePublicDirectory("");
	File[] listFiles = dir.listFiles(new FilenameFilter() {

	    @Override
	    public boolean accept(File dir, String filename) {
		if (filename.startsWith("wyq-log-")
			&& filename.endsWith(".txt"))
		    return true;
		else
		    return false;
	    }
	});
	StringBuilder sb = new StringBuilder();
	for (File f : listFiles) {
	    sb.append("=====File:[" + f.getName() + "]=Start====");
	    sb.append(System.getProperty("line.separator"));
	    sb.append(readFile(f));
	    sb.append("=====File:[" + f.getName() + "]===End====");
	    sb.append(System.getProperty("line.separator"));
	}
	return sb.toString();
    }

    private String readFile(File logFile) {
	StringBuilder sb = new StringBuilder();
	try {
	    FileReader isr = new FileReader(logFile);
	    BufferedReader reader = new BufferedReader(isr);
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line);
		sb.append(System.getProperty("line.separator"));
	    }
	    reader.close();
	    isr.close();
	    return sb.toString();
	} catch (FileNotFoundException e) {
	    toast(e.getMessage());
	} catch (IOException e) {
	    toast(e.getMessage());
	}
	return sb.toString();
    }

    private ServiceConnection mySvcConn = new ServiceConnection() {

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
	    myService = ((MyBinder) service).getService();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
	    myService = null;
	}
    };

    private void toast(Object txt) {
	Toast.makeText(this, txt.toString(), Toast.LENGTH_SHORT).show();
    }
}