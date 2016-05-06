
package com.voxtab.ariatech.voxtab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.voxtab.ariatech.voxtab.gcm.NotificationHandler;
import com.voxtab.ariatech.voxtab.gcm.ServerUtilities;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

import static com.voxtab.ariatech.voxtab.CommonUtilities.SENDER_ID;
import static com.voxtab.ariatech.voxtab.CommonUtilities.displayMessage;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		GlobalData.printMessage("Register Device" + registrationId);
		Log.i(TAG, "Device registered: regId = " + registrationId);
		displayMessage(context, getString(R.string.gcm_registered));
		ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			// ServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		


		String data = "";
		Log.i(TAG, "Received message");
		try {
			data = intent.getStringExtra("payload");



			try {
				data = GlobalData.decryptData(data);

			}catch (Exception e){
				GlobalData.printError(e);}

		//	generateNotification(context, data);





		//	displayMessage(context, data);
			
			try {
			///	data=GlobalData.decryptData(data);
			} catch (Exception e) {
				// TODO: handle exception
				GlobalData.printError(e);
			}
			Log.i("data", data);
			GlobalData.printMessage("data=" + data);


			 try {
				 GlobalData.setUserData(context);
				 JSONObject jsonObject=new JSONObject(data);

				 if(jsonObject.getString("user_id").equals(""+ GlobalData.userSelected.getUser_id())) {
					 NotificationHandler notification = new NotificationHandler(context, data);
					 notification.setNotification();
				 }else{

					 GlobalData.printMessage("Notification USer Not Selected");
				 }
					 
				} catch (Exception e) {
					// TODO: handle exception
					GlobalData.printError(e);
				}
			 
			
				 

				 
			
			 
			 
		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("Error:" + e);
		}
		// notifies user
//		generateNotification(context, data);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);

		// notifies user
		generateNotification(context, message);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	

		String title = context.getString(R.string.app_name);
		Notification notification = new Notification(icon, message, when);
		Intent notificationIntent = new Intent(context, GCMStart.class);

		GlobalData.printMessage(message);

		int type = 0;
		JSONObject notData;
		String notMsg = "";
		try {

			notData = new JSONObject(message);

			try {
				type = Integer.parseInt(notData.getString("Type"));
			} catch (Exception e) {
				type = 0;
			}

			try {
				notMsg = notData.getString("Msg");
			} catch (Exception e) {
				notMsg = "";
			}
			
			

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError("generateNotification", e);

			type = 0;
		}

		if (type==1) {





		}else if (type==3) {
//			context.startActivity(new Intent(context,
//					DeleteApp.class)
//					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			
			return;
		
		} else if(type==2){

			notificationIntent = new Intent(context, GCMStart.class);
		}



//		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);






			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context);

			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			notification = builder.setContentIntent(intent)
					.setSmallIcon(icon).setTicker(notMsg).setWhen(when)
					.setAutoCancel(true).setContentTitle(title)
					.setContentText(message).build();


			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.flags |= Notification.DEFAULT_SOUND;

			int n = (int) System.currentTimeMillis();
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
