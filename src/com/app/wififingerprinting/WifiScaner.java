package com.app.wififingerprinting;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WifiScaner extends BroadcastReceiver {

	CheckFingerprints wifiFingerprint;
	
	public WifiScaner(CheckFingerprints wifiFingerprint)
	{
		super();
		this.wifiFingerprint = wifiFingerprint;
	}
	

	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		List<ScanResult> results = wifiFingerprint.myWifiMgr.getScanResults();
		ScanResult bestsignal = null;
		for(ScanResult result: results)
		{
			if(bestsignal==null || WifiManager.compareSignalLevel(bestsignal.level, result.level)<0)
				bestsignal=result;
			
				
		}
	}

}
