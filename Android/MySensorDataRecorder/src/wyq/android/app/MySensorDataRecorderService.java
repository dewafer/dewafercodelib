package wyq.android.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class MySensorDataRecorderService extends Service implements
	SensorEventListener {

    public class MyBinder extends Binder {
	public MySensorDataRecorderService getService() {
	    return MySensorDataRecorderService.this;
	}
    }

    private MyBinder mBinder = new MyBinder();
    private boolean isStarted = false;

    @Override
    public IBinder onBind(Intent arg0) {
	return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	open();
	if (writer != null) {
	    getSMng().registerListener(this, getSensor(),
		    SensorManager.SENSOR_DELAY_FASTEST);
	    isStarted = true;
	    toast("MySensorDataRecorderService started successfully.");
	} else {
	    this.stopSelf();
	    isStarted = false;
	    toast("MySensorDataRecorderService started failure.");
	}
	return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
	isStarted = false;
	close();
	getSMng().unregisterListener(this);
	toast("MySensorDataRecorderService destoried.");
	super.onDestroy();
    }

    private Sensor getSensor() {
	Sensor sor = getSMng().getDefaultSensor(Sensor.TYPE_LIGHT);
	return sor;
    }

    private SensorManager getSMng() {
	SensorManager sMng = (SensorManager) getSystemService(SENSOR_SERVICE);
	return sMng;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	// do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
	write(event.values);
    }

    private void write(float[] values) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd hh:mm:ss:SSS");
	String now = format.format(Calendar.getInstance().getTime());
	StringBuilder sb = new StringBuilder("[" + now + "]");
	for (int i = 0; i < values.length; i++) {
	    sb.append(values[i]);
	    sb.append(",");
	}
	sb.deleteCharAt(sb.length() - 1);
	sb.append(System.getProperty("line.separator"));
	try {
	    writer.write(sb.toString());
	    writer.flush();
	} catch (IOException e) {
	    toast(e.getMessage());
	}
    }

    private Writer writer = null;

    private void open() {
	File externalFilesDir = Environment
		.getExternalStoragePublicDirectory("");
	File logFile = new File(externalFilesDir, getLogFileName());
	if (!logFile.exists()) {
	    try {
		logFile.createNewFile();
	    } catch (IOException e) {
		toast(e.getMessage());
	    }
	}
	FileWriter osw = null;
	try {
	    osw = new FileWriter(logFile, true);
	} catch (IOException e) {
	    toast(e.getMessage());
	}
	writer = new BufferedWriter(osw);
    }

    private String getLogFileName() {
	SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
	Date now = Calendar.getInstance().getTime();
	String strNow = smt.format(now);
	String fileName = "wyq-log-" + strNow + ".txt";
	return fileName;
    }

    private void close() {
	if (writer == null)
	    return;
	try {
	    writer.flush();
	    writer.close();
	} catch (IOException e) {
	    toast(e.getMessage());
	}
    }

    public boolean isStarted() {
	return isStarted;
    }

    private void toast(Object txt) {
	Toast.makeText(this, txt.toString(), Toast.LENGTH_SHORT).show();
    }
}
