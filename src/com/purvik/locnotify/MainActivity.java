package com.purvik.locnotify;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	ToggleButton receiverToggle;
	TextView receiverDeails;
	Button toggleDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		receiverToggle = (ToggleButton) findViewById(R.id.receiverToggle);
		
		receiverToggle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (receiverToggle.isChecked()) {
					
					//Enable BroadCast Receiver
					PackageManager pm = MainActivity.this.getPackageManager();
					ComponentName componentName = new ComponentName(MainActivity.this, MessageReceiver.class);
					pm.setComponentEnabledSetting(componentName,
							PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
							PackageManager.DONT_KILL_APP);
					
					Toast.makeText(getApplicationContext(), "Receiver Enabled", Toast.LENGTH_SHORT).show();
				}else{
					//Disable BroadCast Receiver
					PackageManager pm = MainActivity.this.getPackageManager();
					ComponentName componentName = new ComponentName(MainActivity.this, MessageReceiver.class);
					pm.setComponentEnabledSetting(componentName, 
							PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
							PackageManager.DONT_KILL_APP);
					
					Toast.makeText(getApplicationContext(), "Receiver Disabled", Toast.LENGTH_SHORT).show();
				
					
				}
				
			}
		});
		
	}
	
	public void mannualClick(View v) {
		
		Intent i = new Intent("com.purivik.mannual");
		sendBroadcast(i);
	}
	
	public void showDetails(View v) {
		
/*		Dialog detailsDialog = new Dialog(getApplicationContext());
		detailsDialog.setTitle("Toggle Details");*/
		
		receiverDeails = (TextView)findViewById(R.id.tv_receiverDetails);
		toggleDetails = (Button)findViewById(R.id.btnToggleDetails);
		
		if ((toggleDetails.getText().toString()).equalsIgnoreCase("?")) {
			
			receiverDeails.setVisibility(View.VISIBLE);
			toggleDetails.setText("#");
			
		}else{
			receiverDeails.setVisibility(View.GONE);
			toggleDetails.setText("?");
		}
		
		//Toast.makeText(getApplicationContext(), "Details Dialog Need to implement", Toast.LENGTH_SHORT).show();
	
			
	}

}
