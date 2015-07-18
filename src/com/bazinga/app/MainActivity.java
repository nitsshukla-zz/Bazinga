package com.bazinga.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	static List<String> layoutListId = new ArrayList<String>() /*{ "Home Maintenance", "Labour",
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
			layoutListId.add(ar[1]);
			
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
			//Intent intent = new Intent(this, SearchActivity.class);
			//intent.putExtra("jobCode", layoutListId.get(position));
			//startActivity(intent);
			try {
				getJSON(layoutListId.get(position));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void getJSON(String jobCode2) throws Exception {
		String url = "http://192.168.1.245:8080/Bazinga1/action?mode=getUsers&jobCode="
				+ jobCode2;
		RequestTask as = new RequestTask();
		as.execute(url);
	}

private class RequestTask extends AsyncTask<String, String, String> {
	public SearchActivity act = null;
	@Override
	protected String doInBackground(String... uri) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String responseString = null;
		try {
			response = httpclient.execute(new HttpGet(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				responseString = out.toString();
				System.out.println("outpppp: " + out.toString());
				JSONArray arr1 = new JSONArray(out.toString());
				SearchActivity.arr.clear();
				for(int i=0;i<arr1.length();i++)
					 SearchActivity.arr.add(new String[]{arr1.getJSONObject(i).getString("name"),
							 							 arr1.getJSONObject(i).getString("pay"),
							 							 arr1.getJSONObject(i).getString("primary_mobile")});
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
				ListAdapter adapter = act.createAdapter();
				act.setListAdapter(adapter);
				out.close();
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseString;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// Do anything with response..
	}
}
}

