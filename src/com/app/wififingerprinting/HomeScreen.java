package com.app.wififingerprinting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends Activity {
	
	Button check_sensor, check_aps, button4;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        check_sensor = (Button)findViewById(R.id.button1);
        check_aps	 = (Button)findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);
        
        check_aps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.w("Inside MAin Acticvity", "**********Main Activity");
				Intent check_sensor_aps = new Intent("com.app.WifiFingerprint");
				startActivity(check_sensor_aps);
			}
		});
        
        check_sensor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent check_sensor_intent = new Intent("com.app.DisplaySensorActivity");
				startActivity(check_sensor_intent);
			}
		});
        
        	button4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent2 = new Intent("com.app.LoadJsonData");
				startActivity(intent2);
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
