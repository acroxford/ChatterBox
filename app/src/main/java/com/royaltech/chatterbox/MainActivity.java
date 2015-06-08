package com.royaltech.chatterbox;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "SrplttGTWim2KQloXEObyUCJZ2lGN6JQXXYrH2nm", "IylG1gO7wfnFy2zZjXYJcrXyFxqWHNVWQrCP2MAL");
        double longitude = 0;
        double latitude = 0;
        //Credit: http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
        //GPSTracker gps = new GPSTracker(this);
          //  if(gps.canGetLocation()){
            //    latitude = gps.getLatitude();
              //  longitude = gps.getLongitude();
           // }
           // else{
             //   gps.showSettingsAlert();
            //}
        // end
        ParseObject Location = new ParseObject("Location");
        Location.put("Latitude", latitude);
        Location.put("longitude", longitude);
        Location.saveInBackground();

        setupCreateButton();
		
	}
	
	private void setupCreateButton()
    {
        Button Create = (Button)findViewById(R.id.createBox);
        Create.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MainActivity.this,CreateActivity.class));
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
