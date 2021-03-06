package com.voxtab.ariatech.voxtab.fileuploading;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.RevisedOrderDetailsActivity;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class FileUploading  {

	NotificationCompat.Builder mBuilder;
	NotificationManager mNotifyManager;
	int id = 1;
	int counter = 0;
	private NotificationReceiver nReceiver;
	ArrayList<AsyncTask<MyRecording, String, String>> arr;

	LinkedList<MyRecording> urlsToDownload=new LinkedList<>();


	Context context;

	int uploadCount =0;






	OrderDetails orderDetails=new OrderDetails();

	Timer timer=new Timer();
	Calendar validTime=Calendar.getInstance();
	Calendar curTime=Calendar.getInstance();

	class NotificationReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
			Log.i("NotificationReceiver", "NotificationReceiver onReceive : " + event);
			if (event.trim().contentEquals(NLService.NOT_REMOVED)) {
				killTasks();
			}
		}
	}

	private void killTasks() {
		if (null != arr & arr.size() > 0) {
			for (AsyncTask<MyRecording, String, String> a : arr) {
				if (a != null) {
					Log.i("NotificationReceiver", "Killing download thread");
					a.cancel(true);
				}
			}
			mNotifyManager.cancelAll();
		}
	}


//	@Override
//	public void onCreate() {
//		super.onCreate();
//
//
//
//
//
//	}

//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//
//
//		context = FileUploading.this;
//		FileUploading(context,GlobalData.selectedMediaArray,GlobalData.selectedOrder);
//		setData();
//
//		return super.onStartCommand(intent, flags, startId);
//	}
//
//	@Nullable
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}

	public   FileUploading(Context contexts,LinkedList<MyRecording> urlArray, final OrderDetails orderDetails) {
		// urlsToDownload = urlArray;

		this.context = contexts;
		urlsToDownload = urlArray;
		uploadCount =0;
		this.orderDetails=orderDetails;

		try {
			id = (int) System.currentTimeMillis();
		}catch (Exception e){
			GlobalData.printError(e);
		}


		// Set Timer to check

		try {

			Calendar calendar=GlobalData.getSysDate(orderDetails.getTo_date());



			validTime = Calendar.getInstance();
			validTime.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY));
			validTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
			validTime.set(Calendar.SECOND,calendar.get(Calendar.SECOND));


			 curTime = Calendar.getInstance();

			long seconds = (validTime.getTimeInMillis()-curTime.getTimeInMillis())/1000;


			GlobalData.printMessage("Cur time"+curTime.getTime().toString());
			GlobalData.printMessage("Nxt time"+validTime.getTime().toString());
			GlobalData.printMessage("Sec "+seconds);




			if(seconds<=0) {

				// 1 Day = 86400 Sec

				validTime.set(Calendar.DAY_OF_MONTH,validTime.get(Calendar.DAY_OF_MONTH)+1);
				GlobalData.printMessage("Nxt time" + validTime.getTime().toString());
				// minuse 12 hour sec from current timestamp
try {
	//seconds = 43200 + seconds;
	GlobalData.printMessage("Sec "+seconds);
}catch (Exception e){
	GlobalData.printError(e);
}



			}
//				timer.schedule(new TimerTask() {
//					@Override
//					public void run() {
//
//						try {
//
//							if(uploadCount == urlsToDownload.size()){
//
//
//								try {
//									new UpdateOrder().execute(orderDetails);
//
//								}catch (Exception e){
//									GlobalData.printError(e);
//								}
//
//								// Upload Count
//
//
//
//							}else{
//
//								// Call Uploading Not Completed...
//
//							}
//
//
//							timer.cancel();
//
//
//						}catch (Exception e){
//							GlobalData.printError(e);
//						}
//
//
//					}
//				},seconds);
//			}



		}catch (Exception e){
			GlobalData.printError(e);
		}

	}



	public void setData(){

		try {

		mNotifyManager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(context);

		mBuilder.setContentTitle(""+context.getString(R.string.uploading_files)).setContentText(orderDetails.getAssignment_no()+" "+context.getString(R.string.uploading_in_progress)).setSmallIcon(R.drawable.ic_stat_noticon);
		// Start a lengthy operation in a background thread
		mBuilder.setProgress(0, 0, false);
		mBuilder.setOngoing(true);

		mNotifyManager.notify(id, mBuilder.build());
		//mBuilder.setAutoCancel(false);



		arr = new ArrayList<AsyncTask<MyRecording, String, String>>();
		int incr;
		for (incr = 0; incr < urlsToDownload.size(); incr++) {
			FileUploadTask imageDownloader = new FileUploadTask();
			imageDownloader.execute(urlsToDownload.get(incr));
			arr.add(imageDownloader);
		}

		ContentResolver contentResolver = context.getContentResolver();
		String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
		String packageName = context.getPackageName();

		// check to see if the enabledNotificationListeners String contains our
		// package name
		if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName)) {
			// in this situation we know that the user has not granted the app
			// the Notification access permission
			// Check if notification is enabled for this application
			Log.i("ACC", "Dont Have Notification access");
			Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
	     	context.startActivity(intent);
		} else {
			Log.i("ACC", "Have Notification access");
		}

		nReceiver = new NotificationReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(NLService.NOT_TAG);
		context.registerReceiver(nReceiver, filter);

		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		killTasks();
