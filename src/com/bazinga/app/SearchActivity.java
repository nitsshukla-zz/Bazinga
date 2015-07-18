package com.bazinga.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

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

public class SearchActivity extends ListActivity {
	String jobCode = "";
	static ArrayList<String[]> arr = new ArrayList<String[]>();
		/** Called when the activity is first created. */

	private final String USER_AGENT = "Mozilla/5.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			jobCode = extras.getString("jobCode");
		super.onCreate(savedInstanceState);
		setTitle("Bazinga");
		ListAdapter adapter = createAdapter();
		setListAdapter(adapter);

	}

	public ListAdapter createAdapter() {
		String[] arr1 = new String[arr.size()];
		for(int i=0;i<arr.size();i++)
			arr1[i] = arr.get(i)[0] + "\t" + arr.get(i)[1];
		ListAdapter adapter = new ArrayAdapter<Object>(this,
				android.R.layout.simple_list_item_1, arr1);
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
		String url = "http://192.168.1.245:8080/Bazinga1/action?mode=getMainUsers&primary_mobile="
				+ arr.get(position)[2];
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
					JSONObject arr1 = new JSONObject(out.toString());
					SearchActivity.arr.clear();
						 UserActivity.arr=new String[]{arr1.getString("name"),
								 							 arr1.getString("pay"),
								 							 arr1.getString("primary_mobile"),
								 							 arr1.getString("age"),
								 							 arr1.getString("pin"),
								 							 arr1.getString("job_type"),
								 							 arr1.getString("rating")};
					Intent intent = new Intent(SearchActivity.this, UserActivity.class);
					startActivity(intent);
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
	}
}
