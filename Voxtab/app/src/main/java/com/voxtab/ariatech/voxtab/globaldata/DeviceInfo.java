package com.voxtab.ariatech.voxtab.globaldata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class DeviceInfo {

	private Context context = null;
	private String api_key = "";
	private String 	device_type = "";
	private String current_version = "";
	private String device_name = "";
	private String device_os = "";
	private String imei = "";

	private String language="";
	SharedPreferences settings = null;


	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	public DeviceInfo(){

	}
	public DeviceInfo(Context context){
		this.context = context;
		 settings = PreferenceManager
					.getDefaultSharedPreferences(context);
		 
		 
		 // Set Device information
		 try {
			 setApi_key();
			 setDevice_type();
			 setCurrent_version();
			 setDevice_name();
			 setDevice_os();
			 setImei();
			 setLanguage(GlobalData.LANGUAGE);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	
	
	
	
	public String getApi_key() {
		return api_key;
	}

	public void setApi_key() {
		
		try {
			this.api_key = settings.getString("regID", "0");			
		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}
		
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type() {
		this.device_type = GlobalData.deviceType;
	}

	public String getCurrent_version() {
		return current_version;
	}

	public void setCurrent_version() {
		
//		String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//		OR

		try {
			int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//			String versionName =  context.getPackageManager().getPackageInfo( context.getPackageName(), 0).versionName;
			this.current_version = ""+versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name() {
		try {
			this.device_name = getDeviceName();
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	public String getDevice_os() {
		return device_os;
	}

	public void setDevice_os() {
		this.device_os = getDeviceOS();
	}

	public String getImei() {
		
		
		return imei;
	}

	public void setImei() {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String uniqueid = telephonyManager.getDeviceId()
					.toString();
			this.imei = uniqueid;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	
	///Global Methods
	public static String getDeviceName() {
		 
		 try {
			  String manufacturer = Build.MANUFACTURER;
			  String model = Build.MODEL;
			int version=  Build.VERSION.SDK_INT;
			  
			  if (model.startsWith(manufacturer)) {
			    return capitalize(model);
			  } else {
			    return capitalize(manufacturer) + " " + model;
			  }
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		 return "";
		
		}
	 public static String getDeviceOS() {
		 String os = "";
		 try {
			 
			int version=  Build.VERSION.SDK_INT;
			os = ""+version;
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		 return os;
		
		}
	
	 private static String capitalize(String s) {
		  if (s == null || s.length() == 0) {
		    return "";
		  }
		  char first = s.charAt(0);
		  if (Character.isUpperCase(first)) {
		    return s;
		  } else {
		    return Character.toUpperCase(first) + s.substring(1);
		  }
		}
	public static String getCoutrySIMISO(Context context){

		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSimCountryIso();
	}
}
