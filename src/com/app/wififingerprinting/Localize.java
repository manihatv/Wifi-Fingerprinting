package com.app.wififingerprinting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemSelectedListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Localize extends Activity implements OnItemSelectedListener 
{
	public Map<String, Double> map_a112;
	public Map<String, Double> map_a118;
	public Map<String, Double> map_a124;
	public Map<String, Double> map_a128;
	public Map<String, Double> map_a136;
	public Map<String, Double> map_a141;
	public Map<String, Double> map_livingroom;
	public Map<String, Double> map_kitchen;
	public Map<String, Double> map_hall;
	public Map<String, Double> map_measure;
	String line = "";
	BufferedReader br;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Button measure1 = (Button) findViewById(R.id.measure1);
		Button measure2 = (Button) findViewById(R.id.measure2);
		Button measure3 = (Button) findViewById(R.id.measure3);
		Button measure4 = (Button) findViewById(R.id.measure4);
		Button self_measure3 = (Button) findViewById(R.id.measure_self);

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsondata);
	}
	
	public void viewresult(View view) {
		if (view.getId() == R.id.measure1)
		{
			Log.d("Mobile Sensing", "******** Detect Room for Measurement 1");
			findroom(map_measure,"measure_1.csv");
		}
		else if(view.getId() == R.id.measure2)
		{
			Log.d("Mobile Sensing", "******** Detect Room for Measurement 2");
			findroom(map_measure,"measure_2.csv");
		}
		else if(view.getId() == R.id.measure3)
		{
			Log.d("Mobile Sensing", "******** Detect Room for Measurement 3");
			findroom(map_measure,"measure_3.csv");
		}
		else if(view.getId() == R.id.measure4)
		{
			Log.d("Mobile Sensing", "******** Detect Room for Measurement 3");
			findroom(map_measure,"SelfMeasure_141.csv");
		}
		else if(view.getId() == R.id.measure_self)
		{
			Log.d("Mobile Sensing", "******** Detect Room for Self Measurement");
			findroom_self(map_measure,"Random.csv");
		}
		
	 }
	private void findroom(Map<String, Double> map_measure, String filename) {
		
		
		double smallest;
		String room_num;
		double temp;
		
		TextView RoomNo = (TextView) findViewById(R.id.RoomNumber);
		//TextView SelfLocation = (TextView) findViewById(R.id.SelfLocation);
		RoomNo.setText("");
		BufferedReader br = null, br1=null;
		
		map_a112 = storeHash("ref_a112.csv");
		map_a118 = storeHash("ref_a118.csv");
		map_a124 = storeHash("ref_a124.csv");
		map_a128 = storeHash("ref_a128.csv");
		map_a136 = storeHash("ref_a136.csv");
		map_a141 = storeHash("ref_a141.csv");
		map_measure = storeHash(filename);
		
		Map<String, Double> map_distance = new HashMap<String, Double>();
		
		map_distance.put("a112", calculateD(map_measure,map_a112));
		map_distance.put("a118", calculateD(map_measure,map_a118));
		map_distance.put("a124", calculateD(map_measure,map_a124));
		map_distance.put("a128", calculateD(map_measure,map_a128));
		map_distance.put("a136", calculateD(map_measure,map_a136));
		map_distance.put("a141", calculateD(map_measure,map_a141));
		
		smallest = 99999;
		room_num = null;
		temp = 0;
		
		for (String k : map_distance.keySet())
		{
			temp = map_distance.get(k);
			System.out.println("Values:" + temp);
			if(smallest > temp){
				smallest = temp;
				room_num = k;
			}
				
		    
		}
		RoomNo.append(room_num);
		System.out.println("Smallest for " + room_num +":" +  smallest);
		
	}

private void findroom_self(Map<String, Double> map_measure, String filename) {
		
		
		double smallest;
		String room_num;
		double temp;
		
		TextView RoomNo = (TextView) findViewById(R.id.RoomNumber);
		//TextView SelfLocation = (TextView) findViewById(R.id.SelfLocation);
		RoomNo.setText("");
		BufferedReader br = null, br1=null;
		
		map_livingroom = storeHash("SelfReference1.csv");
		map_kitchen = storeHash("SelfReference2.csv");
		map_hall = storeHash("SelfReference3.csv");
		map_measure = storeHash(filename);
		
		Map<String, Double> map_distance = new HashMap<String, Double>();
		
		map_distance.put("Living Room", calculateD(map_measure,map_livingroom));
		map_distance.put("Kitchen", calculateD(map_measure,map_kitchen));
		map_distance.put("Hall", calculateD(map_measure,map_hall));
		
		smallest = 99999;
		room_num = null;
		temp = 0;
		
		for (String k : map_distance.keySet())
		{
			temp = map_distance.get(k);
			System.out.println("Values:" + temp);
			if(smallest > temp){
				smallest = temp;
				room_num = k;
			}
				
		    
		}
		RoomNo.append(room_num);
		System.out.println("Smallest for " + room_num +":" +  smallest);
		
	}

	private double calculateD(Map<String, Double> map_measure,Map<String, Double> map_reference) {
		ArrayList<String> tempArrayList = new ArrayList<String>(map_measure.keySet());
		double summation = 0;
		double temp;
		String str;
		
		for(int i=0;i<tempArrayList.size();i++)
		{
			str = tempArrayList.get(i); 
			if(map_reference.containsKey(str))
			{
				temp = (map_reference.get(str) -  map_measure.get(str));
				temp = temp * temp;
				summation += temp ;
				
			}
			/*else {
                   temp = map_measure.get(str) - (-100);
                   temp = temp * temp;
				   summation += temp;				
			}*/
		}
		temp = Math.sqrt(summation);
		return temp;
		
	}

	public Map<String, Double> storeHash(String mapname) 
	{
		Map<String, Double> map_room = new HashMap<String, Double>();
		try {
				String csvSplitBy = ",";
				br = new BufferedReader(new InputStreamReader(Localize.this.getAssets().open(mapname)));
				while ((line = br.readLine()) != null) 
				{
					String[] wifiDetails = line.split(csvSplitBy);
					//System.out.println("BSSID = " + wifiDetails[0]+ " , Signal Level=" + wifiDetails[2] + "]");
					double rssi = Double.valueOf(wifiDetails[2].trim()).doubleValue();
					System.out.println("BSSID = " + wifiDetails[0]+ " , Signal Level=" + rssi + "]");
					map_room.put(wifiDetails[0], rssi);
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				if (br != null) 
				{
					try 
					{
							br.close();
					} 
					catch (IOException e) 
					{
							e.printStackTrace();
					}
				}
			}
		return map_room;
	}
	
	@Override
	public void onItemSelected(AdapterViewCompat<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onNothingSelected(AdapterViewCompat<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
	