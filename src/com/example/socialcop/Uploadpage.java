package com.example.socialcop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
public class Uploadpage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadpage_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uploadpage, menu);
		return true;
	}
	
}
