package wyq.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FileWriterActivity extends Activity implements OnClickListener {
    TextView textView1 = null;
    TextView textView2 = null;
    EditText editText1 = null;
    Button button1 = null;
    String newLine = System.getProperty("line.separator");

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	button1 = (Button) findViewById(R.id.button1);
	button1.setOnClickListener(this);
	textView1 = (TextView) findViewById(R.id.textView1);
	textView1.setText(readFile());
	textView2 = (TextView) findViewById(R.id.textView2);
	editText1 = (EditText) findViewById(R.id.editText1);
    }

    @Override
    public void onClick(View v) {
	String msg = getLog(editText1.getText().toString());
	toast(msg);
	writeFile(msg);
	textView1.setText(readFile());
	File f = Environment.getExternalStoragePublicDirectory("");
	StringBuilder sb = new StringBuilder();
	sb.append("name:" + f.getName());
	sb.append(newLine);
	sb.append("path:" + f.getAbsoluteFile());
	sb.append(newLine);
	sb.append("space(free/all):" + f.getFreeSpace() + "/"
		+ f.getTotalSpace());
	textView2.setText(sb.toString());
    }

    private String readFile() {
	StringBuilder sb = new StringBuilder();
	try {
	    File externalFilesDir = Environment
		    .getExternalStoragePublicDirectory("");
	    File logFile = new File(externalFilesDir, "wyq-log.txt");
	    if (!logFile.exists()) {
		logFile.createNewFile();
	    }
	    FileReader isr = new FileReader(logFile);
	    BufferedReader reader = new BufferedReader(isr);
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line);
		sb.append(newLine);
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

    private void writeFile(String txt) {
	try {
	    File externalFilesDir = Environment
		    .getExternalStoragePublicDirectory("");
	    File logFile = new File(externalFilesDir, "wyq-log.txt");
	    if (!logFile.exists()) {
		logFile.createNewFile();
	    }
	    FileWriter osw = new FileWriter(logFile, true);
	    BufferedWriter writer = new BufferedWriter(osw);
	    writer.write(txt);
	    writer.newLine();
	    writer.flush();
	    writer.close();
	    osw.close();
	} catch (IOException e) {
	    toast(e.getMessage());
	}
    }

    private String getLog(String txt) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd hh:mm:ss:SSS");
	String now = format.format(Calendar.getInstance().getTime());
	return "[" + now + "]" + txt;
    }

    private void toast(Object txt) {
	Toast.makeText(this, txt.toString(), Toast.LENGTH_SHORT).show();
    }
}