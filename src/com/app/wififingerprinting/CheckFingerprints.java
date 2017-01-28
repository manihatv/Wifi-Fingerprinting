package com.app.wififingerprinting;

import java.io.File;
import static java.lang.Thread.sleep;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckFingerprints extends Activity implements OnClickListener {
	public WifiManager myWifiMgr;
	public BroadcastReceiver myreceiver;
	BroadcastReceiver broadcastReceiver;
	public TextView txt;
	public Button btn_refresh;
	public Button btn_save;
	List<ScanResult> results = null;
	public Button btn_loc1;
	public Button btn_loc2;
	public Button btn_loc3;
	public Localize ljd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifistatus);

		txt = (TextView) findViewById(R.id.selection);
		btn_refresh = (Button) findViewById(R.id.refresh);
		btn_save = (Button) findViewById(R.id.save);

		btn_loc1 = (Button) findViewById(R.id.button_loc1);
		btn_loc2 = (Button) findViewById(R.id.button_loc2);
		btn_loc3 = (Button) findViewById(R.id.button_loc3);

		btn_refresh.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_loc1.setOnClickListener(this);
		btn_loc2.setOnClickListener(this);
		btn_loc3.setOnClickListener(this);
		
		findFingerprints();
		
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(myreceiver = new BroadcastReceiver() 
		{
			@Override
			public void onReceive(Context context, Intent intent) 
			{
			 	findFingerprints();
			}
        }, intentFilter);
		
		new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        sleep(5000);
                        if(myWifiMgr.isWifiEnabled())
                        	myWifiMgr.startScan();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (myreceiver == null) 
		{
			myreceiver = new WifiScaner(this);
			registerReceiver(myreceiver, new IntentFilter(
			WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		}
	}

	private void findFingerprints() 
	{
		Calendar c = Calendar.getInstance();
		c.get(Calendar.SECOND);
		myWifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		myWifiMgr.getConnectionInfo();

		results = myWifiMgr.getScanResults();
		txt.clearComposingText();
		txt.setText("\n Available Wifi APs: " + results.size()+"\n");
		for (ScanResult result : results) 
		{
			txt.append("\n SSID 	: " + result.SSID);
			txt.append("\n BSSID	: " + result.BSSID);
			txt.append("\n RSSI 	: " + result.level);
			txt.append("\n\n");
		}
	}

	private void writeFile(List<ScanResult> results, String fileName) {
		try {
			File file;
			File newFolder = new File(
					Environment.getExternalStorageDirectory(), "MobileSensing");
			if (!newFolder.exists()) {
				newFolder.mkdir();
			}
			List<ScanResult> results1 = myWifiMgr.getScanResults();

			if(fileName == null)
			{
				file = new File(newFolder, "sample.csv");
			}
			else
			{
				file = new File(newFolder, fileName+".csv");
			}
			
			FileWriter writer = new FileWriter(file);
			StringBuilder strBuilder = new StringBuilder();

			for (ScanResult result : results1) {
				System.out.println("********" + result.BSSID + ","
						+ result.SSID + "," + result.level + "\n");
				strBuilder.append(result.BSSID + "," + result.SSID + ","
						+ result.level + "\n");

				writer.write(strBuilder.toString());
			}
			writer.close();

		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	@Override
	public void onClick(View v) 
	{
		if (v.getId() == R.id.refresh) 
		{
			myWifiMgr.startScan();
			//writeFile(results, fileName);
		}
		else if (v.getId() == R.id.save) 
		{
			//findFingerprints(true,null);
			writeFile(results, "Random");
		}
		else if (v.getId() == R.id.button_loc1) 
		{
			writeFile(results, "Location1");
		}
		else if (v.getId() == R.id.button_loc2) 
		{
			writeFile(results, "Location2");
		}
		else if (v.getId() == R.id.button_loc3) 
		{
			writeFile(results, "Location3");
		}
	}

	@Override
	protected void onStop() {
		unregisterReceiver(myreceiver);
		super.onStop();
	}

}
