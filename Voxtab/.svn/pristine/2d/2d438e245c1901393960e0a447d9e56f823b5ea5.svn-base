package com.ariatech.lib_project;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CommonUtil {

	/**
	 * This method device Full name into FirstName,middleName, lastName
	 * 
	 * @param name
	 *            - Full name Example Abhijeet Singh Rajput
	 * @return String array Exa arr[0] = "Abhijeet" arr[1] = "Rajput" and arr[2]
	 *         = "Singh"
	 */

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_DATABASE = "yyyy-MM-dd hh:mm:ss";
	public static final String DATE_FORMAT_DATABASE_SEND = "yyyy-MM-dd";
	public static final String DATE_FORMAT_VT = "hh:mm:ss yyyy-MM-dd";
	public static String[] getDivideFullName(String name) {
		name = name.trim();
		int index = name.lastIndexOf(' ');
		String fname = "", lname = "", mname = "";
		if (index > 0) {
			fname = name.substring(0, index);
			lname = name.substring(index + 1, name.length());
		}

		name = fname.trim();
		index = name.indexOf(' ');
		if (index > 0) {
			fname = name.substring(0, index);
			mname = name.substring(index + 1, name.length());
		}

		return new String[] { fname, lname, mname };
	}

	/**
	 * This method converts Date String from different format to dd/MM/yyy
	 * 
	 * @param dateStr
	 *            : this is date String which user want to convert
	 * @param formatStr
	 *            : this is format of that dateStr Ex: dd-MMM-yyyy hh:mm:ss
	 * @return it returns
	 */
	public static String convertToDateString(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd-MMM-yyyy");

		String newDateStr = null;
		Date date;
		try {
			if (dateStr == null) {
				date = new Date(System.currentTimeMillis());
			} else {
				date = sdf.parse(dateStr);
			}
			newDateStr = sdfTo.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			printError(e);
		}

		return newDateStr;
	}

	/**
	 * This method converts Date String from different format to dd/MM/yyy
	 * 
	 * @param dateStr
	 *            : this is date String which user want to convert
	 * @param formatStr
	 *            : this is format of that dateStr Ex: dd-MMM-yyyy hh:mm:ss
	 * @return it returns
	 */
	public static String convertToDBDate(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd");

		String newDateStr = null;
		Date date;
		try {
			if (dateStr == null) {
				date = new Date(System.currentTimeMillis());
			} else {
				date = sdf.parse(dateStr);
			}
			newDateStr = sdfTo.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			printError(e);
		}

		return newDateStr;
	}
	
	/**
	 * This method converts Date String from different format to dd/MM/yyy
	 * 
	 * @param dateStr
	 *            : this is date String which user want to convert
	 * @param formatStr
	 *            : this is format of that dateStr Ex: dd-MMM-yyyy hh:mm:ss
	 * @return it returns
	 */
	public static String convertStringDateFormat(String dateStr, String formatStr,String toFormatStr ) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		SimpleDateFormat sdfTo = new SimpleDateFormat(toFormatStr);

		String newDateStr = null;
		Date date;
		try {
			if (dateStr == null) {
				date = new Date(System.currentTimeMillis());
			} else {
				date = sdf.parse(dateStr);
			}
			newDateStr = sdfTo.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			printError(e);
		}

		return newDateStr;
	}

	/**
	 * Return String as per given format
	 * 
	 * @param date
	 *            Date object
	 * @param formatStr
	 *            return format dateStr Ex: dd-MMM-yyyy hh:mm:ss
	 * @return String date format
	 */
	public static String formatDate(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String newDateStr = sdf.format(date);
		return newDateStr;
	}

	public static Date getDateFromString(String strDate, String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try
		{
			 date = simpleDateFormat.parse(strDate);
		}catch (ParseException ex)
		{System.out.println("Exception "+ex);}
		return date;
	}

	public static String getFileNameFromURL(String url) {
		String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
		return fileName;
	}

	public static String getFormatURL(String url) {
		if (url != null) {
			url = url.replaceAll(" ", "%20");
		} else {
			return "";
		}
		return url;
	}

	public static void printError(Exception e) {
		e.printStackTrace();
	}

	public static void printMessage(String msg) {
		if (msg != null) {
			Log.d("Aria Lib", msg);
		}
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * This method shows custom toast which is showing some UI
	 * 
	 * @param context
	 *            is Context of activity
	 * @param msg
	 *            : message to show
	 * @param isError
	 *            : set 'true' when you want to show error, set 'false' when
	 *            want to show information
	 */

	public static void showCustomToast(Context context, String msg,
			boolean isError) {
		// Retrieve the layout inflator
		if (toastFlag) {
			return;
		}

		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_custom_layout,
				(ViewGroup) ((Activity) context)
						.findViewById(R.id.toast_layout_root));
		TextView text = (TextView) layout.findViewById(R.id.textToast);
		// text.setTypeface(typeface_obj);

		if (isError) {
			layout.setBackgroundResource(R.drawable.toast_red);
		} else {
			layout.setBackgroundResource(R.drawable.toast_green);
		}
		text.setText(msg);

		// Return the application context
		Toast toast = new Toast(context);
		// Set toast gravity to bottom
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		// Set toast duration
		toast.setDuration(Toast.LENGTH_SHORT);
		// Set the custom layout to Toast
		toast.setView(layout);
		// Display toast
		toast.show();
		toastFlag = true;
		h = new Handler();
		h.postDelayed(thread, 3000);
	}

	private static Handler h;
	public static boolean toastFlag;

	private static Runnable thread = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			toastFlag = false;
		}
	};

	public static boolean isNetworkAvailable(Context con) {

		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected;
		if (activeNetwork != null)
			isConnected = activeNetwork.isConnectedOrConnecting();
		else
			isConnected = false;

		return haveConnectedWifi || haveConnectedMobile || isConnected;
	}
	public static String getSharePreferenceString(Context context, String key,
												  String defaultValue) {
		SharedPreferences setting = PreferenceManager
				.getDefaultSharedPreferences(context);
		return setting.getString(key, defaultValue);
	}

	public static void setSharePreferenceString(Context context, String key,
												String value) {
		SharedPreferences setting = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = setting.edit();

		if (value.equals("")) {
			editor.remove(key);
		} else {
			editor.putString(key, value);
		}
		editor.commit();
	}

	public static String getEmailAccount(Context context){
		String email = null;

		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				email = account.name;
			}
		}

		return email;
	}

	public static String getIMEI(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = null;
		if (tm != null) {
			imei = tm.getDeviceId();
			if (imei == null || imei.length() == 0)
				imei = Build.SERIAL;
		}
		return imei;
		// return "359299057653516";
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}
	public static String capitalize(String s) {
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

	public static String getDeviseOS(){
		StringBuilder builder = new StringBuilder();
		builder.append("Android - ").append(Build.VERSION.RELEASE);

		Field[] fields = Build.VERSION_CODES.class.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			int fieldValue = -1;

			try {
				fieldValue = field.getInt(new Object());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			if (fieldValue == Build.VERSION.SDK_INT) {
				builder.append(" - ").append(fieldName);
			}
		}
return builder.toString();
	}

	public static void setEnable(View view, boolean flag){
		view.setClickable(flag);
		view.setEnabled(flag);
		view.setFocusableInTouchMode(flag);
		view.setFocusable(flag);
	}

}