//		unregisterReceiver(nReceiver);
//	}

	private void downloadImagesToSdCard(String downloadUrl, String imageName) {
		FileOutputStream fos;
		InputStream inputStream = null;

		try {
			URL url = new URL(downloadUrl);
			/* making a directory in sdcard */
			String sdCard = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(sdCard, "DemoDownload");

			/* if specified not exist create new */
			if (!myDir.exists()) {
				myDir.mkdir();
				Log.v("", "inside mkdir");
			}

			/* checks the file and if it already exist delete */
			String fname = imageName;
			File file = new File(myDir, fname);
			Log.d("file===========path", "" + file);
			if (file.exists())
				file.delete();

			/* Open a connection */
			URLConnection ucon = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) ucon;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			inputStream = httpConn.getInputStream();

			/*
			 * if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			 * inputStream = httpConn.getInputStream(); }
			 */

			fos = new FileOutputStream(file);
			// int totalSize = httpConn.getContentLength();
			// int downloadedSize = 0;
			byte[] buffer = new byte[1024];
			int bufferLength = 0;
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				fos.write(buffer, 0, bufferLength);
				// downloadedSize += bufferLength;
				// Log.i("Progress:", "downloadedSize:" + downloadedSize +
				// "totalSize:" + totalSize);
			}
			inputStream.close();
			fos.close();
			Log.d("test", "Image Saved in sdcard..");
		} catch (IOException io) {
			inputStream = null;
			fos = null;
			io.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	public String fileUploading(MyRecording recording){
		WebServiceMySQl webServiceMySQl=new WebServiceMySQl(context);
		String res="";
		try {

			String  url = recording.getSourceLink();//  "/storage/emulated/0/Voxtab/Recording_020_06042016.mp3";



			// Set Paramater



			String param[]=new String[]{"assignment_no",recording.getAssignment_no(),
					"order_media_id",""+recording.getServerId(),
					"api_key",""+GlobalData.SERVERAPIKEY,
					"file_name",""+recording.getRecName(),
					"size", ""+recording.getRecSize(),
					"extension",""+getExtention(recording.getRecLocalPath()),
					"user_id",""+GlobalData.userSelected.getUser_id()};







			if(url.length()>0){
				res = webServiceMySQl.uploadFile(url,param);
			}

			GlobalData.printMessage("Response: "+res);





		}catch (Exception e){
			GlobalData.printError(e);
		}

		return  res;
	}


	public String getExtention(String name){

		String ext="";
		try {
			ext= name.substring(name.lastIndexOf(".") + 1);


		}catch (Exception e){
			GlobalData.printError(e);
		}

		return  ext;
	}


	private class FileUploadTask extends AsyncTask<MyRecording, String, String> {

		@Override
		protected String doInBackground(MyRecording... param) {



			String result= "";
			try {

		// My Recording Data


				result=	fileUploading(param[0]);




			}catch (Exception e){
				GlobalData.printError(e);
			}




			return result;
		}

		protected void onProgressUpdate(String... values) {
		}

		@Override
		protected void onPreExecute() {
			Log.i("Async-Example", "onPreExecute Called");
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("Async-Example", "onPostExecute Called");

			// Response Message
//                {
//                    "status":"200",
//                        "message":" Audio FILE uploaded !",
//                        "assignment_no":"CAPGEJ-1M",
//                        "file_meta_json":[
//                    {
//                            "order_media_id":"1",
//                            "user_id":"1",
//                            "assignment_no":"CAPGEJ-1M",
//                            "file_status":"uploaded",
//                            "source_type":"66",
//                            "file_name":"CAPGEJ-1M_20164675044.png",
//                            "file_description":"666"
//                    }
//                    ]
//                }

			// Set Data To Rec
			try {

				JSONObject object = new JSONObject(result);

				if (object.getString("status").equalsIgnoreCase("200")){
					try {

						JSONArray array= object.getJSONArray("file_meta_json");

						if(array.length()>0){
							File_Meta_JSON obj=new File_Meta_JSON();
							obj.parseJSON(array.getJSONObject(0));


							DatabaseHandlerNew db=new DatabaseHandlerNew(context);
							try {
								db.open();
								if(obj.getFile_status().length()<=0){
									obj.setFile_status("uploaded");
								}

								db.updatingRecording(obj);

								uploadCount = uploadCount +1;





							}catch (Exception e){
								GlobalData.printError(e);
							}finally {
								db.close();
							}


						}




					}catch (Exception e){
						GlobalData.printError(e);
					}

				}



			}catch (Exception e){
				GlobalData.printError(e);
			}




			// Set Counter

			try {
				float len = urlsToDownload.size();
				// When the loop is finished, updates the notification
				if (counter >= len - 1) {


					mBuilder.setContentTitle("Done.");
					mBuilder.setContentText( context.getString(R.string.Upload_complete))
							// Removes the progress bar
							.setProgress(0, 0, false);
					mNotifyManager.notify(id, mBuilder.build());

					// Done All Uploading Contentss

					mNotifyManager.cancelAll();





					if(uploadCount == urlsToDownload.size()){


						try {
							timer.cancel();

						}catch (Exception e){
							GlobalData.printError(e);
						}

						GlobalData.printMessage("Cur time"+ Calendar.getInstance().getTime().toString());
						GlobalData.printMessage("Nxt time" + validTime.getTime().toString());


						if(validTime.getTimeInMillis()>= Calendar.getInstance().getTimeInMillis()) {

							// Upload Count
							new UpdateOrder().execute(orderDetails);
						}else{

							// Reviced Screen  Confirm Order Date And Time
							GlobalData.selectedOrder = orderDetails;

							Intent intent = new Intent(context, RevisedOrderDetailsActivity.class);
							intent.putExtra("orderId", orderDetails.getServer_Id());
							intent.putExtra("assignment_no", orderDetails.getAssignment_no());


							context.startActivity(intent);


						}




					}


				} else {
					int per = (int) (((counter + 1) / len) * 100f);
					Log.i("Counter", "Counter : " + counter + ", per : " + per);
					mBuilder.setContentText(orderDetails.getAssignment_no()+" "+context.getString(R.string.Uploaded) + "  " + per + "/100");
					mBuilder.setProgress(100, per, false);
					// Displays the progress bar for the first time.
					mNotifyManager.notify(id, mBuilder.build());
				}
				counter++;
			}catch (Exception e){
				GlobalData.printError(e);
			}


		}

	}





	class UpdateOrder extends AsyncTask <OrderDetails,WebServiceResonse ,WebServiceResonse>{


		WebServiceMySQl mySQl =null;
	WebServiceResonse resonse=new WebServiceResonse();

		@Override
		protected WebServiceResonse doInBackground(OrderDetails... params) {


			try {
				OrderDetails order= params[0];


				// set Order Placed Details
				order.setOrder_status_id("conf");




				JSONObject object=new JSONObject();
				object.put("user_id",""+GlobalData.userSelected.getUser_id());
				object.put("total_duration",""+order.getTotal_duration());
				object.put("delivery_date",""+order.getDelivery_date());
				object.put("total_fees",""+order.getTotal_fees());
				object.put("order_complete_details",""+order.getOrder_complete_details());
				object.put("assignment_no",""+order.getAssignment_no());
				object.put("order_id",""+order.getServer_Id());
				object.put("order_status_id",""+order.getOrder_status_id());

				mySQl =new WebServiceMySQl(context);

				resonse= mySQl.updateorder(object);





			}catch (Exception e){
				GlobalData.printError(e);
			}



			return resonse;




		}

		@Override
		protected void onPostExecute(WebServiceResonse res) {
			super.onPostExecute(res);

			String msg="";

			try {

				//
				// {"status":"200","message": "Your Order with Assignment number CAPGEJ-1M is updated !","assignment_no":"CAPGEJ-1M" }


				if(res.getJsonObject().getString("status").equalsIgnoreCase("200")){


				}else {


				}
				msg= res.getJsonObject().getString("message");

				if(msg.length()>0){

//					GlobalData.showSnackBar(,msg,true);
				}

			}catch ( Exception e){
				GlobalData.printError(e);
			}


		}
	}
}
