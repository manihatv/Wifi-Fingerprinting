package com.app.wififingerprinting;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplaySensors extends Activity implements SensorEventListener{

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	private SensorManager mSensorManager;
	private TextView mTextView;

	public void onCreate(Bundle SecondInstance) {
		super.onCreate(SecondInstance);
		setContentView(R.layout.sensorlist);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mTextView = (TextView) findViewById(R.id.text_view);

		List<Sensor> mList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		for (int i = 1; i < mList.size(); i++) {
			mTextView.append("\n" + mList.get(i).getName());
			}
		Log.v("Display Sensor Activity", "Sensor Activity***********************");		
	}

}
