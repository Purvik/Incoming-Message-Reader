package com.purvik.locnotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		
		Thread splashThread = new Thread(){
			public void run() {
				
				try {
					
					//Keep Thread Sleep for 3 Seconds (3000 milliseconds)
					sleep(3000);
					
				} catch (InterruptedException e) {

					e.printStackTrace();
				}finally{
					
					
					//Start The Main Activity
					startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
					
					//Finish The SplashScreen Activity
					finish();
				}
				
			}
			
		};
		
		//Start The Thread
		splashThread.start();
	}
}
