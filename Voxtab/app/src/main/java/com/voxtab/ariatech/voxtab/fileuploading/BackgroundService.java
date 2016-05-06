package com.voxtab.ariatech.voxtab.fileuploading;

/**
 * Created by MAC 2 on 4/20/2016.
 */
//package backend.snippets;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ariatech.lib_project.WebCommunicator;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.OrderAudioFailedActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.RevisedOrderDetailsActivity;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.DeviceInfo;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Maurya Talisetti
 */
public class BackgroundService extends IntentService {

    private NotificationManager nm;
    private final Calendar time = Calendar.getInstance();


    Context context;

    OrderDetails orderDetails = new OrderDetails();
    LinkedList<MyRecording> myRecording = new LinkedList<>();


    int uploadCount = 0;


    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int id = 1;
    int counter = 0;
    private NotificationReceiver nReceiver;


    long totalsize = 0;
    int per = 0;

    public BackgroundService() {
        super("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    Calendar validTime = Calendar.getInstance();
    Calendar curTime = Calendar.getInstance();

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
//        fName = intent.getExtras().getString("fname");
//        lName = intent.getExtras().getString("lname");
//        phoneNumber = intent.getExtras().getString("phone_no");
//        latitude = intent.getExtras().getString("latitude");
//        longitude = intent.getExtras().getString("longitude");
//        selectedImagePath = intent.getExtras().getString("selectedImagePath");
//        doFileUpload();

        context = BackgroundService.this;
        myRecording = GlobalData.selectedMyRecording;
        orderDetails = GlobalData.selectedOrder;
        displayPercentageNotification();

        for (int i = 0; i < myRecording.size(); i++) {
            try {

                File file = new File(myRecording.get(i).getSourceLink());
                totalsize = totalsize + file.length();

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }


        for (int i = 0; i < myRecording.size(); i++) {
            try {


//                FileUploadTask imageDownloader = new FileUploadTask();
//                imageDownloader.execute(myRecording.get(i));
                displayPercentageNotification();

                fileUploading(i);

            } catch (Exception e) {
                GlobalData.printError(e);

            }

        }


        checkData();

    }

    @Override
    public void onCreate() {
        super.onCreate();


        context = BackgroundService.this;
        id = (int) System.currentTimeMillis();


        try {

            Calendar calendar = GlobalData.getSysDate(orderDetails.getTo_date());
            validTime = Calendar.getInstance();
            validTime.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            validTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            validTime.set(Calendar.SECOND, calendar.get(Calendar.SECOND));


            curTime = Calendar.getInstance();

            long seconds = (validTime.getTimeInMillis() - curTime.getTimeInMillis()) / 1000;


            GlobalData.printMessage("Cur time" + curTime.getTime().toString());
            GlobalData.printMessage("Nxt time" + validTime.getTime().toString());
            GlobalData.printMessage("Sec " + seconds);


            if (seconds <= 0) {

                // 1 Day = 86400 Sec

                validTime.set(Calendar.DAY_OF_MONTH, validTime.get(Calendar.DAY_OF_MONTH) + 1);
                GlobalData.printMessage("Nxt time" + validTime.getTime().toString());
                // minuse 12 hour sec from current timestamp
                try {
                    //seconds = 43200 + seconds;
                    GlobalData.printMessage("Sec " + seconds);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }


            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }


        setData();

//        Toast.makeText(this, "Service created at " + time.getTime(),
//                Toast.LENGTH_LONG).show();
        // showNotification();

    }


    public String fileUploading(int pos) {

        String res = "";
        try {

            String url = myRecording.get(pos).getSourceLink();//  "/storage/emulated/0/Voxtab/Recording_020_06042016.mp3";


            // Set Paramater


            String param[] = new String[]{"assignment_no", myRecording.get(pos).getAssignment_no(),
                    "order_media_id", "" + myRecording.get(pos).getServerId(),
                    "api_key", "" + GlobalData.SERVERAPIKEY,
                    "file_name", "" + myRecording.get(pos).getRecName(),
                    "size", "" + myRecording.get(pos).getRecSize(),
                    "extension", "" + getExtention(myRecording.get(pos).getRecLocalPath()),
                    "user_id", "" + GlobalData.userSelected.getUser_id()};


            if (url.length() > 0) {
                res = uploadFile(url, param);
            }

            GlobalData.printMessage("Response: " + res);


            try {

                JSONObject object = new JSONObject(res);

                if (object.getString("status").equalsIgnoreCase("200")) {
                    try {

                        JSONArray array = object.getJSONArray("file_meta_json");

                        if (array.length() > 0) {
                            File_Meta_JSON obj = new File_Meta_JSON();
                            obj.parseJSON(array.getJSONObject(0));


                            DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                            try {
                                db.open();
                                if (obj.getFile_status().length() <= 0) {
                                    obj.setFile_status("uploaded");
                                }

                                db.updatingRecording(obj);


                                myRecording.get(pos).setIsUploaded(1);

                                uploadCount = uploadCount + 1;
                                counter = counter + 1;

                            } catch (Exception e) {
                                GlobalData.printError(e);
                            } finally {
                                db.close();
                            }


                        }


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return res;
    }

    public String getExtention(String name) {

        String ext = "";
        try {
            ext = name.substring(name.lastIndexOf(".") + 1);


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return ext;
    }

    @Override
    public void onDestroy() {
        //   super.onDestroy();
        // Cancel the persistent notification.
        // nm.cancel(R.string.service_started);
//        Toast.makeText(this,
//                " Service destroyed at " + time.getTime() + ";",
//                Toast.LENGTH_LONG).show();
    }


    public void setData() {

        try {

            mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);

            mBuilder.setContentTitle("" + context.getString(R.string.uploading_files)).setContentText(orderDetails.getAssignment_no() + " " + context.getString(R.string.uploading_in_progress)).setSmallIcon(R.drawable.ic_stat_noticon);
            // Start a lengthy operation in a background thread


            mNotifyManager.notify(id, mBuilder.build());
            //mBuilder.setAutoCancel(false);


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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            } else {
                Log.i("ACC", "Have Notification access");
            }

            nReceiver = new NotificationReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(NLService.NOT_TAG);
            context.registerReceiver(nReceiver, filter);

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public void checkData() {
        try {

            try {
                float len = myRecording.size();
                // When the loop is finished, updates the notification
                if (counter >= len) {


                    mBuilder.setContentText(context.getString(R.string.Upload_complete))
                            // Removes the progress bar
                            .setProgress(0, 0, false);


//                    if(uploadCount == myRecording.size()){


                    GlobalData.printMessage("Cur time" + Calendar.getInstance().getTime().toString());
                    GlobalData.printMessage("Nxt time" + validTime.getTime().toString());


                    if (validTime.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {

                        mBuilder.setContentTitle("Done.");
                        // Upload Count
                        orderDetails.setOrder_status_id("conf");
//                            new UpdateOrder().execute(orderDetails);

                        updateOrder(orderDetails);

                        mNotifyManager.cancel(id);

                    } else {

                        mBuilder.setContentTitle(context.getString(R.string.revschedule));

                        // Reviced Screen  Confirm Order Date And Time
                        GlobalData.selectedOrder = orderDetails;

                        Intent intent = new Intent(context, RevisedOrderDetailsActivity.class);
                        intent.putExtra("orderId", orderDetails.getServer_Id());
                        intent.putExtra("assignment_no", orderDetails.getAssignment_no());

                        mBuilder.setOngoing(false);
                        mBuilder.setContentIntent(PendingIntent
                                .getActivity(
                                        context,
                                        id,
                                        intent,
                                        PendingIntent.FLAG_CANCEL_CURRENT));


//                            context.startActivity(intent);
                        mBuilder.setAutoCancel(true);
                        mNotifyManager.notify(id + 1, mBuilder.build());
                        mNotifyManager.cancel(id);
                    }


                    // Done All Uploading Contentss


//                    }


                } else {


                    LinkedList<MyRecording> faildRec = new LinkedList<>();
                    try {


                        for (int i = 0; i < myRecording.size(); i++) {

                            if (myRecording.get(i).getIsUploaded() <= 0) {
                                faildRec.add(myRecording.get(i));
                            }

                        }


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                    orderDetails.setRecList(faildRec);

                    Intent intent = new Intent(context, OrderAudioFailedActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("order_id", orderDetails.getServer_Id());
                    b.putString("assignment_no", orderDetails.getAssignment_no());

                    intent.putExtras(b);

                    intent.putExtra("order_id", orderDetails.getServer_Id());
                    intent.putExtra("assignment_no", orderDetails.getAssignment_no());

                    Gson gson = new Gson();
                    String myJson = gson.toJson(orderDetails);


                    intent.putExtra("order_details", "" + myJson);


                    orderDetails.setOrder_status_id("failed");
                    updateOrder(orderDetails);


                    int per = (int) (((counter + 1) / len) * 100f);
                    Log.i("Counter", "Counter : " + counter + ", per : " + per);
                    mBuilder.setContentText(orderDetails.getAssignment_no() + " " + context.getString(R.string.uploading_Failed));


                    int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
                    PendingIntent penintent = PendingIntent.getActivity(context, iUniqueId,
                            intent, 0);
                    mBuilder.setContentIntent(penintent);

//
// mBuilder.setProgress(100, per, false);
                    // Displays the progress bar for the first time.


                    mBuilder.setOngoing(false);
                    mBuilder.setAutoCancel(true);
                    mNotifyManager.notify(id, mBuilder.build());
                }
                counter++;
            } catch (Exception e) {
                GlobalData.printError(e);
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }

    public void updateOrder(OrderDetails order) {

        WebServiceMySQl mySQl = new WebServiceMySQl(context);
        try {
            try {


                // set Order Placed Details


                JSONObject object = new JSONObject();
                object.put("user_id", "" + GlobalData.userSelected.getUser_id());
                object.put("total_duration", "" + order.getTotal_duration());
                object.put("delivery_date", "" + order.getDelivery_date());
                object.put("total_fees", "" + order.getTotal_fees());
                object.put("order_complete_details", "" + order.getOrder_complete_details());
                object.put("assignment_no", "" + order.getAssignment_no());
                object.put("order_id", "" + order.getServer_Id());
                object.put("order_status_id", "" + order.getOrder_status_id());

                mySQl = new WebServiceMySQl(context);

                WebServiceResonse resonse = mySQl.updateorder(object);

                String msg = "";

                try {

                    //
                    // {"status":"200","message": "Your Order with Assignment number CAPGEJ-1M is updated !","assignment_no":"CAPGEJ-1M" }


                    if (resonse.getJsonObject().getString("status").equalsIgnoreCase("200")) {


                    } else {


                    }
                    msg = resonse.getJsonObject().getString("message");

                    if (msg.length() > 0) {

//					GlobalData.showSnackBar(,msg,true);
                    }

                } catch (Exception e) {
                    GlobalData.printError(e);
                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public String uploadFile(String sourceFileUri, String[] params) {

        String fileName = sourceFileUri;
        String serverResponseMessage = "";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        int serverResponseCode = 0;
        if (!sourceFile.isFile()) {
//            dialog.dismiss();
            return "File not Found";
        } else {
            FileInputStream fileInputStream = null;
            try {
                // open a URL connection to the Servlet

                fileInputStream = new FileInputStream(sourceFile);
                java.net.URL url = new java.net.URL(GlobalData.UPLOAD_FILE);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("FilePath", fileName);
                  /* conn.setRequestProperty("userid", ""+74);
                  conn.setRequestProperty("videotype", ""+7); */


                dos = new DataOutputStream(conn.getOutputStream());
                int count = (params.length / 2);
                for (int i = 0; i < params.length; i = i + 2) {

                    try {

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"" + params[i] + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes("" + params[i + 1]);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                }


                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);
                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                long bytes = sourceFile.length();

                long uploadBytes = 0;

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                while (bytesRead > 0) {

                    uploadBytes = uploadBytes + bytesRead;

                    try {
                        per = (int) (((float) uploadBytes / totalsize) * 100);

                    } catch (Exception e) {
                        GlobalData.printError(e);

                    }


                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                }


                GlobalData.printMessage("Uploaded Bytes:" + uploadBytes);
                GlobalData.printMessage("File Bytes:" + bytes);

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
//           serverResponseMessage = conn.getResponseMessage();

                displayPercentageNotification();
                try {
                    InputStream is = null;

                    is = conn.getInputStream();
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }
                    serverResponseMessage = sb.toString();
                } catch (Exception e) {
                    GlobalData.printError(e);
                }


//
//          GlobalData.printMessage("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

                //close the streams //

                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (Exception ex) {
//                dialog.dismiss();
                GlobalData.printError(ex);
            }
//            dialog.dismiss();
            return serverResponseMessage;

        } // End else block
    }


    public void displayPercentageNotification() {

        try {

            Log.i("Counter", "Counter : " + counter + ", per : " + per);
            mBuilder.setContentText(orderDetails.getAssignment_no() + " " + context.getString(R.string.Uploaded) + "  " + per + "/100");
            mBuilder.setProgress(100, per, false);

            // Displays the progress bar for the first time.
            mNotifyManager.notify(id, mBuilder.build());
        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }


    class UpdateOrder extends AsyncTask<OrderDetails, WebServiceResonse, WebServiceResonse> {


        WebServiceMySQl mySQl = null;
        WebServiceResonse resonse = new WebServiceResonse();

        @Override
        protected WebServiceResonse doInBackground(OrderDetails... params) {


            try {
                OrderDetails order = params[0];


                // set Order Placed Details


                JSONObject object = new JSONObject();
                object.put("user_id", "" + GlobalData.userSelected.getUser_id());
                object.put("total_duration", "" + order.getTotal_duration());
                object.put("delivery_date", "" + order.getDelivery_date());
                object.put("total_fees", "" + order.getTotal_fees());
                object.put("order_complete_details", "" + order.getOrder_complete_details());
                object.put("assignment_no", "" + order.getAssignment_no());
                object.put("order_id", "" + order.getServer_Id());
                object.put("order_status_id", "" + order.getOrder_status_id());

                mySQl = new WebServiceMySQl(context);

                resonse = mySQl.updateorder(object);


            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;


        }

        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            String msg = "";

            try {

                //
                // {"status":"200","message": "Your Order with Assignment number CAPGEJ-1M is updated !","assignment_no":"CAPGEJ-1M" }


                if (res.getJsonObject().getString("status").equalsIgnoreCase("200")) {


                } else {


                }
                msg = res.getJsonObject().getString("message");

                if (msg.length() > 0) {

//					GlobalData.showSnackBar(,msg,true);
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            }


        }


    }


    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
            Log.i("NotificationReceiver", "NotificationReceiver onReceive : " + event);
            if (event.trim().contentEquals(NLService.NOT_REMOVED)) {
                //  killTasks();
            }
        }


    }
}










