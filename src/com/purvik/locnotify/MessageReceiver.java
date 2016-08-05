package com.purvik.locnotify;

import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
//import android.util.Log;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {
	

//	private static final String TAG = "MessageReceiver";
	LocationManager lm;
	LocationListener locationListener;
	Location location;	
	String senderTel, Address;
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if((intent.getAction()).equals("android.provider.Telephony.SMS_RECEIVED"))	
		{
				
			//---get the SMS message that was received---
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			String str = "";
			
			if (bundle != null)
			{				
				senderTel = "";
				
				//---retrieve the SMS message received---
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				
				for (int i=0; i<msgs.length; i++)
				{
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					
					if (i==0) 
					{
						//---get the sender address/phone number---
						senderTel = msgs[i].getOriginatingAddress();
					}
					//---get the message body---
					str += msgs[i].getMessageBody().toString();				
				}
			
				if (str.toLowerCase().startsWith("lost".toLowerCase()))
				{	
					//---abort the broadcast; SMS messages won’t be broadcasted---
					abortBroadcast();
					
		    	    //SmsManager.getDefault().sendTextMessage(senderTel, null, "Got the Message with "+ str, null, null);

					//Enable GPS if Disable
					//enableGPS();
					
					//---use the LocationManager class to obtain locations data---
					lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);					
					
					//get the location
					location =  lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					
					
					//CHECK IF LOCATION FETCHED
			    	if(location != null)
			    	{
			    		//GET THE LAT-LONG
			    		double latitude = location.getLatitude();
			    		double longitude = location.getLongitude();
			    		
			    		//CALL getFullAddress TO FETCH FULL ADDRESS INFORMATION ABOUT CURRENT LOCATION
			    		Address = getFullAddress(context,latitude,longitude);
			    		
			    		Address += "Link:-" + " http://maps.google.com/maps?q=" + location.getLatitude() + "," +location.getLongitude();
			    	
			    		//--sent Message containing Full Address Details to requester 
			    	    SmsManager.getDefault().sendTextMessage(senderTel, null, Address, null, null);
			    	    
			    	   // if partially want to break the message in to Parts
//			    	    SmsManager smsManager = SmsManager.getDefault();
	//		    	    ArrayList<String> parts = smsManager.divideMessage(Address); 
	//		    	    smsManager.sendMultipartTextMessage(senderTel, null, parts, null, null);
			    	    
			    	} else
			    	{
			    		Address = "";
			    	    SmsManager.getDefault().sendTextMessage(senderTel, null, "Could not Find the Locatoin", null, null);

			    	}
				}else{
					
				}
			}
		}else if(intent.getAction().equals("com.purivik.mannual")){
			
			Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
			
			
		}else{}
	}

	//Method to Enable The GPS if it is Disable
	private void enableGPS() {
		
		
		
	}
	//---Method to get Full Address Details from the Latitude and Longitude
	private String getFullAddress(Context context,double lati, double longi) {
		
		String fullAddress = "no address found";	

    	//TO GET LOCALE GEOCODER
		Geocoder gc = new Geocoder(context, Locale.getDefault());
		
		try { 
				//GET THE ADDRESS FOR  LAT-LONG
				List<Address> addresses = gc.getFromLocation(lati, longi, 4);
		
				//TO BUILD FINAL STRING WITH FULL ADDRESS
				StringBuilder sb = new StringBuilder();
				
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
						
						//APPEND ALL ITEMS FROM THE LIST ADDRESS TO ONE STRING 
						sb.append("\n"+address.getAddressLine(i));
					}	 
					//GET LOCALITY
					sb.append("\n"+address.getLocality());
					
					//GET POSTAL CODE
					sb.append("\n"+address.getPostalCode());
					
					//GET COUNTRY NAME
					sb.append(address.getCountryName());
			}
				
				fullAddress = sb.toString();
//				Log.i("ADDRESS VALUE:", fullAddress);
				
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//RETURN FULL ADDRESS DETAILS TO BACK METHOD
		return fullAddress;
		
	}

	private class MyLocationListener implements LocationListener
	{
			@Override
			public void onLocationChanged(Location loc) {
				
				
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
	}
}





		