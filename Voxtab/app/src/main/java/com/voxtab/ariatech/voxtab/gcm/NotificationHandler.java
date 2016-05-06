package com.voxtab.ariatech.voxtab.gcm;

import java.util.Calendar;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.voxtab.ariatech.voxtab.OrderDetailsActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.SplashActivity;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GetMasterData;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

public class NotificationHandler {

	Context context;
	String message = "";

	String type = "";
	int order_id = 0;
	String assignment_no="";;
	String	msg ="";
	String title = "";
	Intent intent=new Intent();
	public NotificationHandler(Context context, String message) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.message = message;

	}

	public void setNotification() {

//		{
//			"order_id":"23",
//				"assignment_no":"CAPGEJ-23M",
//				"message":"Your Order No. UUUU+002B002B002B0024CAPGEJ-23MUUUU+002B002B002B0024 has been confirmed. Please upload the files within the defined time to avoid extension of deadlines.",
//				"title":"Order confirmed",
//				"user_id":"1"
//		}
		try {

			if (message.length() <= 0) {
				return;
			}

			JSONObject object = new JSONObject(message);
			try {
				type = (GlobalData.decryptData(object
						.getString("type")));

			}catch (Exception e){
				GlobalData.printError(e);
			}

			try {
				order_id = Integer.parseInt(GlobalData.decryptData(object
						.getString("order_id")));

			}catch (Exception e){
				GlobalData.printError(e);
			}
			try {
				assignment_no = (GlobalData.decryptData(object
						.getString("assignment_no")));

			}catch (Exception e){
				GlobalData.printError(e);
			}


			try {
				msg = (GlobalData.decryptData(object
						.getString("message")));

			}catch (Exception e){
				GlobalData.printError(e);
			}

			try {
				title = (GlobalData.decryptData(object
						.getString("title")));

			}catch (Exception e){
				GlobalData.printError(e);
			}


			// Check the User remember me functionality is login or Not

			try {

				if(GlobalData.getRemMekey(context)){
					GlobalData.logoutTask(context);
					return;
				}
			}catch (Exception e){
				GlobalData.printError(e);
			}




			// Add Notification Setting
			int notFlag= GlobalData.getNotificationkey(context);

			if(notFlag == 2){
				// Mute All notification

				return;

			}else  if(notFlag == 3){

				// Only Delivery Notifications

				if( type.equalsIgnoreCase("Delivered") || type.equalsIgnoreCase("deliveryinProgress") ){

				} else{
					return;
				}

			}


			// Deveice Not in Range for to display message



			intent=new Intent(context, SplashActivity.class);
			Bundle bundle=new Bundle();
			bundle.putInt("order_id",order_id);
			bundle.putString("assignment_no", assignment_no);
			intent.putExtras(bundle);


			switch (type) {
			case "Delivered":
				intent.putExtra("page_id", 2);;


				break;
			case "deliveryinProgress":
				intent.putExtra("page_id", 2);

				break;

			case "OrderConfirmed":

				intent.putExtra("page_id", 1);;
				break;



			default:


				try {

				intent.putExtra("page_id", 0);
				}catch (Exception e){
					GlobalData.printError(e);
				}
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}


		try{
		if(msg.length()>0 && title.length()>0){

			// Add into Db
			DatabaseHandlerNew db=new DatabaseHandlerNew(context);
			try {
				db.open();

				com.voxtab.ariatech.voxtab.bean.Notification notification=new com.voxtab.ariatech.voxtab.bean.Notification();

				notification.setNoti_type(""+type);
				notification.setNotifi_txt("" + msg);
				notification.setNoti_title("" + title);
				notification.setOrder_id(order_id);
				notification.setCreated_date("" + Calendar.getInstance().getTimeInMillis());
				notification.setAssignment_no("" + assignment_no);
				notification.setSoft_del("0");

				db.addNotification(notification);

			}catch (Exception e){
				GlobalData.printError(e);
			}finally {
				db.close();
			}

			generateNotification(context,assignment_no+" "+title,msg,intent);
		}

	}catch (Exception e){
		GlobalData.printError(e);
	}

	}

	private void getPublishData(int eventIds) {
		// TODO Auto-generated method stub
		
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			
			SharedPreferences.Editor editor1 =settings.edit();
			editor1.putInt("updateFlag", 1);
			
			
			
			editor1.commit();
//			new GetDownloadedData(context).execute("");
//			new GetEventData().execute(""+eventIds) ;	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	class DownloadMasterData extends AsyncTask<String, String, String>{


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			try {




				GetMasterData data = new GetMasterData(context,false);
				data.execute();
			} catch (Exception e) {
				// TODO: handle exception
				GlobalData.printError(e);
			}

		}


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			return null;
		}
		
		
		
	}

	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {

		float pk = (float) (180 / 3.14169);
		float a1 = (float) lat1 / pk;
		float a2 = (float) lng1 / pk;

		float b1 = (float) lat2 / pk;

		float b2 = (float) lng2 / pk;

		float t1 = (float) (Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math
				.cos(b2));

		float t2 = (float) (Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math
				.sin(b2));

		float t3 = (float) (Math.sin(a1) * Math.sin(b1));

		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}

	// Generate Notification

	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context,String title, String message,
			Intent notificationIntent) {
		int icon = R.drawable.ic_stat_noticon;

		Bitmap not_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_stat_noticon);

		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		if(title.length()<=0) {
			title = context.getString(R.string.app_name);
		}

		GlobalData.printMessage(message);

		int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);


		PendingIntent intent=null;
		if(notificationIntent!=null) {
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP | PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			 iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
			  intent = PendingIntent.getActivity(context, iUniqueId,
					notificationIntent, 0);
		}
//		notification.setLatestEventInfo(context, title, message, intent);


		int n = (int) System.currentTimeMillis();
//		notificationManager.notify(n, notification);




		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(
				context);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);


//			notification = builder.setContentIntent(intent)
//					.setSmallIcon(icon).setTicker(message).setWhen(when)
//					.setAutoCancel(true).setContentTitle(title)
//					.setContentText(message)
//					.setStyle(new Notification.BigTextStyle()
//							.setSummaryText(message)).build();

		 notification = new Notification.Builder(context)
				 .setContentIntent(intent)
				.setContentTitle(title)
				.setContentText(message)
				.setSmallIcon(icon)
				 //.setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_launcher)).getBitmap())
				 .setStyle(new Notification.BigTextStyle()
						 .bigText(message))
				 .build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notification.flags |= Notification.DEFAULT_SOUND;

		Uri defaultRingtoneUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		notification.sound = defaultRingtoneUri;

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.DEFAULT_SOUND;


		notificationManager.notify(n, notification);
	}

	public static void addDataIntoDatabase(Context context, int gcmId,
			String message) {
		//
		// DataBaseHandler db=new DataBaseHandler(context);
		// GCM g=new GCM();
		// try{
		// db.open();
		//
		// g.setGcmGCMID(gcmId);
		// g.setGcmMessage(message);
		// db.addGCM(g);
		//
		// SharedPreferences settings = PreferenceManager
		// .getDefaultSharedPreferences(context);
		//
		// SharedPreferences.Editor editor = settings.edit();
		// editor.putInt("GCMshowFlag", gcmId);
		// editor.commit();
		//
		// }catch (Exception e) {
		// // TODO: handle exception
		// GlobalData.printErrorMessage(e);
		// }finally{
		// db.close();
		// }
		//
		//
	}
}
