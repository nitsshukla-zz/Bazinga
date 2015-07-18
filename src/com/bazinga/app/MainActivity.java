package com.bazinga.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	String[] layoutList = null /*{ "Home Maintenance", "Labour",
			"Plumber", "Pest Control"}*/;
	static ArrayList<String> arr = new ArrayList<String>();
	static{Scanner scan =null;
		try{scan = new Scanner(new String("Laundry:LNDY\n" + 
				"Plumber:PLMB\n" + 
				"Electrician:ELEC\n" + 
				"Carpenter:CRPN\n" + 
				"Pet Care:PETC\n" + 
				"Pest Control:PSTC\n" + 
				"Car Wash:CARW\n" + 
				"Computer Service:CMPS\n" + 
				"Puja:PUJA\n" + 
				"Cakes:CAKE\n" + 
				"Cook:COOK\n" + 
				"Driving School:DRVS\n" + 
				"Tax Consultants:TXCS\n" + 
				"Notary & Agreements:NTRY\n" + 
				"Driver:DRVR\n" + 
				"Home Cleaning Services:HEPR\n" + 
				"Painting Services:PANT\n" + 
				"Rent Car:RCAR\n" + 
				"Maid:MAID\n" + 
				"Other:OTHR")); 
		while(scan.hasNext()){
			String line = scan.nextLine();
			String[] ar = line.split(":");
			arr.add(ar[0]);
			System.out.println(ar[0]);
		}scan.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{

		}
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		setTitle("Bazinga");
		ListAdapter adapter = createAdapter();
		setListAdapter(adapter);
	}

	private ListAdapter createAdapter() {		
		ListAdapter adapter = new ArrayAdapter<Object>(this,
				android.R.layout.simple_list_item_1, arr.toArray());
		return adapter;
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
///		try {			Thread.sleep(2000);		} catch (InterruptedException e) {e.printStackTrace();}
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    ActionBar actionBar = getActionBar();
	    actionBar.setIcon(R.drawable.bazinga_title);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
			Toast.makeText(this, arr.get(position)+" Opening.", Toast.LENGTH_LONG).show();
		
	}
}