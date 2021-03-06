package com.voxtab.ariatech.voxtab.globaldata;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.AboutusActivity;
import com.voxtab.ariatech.voxtab.HomeActivity;
import com.voxtab.ariatech.voxtab.LoginActivity;
import com.voxtab.ariatech.voxtab.NotificationsActivity;
import com.voxtab.ariatech.voxtab.OrderDetailsActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.adapter.NotificationListAdapter;
import com.voxtab.ariatech.voxtab.bean.ChangePrice;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Notification;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Order_Details_Bean;
import com.voxtab.ariatech.voxtab.bean.Service_type;
import com.voxtab.ariatech.voxtab.bean.TimeStamb;
import com.voxtab.ariatech.voxtab.bean.Transcription_type;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.bean.Users_JSON;
import com.voxtab.ariatech.voxtab.bean.Vas;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class GlobalData {
    public static final String baseURL ="http://uatvoxtab.atechmobility.com/";
//    public static final String baseURL ="http://voxtab.atechmobility.com/";
    public static final String URL = baseURL+ "/VoxTabServices.svc/";
    public static final String IMAGE_URL = baseURL + "/VOXTAB_DRIVE/user_profile/";
    public static final String PDF_URL = baseURL + "VOXTAB_DRIVE/";

    public static final String ABOUT_US_URL="http://www.voxtab.com/about-us.htm";
    public static final String TERMS_URL="http://www.voxtab.com/tandc.htm";
    public static final String CONFI_URL="http://www.voxtab.com/confidentiality.htm";


    public static final String SERVICE_REGISTER_DEVICE = "registerDeviceId";
    public static final String SERVICE_FORGOT_PASSWORD = "forgotProviderPassword";
    public static final String SERVICE_CHANGE_PASSWORD = "changeProviderPassword";
    public static final String MESSAGE_CHANGE_PASSWORD = "Password changed succesfully";
    public static final String MESSAGE_CHANGE_PASSWORD_FAILED = "Incorrect Password";

    public static final String ERR_NETWORK_NO_CONNECTION = "Can't connect to the Internet. Please check your mobile data or Wifi connection";
    public static final String ERR_NETWORK_SLOW_OR_NO_CONNECTION = "{ \"status\": \"fail\", \"message\": \"No response from server\"}";

    public static final String ERR_CONNECTION = "No internert connection";

    public static final String SHARE_LEVEL = "SHARE_LEVEL";
    public static final String SHARE_USER_NAME = "SHARE_USER_NAME";

    public static   String USERID1="0";
    public static  boolean longpressFlag = false;

    public static final String APP_KEY = "52k0xv3y3xphw16";
    public static String LANGUAGE=" ";

    //Stoage Directory

    public static String logDirectory = "/Voxtab/Log/";
    public static String storageDirectory = "/Voxtab/Audio/";
    public static String storageDirectoryPDF = "/Voxtab/PDF/";

    public static String permissionRecAudioFlag = "rcord_perm";
    public static String permissionRecStorageFlag = "storage";
    public static String permissionDeviceInfo = "device";

    public static LinkedList<OrderDetails> selectedFiles=new LinkedList<>();

    public static Users userSelected= new Users();

    // SharePrefKeys
    public static String userKey= "UserKey";

    public static String notKey= "notification";
    public static String memSetKey= "memSetting";
    public static String remFlag= "remMeFlag";
    public static String upLoadFlag= "uploadFlag";
    public  static  String feedbackKey="feedback";



    //Settings --- notification
    public static String NOTIFICATIONS = "1";
    public static final String MEMORY = "1";
    public static final String DATA_USAGE = "1";


    public static LinkedList<MyRecording> selectedMediaArray =new LinkedList<>();

    public static PopupWindow popupWindow;
    static LinkedList<Notification> list;


    // Webservices
    public static String webSrviceGetMasterData="GetMasterUpdates";
    public static String webSrvicePlaceOrder="PlaceOrder";
    public static String webSrviceGetOrderList="GetOrderList";
    public static String webSrviceLogin="login";
    public static String webSrviceUpdateorder="updateorder";
    public static String webSrviceGetOrderDetail="GetOrderDetail";
    public static String webSrviceUpdateprofile ="updateprofile";
    public static String webSrviceGetprofile="getprofile";
    public static String webSrviceChangepassword="changepassword";
    public static String webSrviceUpdategcmkey="updategcmkey";
    public static String webSrvicegetpages="getpages";
    public static String webSrviceGetOrderListForFeedback = "get_orderlist4feedback";
    public static String webSrviceSendFeedback = "sendfeedback";
    public static String webSrviceForgotPassword = "forgotpassword";
    public static String webSrviceLogout = "logout";

    public static Order_Details_Bean obj =new Order_Details_Bean();




    public static String free_services_accent = "", free_services_terminology = "", free_services_timestamp = "", free_services_identification = "", free_services_type = "";
    public static String no_discount="No discount";

    public static ArrayList<String> name=new ArrayList<>();
    public static ArrayList<String> type=new ArrayList<>();
    public static ArrayList<String> duration=new ArrayList<>();
    public static ArrayList<String> services=new ArrayList<>();
    public static String service_type_name="", transcription_type_name="", timestamp_duration_name="", valueadded_name="";

    public static int service_type_id, transcription_type_id, timestamp_duration_id, valueadded_id;


    public static ArrayList<ChangePrice> change_price_arraylist=new ArrayList<>();
    public static ArrayList<Service_type> serviceTypes=new ArrayList<>();
    public static ArrayList<Transcription_type> transcriptionType=new ArrayList<>();
    public static ArrayList<TimeStamb> timestampDuration=new ArrayList<>();
    public static ArrayList<Vas> valueAddedServices=new ArrayList<>();
    public static ArrayList<Free_services> freeServices=new ArrayList<>();
    public static final String UPLOAD_FILE = baseURL+"/voxtab/uploadmedia.aspx";


    public static OrderDetails selectedOrder=new OrderDetails();
    public static LinkedList<MyRecording> selectedMyRecording=new LinkedList<>();



    // GCM Variables

    public static String deviceType = "1";
    public static String SERVERAPIKEY = "abcd";
    public static DeviceInfo deviceInfo = null;



    public static final int IMG_WIDTH_EXS = 56;
    public static final int IMG_HEIGHT_EXS = 56;
    public static final int IMG_WIDTH_S = 128;
    public static final int IMG_HEIGHT_S = 128;
    public static final int IMG_WIDTH_M = 256;
    public static final int IMG_HEIGHT_M = 256;
    public static final int IMG_WIDTH_M1 = 256;
    public static final int IMG_HEIGHT_M1 = 150;
    public static final int IMG_WIDTH_L = 512;
    public static final int IMG_HEIGHT_L = 512;
    //Settings --- notification
    public  static String SEND_ALL="";
    public  static String MUTE_ALL="";
    public  static String NOTIFY_DELIVERY="";

    public static final String EXTERNAL = "";
    public static final String PHONE = " ";
    public static final String WIFI = " ";
    public static final String NETWORK = " " ;
    public static final String ANYTIME = " ";
    public static final String ASK = " " ;


    public static final String SHARE_ASSIGNMENT_ID = "SHARE_ASSIGNMENT_ID";
    public static final String SHARE_DELV_DATE = "SHARE_DELV_DATE";
    public static final String SHARE_TOTAL_FEE = "SHARE_TOTAL_FEE";


    public static void setDeviceInfo(Context context) {
        deviceInfo = new DeviceInfo(context);

//        SharedPreferences settings = PreferenceManager
//                .getDefaultSharedPreferences(context);
//
//        if(settings.getInt("firstTimedevice",0) == 0) {
//
//            deviceInfo = new DeviceInfo(context);
//
//            SharedPreferences.Editor editor1 = settings.edit();
//            editor1.putInt("firstTimedevice", 0);
//
//            editor1.putString("deviceInfo", new Gson().toJson(deviceInfo).toString());
//
//            editor1.commit();
//
//
//
//        }else{
//
//            String obj= settings.getString("deviceInfo", "");
//
//            if(obj.length()>0){
//
//                try {
//                    JSONObject object=new JSONObject(obj);
//
//                    deviceInfo=(DeviceInfo) new Gson().fromJson (obj, DeviceInfo.class);
//
//
//                }catch (Exception e){
//                    GlobalData.printError(e);
//                    deviceInfo = new DeviceInfo(context);
//                }
//            }else{
//                deviceInfo = new DeviceInfo(context);
//            }


//        }

    }




    public static LinkedList<MyRecording> sList=new LinkedList<>();


    // Flags
    public static boolean dataDownloadFlag=false;



    public static String convertSecondsToHMmSsS(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d Hrs %02d Mins", h, m);
    }

    public static void showSnackBar(View view, String message, boolean flag) {


        if (message.length() > 0 && view !=null ){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public static void printError(Exception e) {

        try {
            Logger mLog = Logger.getLogger("");
            mLog.warn(e.toString(),e);


        }catch (Exception e1){
            e1.printStackTrace();
        }
        e.printStackTrace();
        System.out.println(e);
    }

    /*public static Toolbar initToolBar(final AppCompatActivity c, String title, boolean showBack) {
        Toolbar toolbar = (Toolbar) c.findViewById(R.id.toolbar);
        if (title != null) {
            toolbar.setTitle(title);
        }
        c.setSupportActionBar(toolbar);

        if (showBack) {
            c.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.onBackPressed();
                }
            });
        } else {
            c.getSupportActionBar().setDisplayShowHomeEnabled(true);
//            c.getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        }

        c.getSupportActionBar().setHomeButtonEnabled(true);

        return toolbar;
    }

*/
    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printError(Exception e, String message) {
        e.printStackTrace();
        System.out.println(message);
    }

    public static void printError(String message, Exception e) {
        e.printStackTrace();
        System.out.println(message);
    }


    public static Toolbar initToolBarMenu(AppCompatActivity c, String title, boolean showBack) {
        Toolbar toolbar = (Toolbar) c.findViewById(R.id.toolbar);
        if (title != null) {
            toolbar.setTitle(title);
        }
        c.setSupportActionBar(toolbar);

        if (showBack) {
            c.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            c.getSupportActionBar().setDisplayShowHomeEnabled(true);
//            c.getSupportActionBar().setLogo(R.drawable.ic_launcher);
        }

        c.getSupportActionBar().setHomeButtonEnabled(true);

        return toolbar;
    }

    public static Toolbar initToolBarMenu(final AppCompatActivity c, boolean showBack, boolean notFlag) {

        Toolbar toolbar = (Toolbar) c.findViewById(R.id.toolbar);

        final RelativeLayout img_noti = (RelativeLayout) c.findViewById(R.id.img_noti);
        final TextView counter = (TextView) c.findViewById(R.id.counter);

        DatabaseHandlerNew db=new DatabaseHandlerNew(c);

        int notSize=0;
        try {
            db.open();

            notSize= db.getNotificationCount();

        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            db.close();
        }


        if(notFlag && notSize > 0){
            img_noti.setVisibility(View.VISIBLE);
            counter.setVisibility(View.VISIBLE);
        }else{
            img_noti.setVisibility(View.GONE);
            counter.setVisibility(View.GONE);
        }

        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar.setEnabled(true);


        c.setSupportActionBar(toolbar);

        if (showBack) {
            c.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            c.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        c.getSupportActionBar().setHomeButtonEnabled(true);

        int count = 0;
        try {
            db.open();
            count = db.getNotificationCount();
        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            db.close();
        }
        counter.setText("" + count);


        if (img_noti != null) {
            img_noti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopupWindow(c, img_noti);

                }
            });
        }


        return toolbar;
    }





    public static String getPath(AppCompatActivity app, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = app.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public static Bitmap decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeFile(filePath, o2);
    }

    public static int getProductDim1(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int pixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources()
                        .getDisplayMetrics());

        return ((width / 2) - pixels);
    }

    public static double getProductDim(double ratio, Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int pixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources()
                        .getDisplayMetrics());

        return ((width / ratio) - pixels);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static String getFileExt(String file) {
        String extension = file.substring(file.lastIndexOf("."));
        return extension.toUpperCase();
    }


    public static int checkFileSizeFromURL(String url) {
        CommonUtil.printMessage(url);
        URLConnection urlConnection = null;
        try {
            java.net.URL myUrl = new java.net.URL(url);
            urlConnection = myUrl.openConnection();

            urlConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlConnection.getContentLength();
    }


    // save database File
    public static void BackupDatabase(Context context) throws IOException {
        boolean success = true;
        File file = null;
        file = new File(Environment.getExternalStorageDirectory() + "/DB_Backup");

        if (file.exists()) {
            success = true;
        } else {
            success = file.mkdir();
        }

        if (success) {
            String inFileName = "/data/data/" + context.getPackageName() + "/databases/voxtab_new.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = Environment.getExternalStorageDirectory() + "/DB_Backup/voxtab_new.db";

            File outFile = new File(outFileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            //Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            fis.close();
        }
    }

    public static String getTimeFromMis(int milisec) {


        String dateForButton = "";

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(milisec);
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
            Date date = cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            dateForButton = dateFormat.format(date);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return dateForButton;
    }

    public static String convertSecondsToHMmSs(long millis) {
//        long s = seconds % 60;
//        long m = (seconds / 60) % 60;
//        long h = (seconds / (60 * 60)) % 24;
//        return String.format("%d:%02d:%02d", h, m, s);


        int s = (int) (millis / 1000) % 60;
        int m = (int) ((millis / (1000 * 60)) % 60);
        int h = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format("%d:%02d:%02d", h, m, s);
    }


    //holiday_format_conversion
    public static String getDateFromDMY(int day,int month,int year) {


        String dateString = "";

        try {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR, year);
            Date date = cal.getTime();

          //  06-March-2016
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            dateString = dateFormat.format(date);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return dateString;
    }


    public static String getDateFromDMY(String date) {

        //2016-10-13 00:00:00
        // 12 Dec 2016 11:30am

        String dateString = "";

        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd MMM yyyy hh:mma");

            Date olddate = parseFormat.parse(date);




            Calendar cal = Calendar.getInstance();


            cal.setTime(olddate);




            //  06-March-2016
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss ");

            dateString = dateFormat.format(cal.getTime());


        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return dateString;
    }


    public static String getStanderdDateFormt(Calendar cal) {

        //2016-10-13 00:00:00
        // 12 Dec 2016 11:30am

        String dateString = "";

        try {



            //  06-March-2016
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            dateString = dateFormat.format(cal.getTime());


        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return dateString;
    }


    public static Calendar getStanderdDateFormt(String time) {

        //2016-10-13 00:00:00
        // 12 Dec 2016 11:30am

        Calendar calendar =Calendar.getInstance();

        try {



            //  06-March-2016
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            calendar.setTime( dateFormat.parse(time));


        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return calendar;
    }


    public static boolean selfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }


    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void finishAll() {
        if (activities.size() > 0) {
            for (Activity activity : activities) {
                try {
                    activity.finish();
                } catch (Exception e) {
                    GlobalData.printError("", e);
                }
                GlobalData.printMessage("Activity=>"
                        + activity.getClass().getName());
            }
        }
    }


    public static int parseSeconds(String h) {
        String[] h1 = h.split(":");

        int hour = Integer.parseInt(h1[0]);
        int minute = Integer.parseInt(h1[1]);
        int second = Integer.parseInt(h1[2]);

        int temp;
        temp = second + (60 * minute) + (3600 * hour);

        System.out.println("secondsss" + temp);
        return temp;
    }

    public static double parseTimeToMinute(String hours) {
        double minutes = 0;
        String[] split = hours.split(":");
        try {
            minutes += Double.parseDouble(split[0]) * 60;
            minutes += Double.parseDouble(split[1]);
            minutes += Double.parseDouble(split[2]) / 60;
            return minutes;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static String formatHoursAndMinutes(double totalMinutes) {
        String minutes = Double.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + ":" + minutes;
    }


    public static boolean checkdates(String cDate, String fDate, String tDate) {

        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
            Date currentDate = parseFormat.parse(cDate);
            Date fromDate = parseFormat.parse(fDate);
            Date toDate = parseFormat.parse(tDate);





            return currentDate.getTime() >= fromDate.getTime() &&
                    currentDate.getTime() <= toDate.getTime();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String convert24Time(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static boolean timesCheck(String someRandomTime, String string1, String string2) {
        try {
            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            // Add 30 Min time to updated revised logic
            calendar2.setTimeInMillis(calendar2.getTimeInMillis()+1800000);

            calendar2.add(Calendar.DATE, 1);

            Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context
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
        return haveConnectedWifi || haveConnectedMobile;
    }




    public  static  void setFirstTimeDataLoading(Context context){


        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        try {
            editor.putInt("fisrttime",1);

            editor.commit();

        }catch (Exception e){
            printError(e);
        }

    }

    public  static int getFirstTimeDataLoading(Context context){


        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        int value =0;
        try {
            value= settings.getInt("fisrttime", 0);


        }catch (Exception e){
            printError(e);
        }

        return value;

    }


    // Set User

    public static int  setUserData(Context context){

        DatabaseHandlerNew db=new DatabaseHandlerNew(context);
        userSelected=new Users();
        try {
            db.open();

            userSelected = db.getUsers().get(0);


        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            db.close();
        }


//        if(userSelected.getUser_id()<=0){
//            userSelected.setUser_id(1);
//        }

//        USERID =""+userSelected.getUser_id();


        return  userSelected.getUser_id();
        }

    public static String showdate(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd H:mm:ss z yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static Calendar getSysDate(String time) {
        Calendar cal =Calendar.getInstance();
        try {

            SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd H:mm:ss z yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));


            cal.setTime(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return cal;
    }


    public static String showTodate(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd H:mm:ss z yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }


    public static String convertDelDate(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }


    public static String dateDDMMYYYY(Date date) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }


    public static String convertDeliveryDate(String time) {
        String str = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }


    public static String convertDeliveryDateOrderHistory(String time) {
        String str = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a  ");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }



    public static String convertShortDate(String time) {
        String str = ""+time;
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String convertShortDateWithHHmm(String time) {
        String str = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String convertShortDateDownTRPg(String time) {
        String str = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String convertLongDate(String time) {
        String str = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2016-04-08 07:07:29
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String convertholidayDate(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }

    public static String convertDates(String time) {
        String str = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(time);
//            System.out.println(parseFormat.format(date) + " = "
//                    + displayFormat.format(date));
            str = displayFormat.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return str;
    }





    // Get Content URL
    public static String getPath(final Context context, final Uri uri)
    {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static String decryptData(String data) {
        // {"'", "&rsquo;"},
        // {"&#39;", "&rsquo;"}

        try {

            data = data.replace("U 002B", "+");
            data = data.replace("U+002B", "+");
            data = data.replace("U+0021", "!");
            data = data.replace("U+0022", "\"");
            data = data.replace("U+0023", "#");
            data = data.replace("U+0024", "$");
            data = data.replace("U+0025", "%");
            data = data.replace("U+0026", "&");
            data = data.replace("U+0027", "'");
            data = data.replace("U+002F", "/");

            data = data.replace("U 0021", "!");
            data = data.replace("U 0022", "\"");
            data = data.replace("U 0023", "#");
            data = data.replace("U 0024", "$");
            data = data.replace("U 0025", "%");
            data = data.replace("U 0026", "&");
            data = data.replace("U 0027", "'");
            data = data.replace("U 002F", "/");

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return data;
        // {,},
        // {"\"","U+0022"},
        // {"#","U+0023"},
        // {"$","U+0024"},
        //
        // {"%","U+0025"},
        // {"&","U+0026"},
        // {"'","U+0027"},
        // {"/","U+002F"}

    }

// Get Total Hours form TAT
    public static int getTotalHours(float tat){

        int days=0;

        int hours=0;
        int totalHours=0;

        try {
            days = (int) tat;
            hours = (int) (tat - days);

            totalHours= (days * 24) + hours;

        }catch (Exception e){
            GlobalData.printError(e);
        }


        return  totalHours;

    }




    // Get Total Minit form TAT
    public static float getTotalMins(float tat,float totalDuration){


        float totalHours=0;

        float totalTempHours = 0;
        try {
            totalTempHours = totalDuration / 60;

            totalHours = totalTempHours*tat;


        }catch (Exception e){
            GlobalData.printError(e);
        }


        return  totalHours;

    }

    public static float addTAT(float baseTAT, float vatTAT){

        int days=0;
        int hours=0;
        int daysV=0;
        int hoursV=0;
        int totalHours = 0;
        float totalDays = 0;
        try {

            days = (int) baseTAT;
            hours = (int) (baseTAT * 10) - (days * 10);

            daysV = (int) vatTAT;
            hoursV = (int) ((vatTAT * 10 - daysV *10 ));

             totalHours = hours + hoursV;
            totalDays = days + daysV;

            if(totalHours > 8) {
                totalDays = totalDays+ (totalHours / 8);
                totalHours =(totalHours % 8);
            }

            // Add into Total days
            totalDays = totalDays + (float)(totalHours* 0.10);

        }catch (Exception e){
            GlobalData.printError(e);
        }

        return  totalDays;

    }

    public static boolean isLoggedIn(Context context) {
        try {


        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        String json = settings.getString("users", "");
        if (json.equals("")) {
            return false;
        } else {
            return true;
        }
        }catch (Exception e){
            GlobalData.printError(e);
        }
        return false;
    }

    public static Users getMemberId(Context context) {
        try {
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String json = settings.getString("users", "");
            Gson gson = new Gson();
            Users obj = gson.fromJson(json, Users.class);
            return obj;

        } catch (Exception e) {
            GlobalData.printError(e);
            return null;
        }

    }




    public static void  setNotificationkey(Context context, int flag){

        try {

            //flag=>    0: All , 1: Mute All ,  2: Only Delivery

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            editor1.setInteger(GlobalData.notKey, flag);


        } catch (Exception e){
            GlobalData.printError(e);
        }

    }

    public static int  getNotificationkey(Context context){

        int flag=0;
        try {

            //flag=>    0: All , 1: Mute All ,  2: Only Delivery

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            flag=  editor1.getInteger(GlobalData.notKey, 1);


        } catch (Exception e){
            GlobalData.printError(e);
        }

        return  flag;
    }


    public static void  setFeedbackkey(Context context, int flag){

        try {

            //flag=>    1: order, 2: app

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            editor1.setInteger(GlobalData.feedbackKey, flag);


        } catch (Exception e){
            GlobalData.printError(e);
        }

    }

    public static int  getFeedbackKey(Context context){

        int flag=0;
        try {

             //flag=>    1: order, 2: app

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            flag=  editor1.getInteger(GlobalData.feedbackKey, 1);


        } catch (Exception e){
            GlobalData.printError(e);
        }

        return  flag;
    }



    public static void  setMemSettingkey(Context context, int flag){

        try {

            //flag=>    0: Internal Memory , 1: External memory

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            editor1.setInteger(GlobalData.memSetKey, flag);


        } catch (Exception e){
            GlobalData.printError(e);
        }

    }

    public static int  getMemSettingkey(Context context){

        int flag=0;
        try {

            //flag=>    0: Internal Memory , 1: External memory

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            flag=  editor1.getInteger(GlobalData.memSetKey, 1);


        } catch (Exception e){
            GlobalData.printError(e);
        }

        return  flag;
    }

    public static void  setRemMeLoginkey(Context context, boolean flag){

        try {

            //flag=>    false: Internal Memory , true External memory

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            editor1.setBoolean(GlobalData.remFlag, flag);


        } catch (Exception e){
            GlobalData.printError(e);
        }

    }

    public static boolean  getRemMekey(Context context){

        boolean flag=false;
        try {

            //flag=>    0: Internal Memory , 1: External memory

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            flag=  editor1.getPreferenceBoolean(GlobalData.remFlag, false);


        } catch (Exception e){
            GlobalData.printError(e);
        }

        return  flag;
    }


    public static void  setUploadkey(Context context, int flag){

        try {

            //flag=>    3: Upload Anytime , 1: Wifi , 2: Network, 4: Ask Before Uploading

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            editor1.setInteger(GlobalData.upLoadFlag, flag);


        } catch (Exception e){
            GlobalData.printError(e);
        }

    }

    public static int  getUploadkey(Context context){

        int flag=0;
        try {

            //flag=>    0: Internal Memory , 1: External memory

            SharedPreferencesUtility  editor1 = new SharedPreferencesUtility(context);
            flag=  editor1.getInteger(GlobalData.upLoadFlag, 1);


        } catch (Exception e){
            GlobalData.printError(e);
        }

        return  flag;
    }




    // Logout Content

    public  static  void logoutTask(Context context){

        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();

            SharedPreferencesUtility  sharedPreferencesUtility=new SharedPreferencesUtility(context);
            sharedPreferencesUtility.setString(GlobalData.userKey, "");

            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = settings.edit();
            prefsEditor.putString("users", "");
            prefsEditor.commit();



            GlobalData.userSelected=new Users();

            db.deleteUsers();
            db.deleteCompany_info();
            db.deleteCompany_location();
            db.deleteNotification();



            GlobalData.popupWindow=null;
        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            db.close();
        }
    }



//    public static Users userSelected(Context context){
//
//        Users_JSON users_json=new Users_JSON();
//        Users users=new Users();
//        SharedPreferencesUtility sharedPreferencesUtility=new SharedPreferencesUtility(context);
//        try{
//
//            try {
//                users_json.parseJSON(new JSONObject(sharedPreferencesUtility.getString(GlobalData.userKey, "")));
//
//            }catch (Exception e){
//                GlobalData.printError(e);
//            }
//
//        }catch (Exception e){
//            GlobalData.printError(e);
//        }
//
//
//    }

    public static String getPhoto(Context context) {
        String photo=" ";

        try {
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String json = settings.getString("users", "");
            Gson gson = new Gson();
            Users obj = gson.fromJson(json, Users.class);
            photo=obj.getPhoto();


        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return photo;
    }



    public static String getEmail(Context context) {
        String email=" ";

        try {
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String json = settings.getString("users", "");
            Gson gson = new Gson();
            Users obj = gson.fromJson(json, Users.class);
            email=obj.getEmail();


        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return email;
    }


    public static void setPopupWindow(final Context context, RelativeLayout imageView) {
        if (popupWindow != null) {
            if (popupWindow.isShowing() == true)
                popupWindow.dismiss();
            else {
                popupWindow = null;
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popup, null);
                popupWindow = new PopupWindow(
                        popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ListView lst_notifications = (ListView) popupView.findViewById(R.id.lst_change);
                TextView view_all = (TextView) popupView.findViewById(R.id.view_all);
//                NotificationsActivity.setAdapaterData(context);

                DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                try {
                    db.open();

                    list = db.getNotification(4);
                    NotificationListAdapter adpt = new NotificationListAdapter(context, list);
                    lst_notifications.setAdapter(adpt);

                } catch (Exception e) {
                    GlobalData.printError(e);
                } finally {
                    db.close();
                }
                if (list != null) {
                    if (list.size() > 3) {
                        view_all.setVisibility(View.VISIBLE);
                        view_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, NotificationsActivity.class);
                                context.startActivity(intent);
                            }
                        });
                    }

                    lst_notifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //  Toast.makeText(context, "Selected index " + position, Toast.LENGTH_LONG).show();
                            int noti_id = list.get(position).getNoti_id();
                            DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                            try {
                                db.open();
                                db.updateReadNotification(noti_id);

                                try{
                                    if(list.get(position).getOrder_id()>0 && list.get(position).getAssignment_no().length()>0){

                                        Intent intent = new Intent(context, OrderDetailsActivity.class);
                                        intent.putExtra("order_id", list.get(position).getOrder_id());

                                        System.out.print(list.get(position).getOrder_id());
                                        intent.putExtra("assignment_no", list.get(position).getAssignment_no());
                                        context.startActivity(intent);

                                    }

                                }catch (Exception e){
                                    GlobalData.printError(e);
                                }
                            } catch (Exception e) {
                                GlobalData.printError(e);
                            } finally {
                                db.close();
                            }

                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(imageView, 50, 0);
                } else {
                    CommonUtil.showToast(context, "No notifications available");
                }

            }
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(
                    popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView lst_notifications = (ListView) popupView.findViewById(R.id.lst_change);
            TextView view_all = (TextView) popupView.findViewById(R.id.view_all);
//                NotificationsActivity.setAdapaterData(context);

            DatabaseHandlerNew db = new DatabaseHandlerNew(context);
            try {
                db.open();

                list = db.getNotification(4);
                NotificationListAdapter adpt = new NotificationListAdapter(context, list);
                lst_notifications.setAdapter(adpt);

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                db.close();
            }
            if (list != null) {
                if (list.size() > 3) {
                    view_all.setVisibility(View.VISIBLE);
                    view_all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, NotificationsActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }

                lst_notifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  Toast.makeText(context, "Selected index " + position, Toast.LENGTH_LONG).show();
                        int noti_id = list.get(position).getNoti_id();
                        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                        try {
                            db.open();
                            db.updateReadNotification(noti_id);
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        } finally {
                            db.close();
                        }

                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(imageView, 50, 0);
            } else {
                CommonUtil.showToast(context, "No notifications available");
            }

        }

    }


    public static int  getImage(String fileStatus){

        int  drawable    =0;


        switch (fileStatus){

           // File Status
            case "complete":
                drawable = R.drawable.delivered_completed;

                break;
            case "failed":
                drawable = R.drawable.upload_failed;

                break;
            case "qcheck":
                drawable = R.drawable.flag_icon;

                break;
            case "uploaded":
                drawable = R.drawable.upload_successful;

                break;
            case "uploading":
                drawable = R.drawable.audio_uploaded_icon;

                break;
            case "progress":
                drawable = R.drawable.accelerator_icon;

                break;







            // Order Status

            case "aud-pen":
                drawable = R.drawable.audio_uploaded_icon;

                break;

            case "can":
                drawable = R.drawable.upload_failed;

                break;


            case "conf":
                drawable = R.drawable.order_placed_icon;

                break;

            case "del":
                drawable = R.drawable.delivered_completed;

                break;

            case "del-prog":
                drawable = R.drawable.delivered_completed;

                break;

            case "prog":
                drawable = R.drawable.delivered_completed;

                break;

            case "rej":
                drawable = R.drawable.upload_failed;

                break;







        }



        return  drawable;

    }


    public static long getTimeZoneOffset()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());


        int hour=0;
        int min=0;
        long gmtTime=0;
        boolean addFlag=false;


        try {
            hour = Integer.parseInt(timeZone.substring(1, 3));
        }catch (Exception e){
            GlobalData.printError(e);
        }


        try {
            min = Integer.parseInt(timeZone.substring(3, 5));
        }catch (Exception e){
            GlobalData.printError(e);
        }

        try {
           if(timeZone.substring(0,1).equalsIgnoreCase("-")){
               addFlag = false;
           }else {

               addFlag = true;
           }
        }catch (Exception e){
            GlobalData.printError(e);
        }

        gmtTime = TimeUnit.HOURS.toMillis(hour) + TimeUnit.MINUTES.toMillis(min);



        if(!addFlag){
            gmtTime = gmtTime * -1;

        }


        return gmtTime;
    }


    public static String getAddedTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());


        return timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5);
    }





    public static String getLocalToIndianStanderdTime(String timeStamp) {

        String timeDate = timeStamp;
        Calendar calendarDateTime = Calendar.getInstance();
        try {
            calendarDateTime = getStanderdDateFormt(timeStamp);


            if(! calendarDateTime.getTimeZone().getID().equalsIgnoreCase("Asia/Calcutta")) {

                timeDate = formatDateToString(calendarDateTime.getTime(), "yyyy-MM-dd hh:mm:ss", "Asia/Calcutta");

                GlobalData.printMessage("IST Time:" + timeDate);
            }

//            System.out.println("System Date in IST: "+formatDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd hh:mm:ss", "Asia/Calcutta"));

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return  timeDate;
    }



    public static String getIndianToLocalStanderdTime(String timeStamp) {

        String timeDate = "";
        Calendar calendarDateTime = Calendar.getInstance();
        try {

            calendarDateTime = getStanderdDateFormt(timeStamp);


            if(     calendarDateTime.getTimeZone().getID().equalsIgnoreCase("Asia/Calcutta")) {

                return  timeStamp;
            }




            GlobalData.printMessage("Server Iime :" +calendarDateTime.getTime().toString() );


/// Minuse Indian Standard Time Zone
            long time=  TimeUnit.HOURS.toMillis(5)+TimeUnit.MINUTES.toMillis(30);
            calendarDateTime.setTimeInMillis(calendarDateTime.getTimeInMillis()- time);

            //Added Indian  Standard Time Zone

            GlobalData.printMessage("Remove IST Iime :" + calendarDateTime.getTime().toString());

            calendarDateTime.setTimeInMillis(calendarDateTime.getTimeInMillis() + getTimeZoneOffset());

            GlobalData.printMessage("Added Local Iime :" +calendarDateTime.getTime().toString() );

//            GlobalData.printMessage("Local Time:" + calendarDateTime.getTime().toString()+"   Zone Id: "+calendarDateTime.getTimeZone().getID());
//            calendarDateTime.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
//            GlobalData.printMessage("IST Time:" + calendarDateTime.getTime().toString()+"   Zone Id: "+calendarDateTime.getTimeZone().getID());

            timeDate = formatDateToString(calendarDateTime.getTime(), "yyyy-MM-dd hh:mm:ss","Atlantic/Azores", "");

            GlobalData.printMessage("Local Time:" + timeDate);


//            System.out.println("System Date in IST: "+formatDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd hh:mm:ss", "Asia/Calcutta"));

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return  timeDate;
    }









    public static String formatDateToString(Date date, String format,
                                            String timeZone) {
        // null check
        if (date == null) return null;
        // create SimpleDateFormat object with input format
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        // default system timezone if passed null or empty
        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            timeZone = Calendar.getInstance().getTimeZone().getID();
        }
        // set timezone to SimpleDateFormat
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        // return Date in required format with timezone as String
        return sdf.format(date);
    }

    public static String formatDateToString(Date date, String format,String fromtimeZone,
                                            String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date currentdate=new Date();
        try {


        // null check
        if (date == null) return null;
        // create SimpleDateFormat object with input format
         sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("" + fromtimeZone));

        // default system timezone if passed null or empty

         String dateFrom =sdf.format(date);

            SimpleDateFormat outputFormat2 = new SimpleDateFormat(format);
             currentdate = outputFormat2.parse(dateFrom);

            if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            timeZone = Calendar.getInstance().getTimeZone().getID();
        }



        // set timezone to SimpleDateFormat
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

        }catch (Exception e){
            GlobalData.printError(e);
        }
        // return Date in required format with timezone as String
        return sdf.format(currentdate);
    }


//    public static int getFileStatus(String fileStatus) {
//
//        int color = 0;
//
//
//        switch (fileStatus) {
//
//            // File Status
//            case "complete":
//                color = R.color.grayButtonText;
//
//                break;
//            case "failed":
//                color = R.color.red;
//
//                break;
//            case "qcheck":
//                color = R.color.colorPrimary;
//
//                break;
//            case "uploaded":
//                color = R.color.green;
//
//                break;
//            case "uploading":
//                color = R.color.color_upload_audio;
//
//                break;
//            case "progress":
//                color = R.color.colorAccent;
//
//                break;
//
//
//            // Order Status
//
//            case "aud-pen":
//                color = R.color.red;
//
//                break;
//
//            case "can":
//                color = R.color.red;
//
//                break;
//
//
//            case "conf":
//                color = R.color.red;
//
//                break;
//
//            case "del":
//                color = R.color.red;
//
//                break;
//
//            case "del-prog":
//                color = R.color.red;
//
//                break;
//
//            case "prog":
//                color = R.color.red;
//
//                break;
//
//            case "rej":
//                color = R.color.red;
//
//                break;
//
//
//        }
//
//
//        return color;
//
//    }


    public static  void setLoginAndLogout(final Context context,NavigationView navigationView, final Intent intent){

       final boolean isLoggedIn= GlobalData.isLoggedIn(context);
        try {


            View headerView = LayoutInflater.from(context).inflate(R.layout.nav_header_main, null);
            navigationView.addHeaderView(headerView);


            TextView txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
            TextView txt_email = (TextView) headerView.findViewById(R.id.txt_email);
            TextView txt_mem_id = (TextView) headerView.findViewById(R.id.txt_mem_id);
            CircularSmartImageView img_user_photo = (CircularSmartImageView) headerView.findViewById(R.id.img_user_photo);


            if (isLoggedIn) {
                navigationView.inflateMenu(R.menu.activity_menu_drawer);
                Users memberId = GlobalData.getMemberId(context);
                String photo = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + memberId.getPhoto());

                txt_header_login.setText("Logout");
                txt_email.setText(memberId.getEmail());
                txt_mem_id.setText(memberId.getMembership_id());

                img_user_photo.setImageUrl(photo, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);


            } else
            {
                navigationView.inflateMenu(R.menu.activity_menu_drawer);
                txt_header_login.setText("Login");
                txt_email.setVisibility(View.GONE);
                txt_mem_id.setVisibility(View.GONE);

            }


            img_user_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLoggedIn) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("Name", context.getClass().getName());
                        context.startActivity(intent);
                    }
                }
            });


            txt_header_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLoggedIn) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("Name", context.getClass().getName());
                        context.startActivity(intent);
                    } else {

                        try {

                            if (!GlobalData.isNetworkAvailable(context)) {
                                Toast.makeText(context, context.getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();

                            } else {
                                new LogoutWebServiceCall(context, intent).execute();

//                                Intent intent = new Intent(context, HomeActivity.class);
//                                context.startActivity(intent);
//                                ((Activity) context).finish();


                            }


                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }

                    }
                }
            });

        }catch (Exception e){
            GlobalData.printError(e);
        }
    }


    public static int  getFileStatusColor(String fileStatus) {

        int  color = R.color.color_green;;


        switch (fileStatus) {

// File Status
            case "failed":
                color = R.color.failed;

                break;

            case "#uploading":
                color = R.color.uploading;
                break;


        }


        return color;

    }


    public static int  getColor(String fileStatus){

        int  drawable    =0;


        switch (fileStatus){

            // File Status
            case "complete":
                drawable = R.drawable.delivered_completed;

                break;
            case "failed":
                drawable = R.drawable.upload_failed;

                break;
            case "qcheck":
                drawable = R.drawable.flag_icon;

                break;
            case "uploaded":
                drawable = R.drawable.upload_successful;

                break;
            case "uploading":
                drawable = R.drawable.audio_uploaded_icon;

                break;
            case "progress":
                drawable = R.drawable.accelerator_icon;

                break;







            // Order Status

            case "aud-pen":
                drawable = R.drawable.audio_uploaded_icon;

                break;

            case "can":
                drawable = R.drawable.upload_failed;

                break;


            case "conf":
                drawable = R.drawable.order_placed_icon;

                break;

            case "del":
                drawable = R.drawable.delivered_completed;

                break;

            case "del-prog":
                drawable = R.drawable.delivered_completed;

                break;

            case "prog":
                drawable = R.drawable.delivered_completed;

                break;

            case "rej":
                drawable = R.drawable.upload_failed;

                break;







        }



        return  drawable;

    }
}
