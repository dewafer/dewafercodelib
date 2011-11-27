package wyq.android;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MySensorsActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
	LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	for (Sensor sensor : sensors) {
	    TextView tv = new TextView(this);
	    tv.setTag(sensor);
	    layout.addView(tv);
	    fillTxt(sensor, new float[0]);
	}
	TextView tv = (TextView) findViewById(R.id.text);
	tv.setText("Sensor count:" + sensors.size());
    }

    @Override
    protected void onResume() {
	super.onResume();
	List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
	for (Sensor sensor : sensors) {
	    mSensorManager.registerListener(this, sensor,
		    SensorManager.SENSOR_DELAY_UI);
	}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
	fillTxt(event.sensor, event.values);
    }

    private void fillTxt(Sensor sensor, float[] values) {
	LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	TextView tv = (TextView) layout.findViewWithTag(sensor);
	StringBuilder sb = new StringBuilder();
	sb.append("Name:" + sensor.getName());
	sb.append(System.getProperty("line.separator"));
	sb.append("Type:" + sensor.getType());
	sb.append(System.getProperty("line.separator"));
	for (int i = 0; i < values.length; i++) {
	    sb.append("v[" + i + "]:");
	    sb.append(values[i]);
	}
	tv.setText(sb.toString());
    }
}