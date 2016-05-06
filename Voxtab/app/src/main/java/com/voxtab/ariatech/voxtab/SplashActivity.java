package com.voxtab.ariatech.voxtab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.DeviceInfo;
import com.voxtab.ariatech.voxtab.globaldata.GetMasterData;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import java.util.Locale;

public class SplashActivity extends Activity {

    private Context context;
    private Handler handler;

    SharedPreferencesUtility sharedPreferencesUtility;
    private static final int PERMISSION_REQUEST_CODE = 1;
    int flag_audio_permission = 0;

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GlobalData.activities.add(SplashActivity.this);
        context = SplashActivity.this;
        sharedPreferencesUtility=new SharedPreferencesUtility(context);
        GlobalData.LANGUAGE = Locale.getDefault().getLanguage();

        ChangeLanguage.setLocaleLanguage(SplashActivity.this);

        Log.e(" LANGUAGE ", GlobalData.LANGUAGE);

        GlobalData.setUserData(context);

        GlobalData.deviceInfo=new DeviceInfo(context);
        // Set device Information
        GlobalData.setDeviceInfo(context);
       /* DatabaseHandler db = new DatabaseHandler(this);
        db.initDatabase();*/

        layout=(RelativeLayout)findViewById(R.id.layout);
        // Check the logout

            try {
                if(GlobalData.getRemMekey(context)){
                    GlobalData.logoutTask(context);
                }
            }catch (Exception e){
                GlobalData.printError(e);
            }

        handler = new Handler();
        handler.postDelayed(r, 3000);





        // Set User Data

    }


    private Runnable r = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub
            /**/

            AccessPermission();
//
//            startActivity(new Intent(SplashActivity.this,
//                    HomeActivity.class));
            /*startActivity(new Intent(SplashActivity.this,
                    LoginActivity.class));*/


            /*if (CommonUtil.getSharePreferenceString(context, GlobalData.SHARE_LEVEL, "").equalsIgnoreCase("1")) {
                startActivity(new Intent(SplashActivity.this,
                        MenuActivity.class));
                finish();
            }*//* else {
                startActivity(new Intent(SplashActivity.this,
                        SignInActivity.class));
                finish();
            }*/

        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (handler != null) {
                handler.removeMessages(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    if(selfPermissionGranted(permissions[0])){

                    flag_audio_permission = 1;

                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecAudioFlag, flag_audio_permission);
                    //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();

                    //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));
                    setAction();


                } else {

                    flag_audio_permission = 0;
                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecAudioFlag, flag_audio_permission);
                    //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                    //    Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                }


                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

//                    if(selfPermissionGranted(permissions[0])){

                    flag_audio_permission = 1;

                    sharedPreferencesUtility.setInteger(GlobalData.permissionDeviceInfo, flag_audio_permission);
                    //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();

                    //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));
                    setAction();


                } else {

                    flag_audio_permission = 0;
                    sharedPreferencesUtility.setInteger(GlobalData.permissionDeviceInfo, flag_audio_permission);
                    //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                    //    Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                }
                break;
        }
    }


    private void permissionCheck() {


        if ( (sharedPreferencesUtility.getInteger(GlobalData.permissionDeviceInfo, 0) == 0 || sharedPreferencesUtility.getInteger(GlobalData.permissionDeviceInfo, 0) == 0 ) && Build.VERSION.SDK_INT >= 23) {

            AccessPermission();


        } else {


//            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
            setAction();

        }

    }


    private void AccessPermission() {


        int firstTime = sharedPreferencesUtility.getInteger(GlobalData.permissionDeviceInfo, 0);

        if (firstTime == 0 && Build.VERSION.SDK_INT >= 23) {


//                    if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
            if (GlobalData.selfPermissionGranted(context, Manifest.permission.READ_PHONE_STATE)&& GlobalData.selfPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //  Toast.makeText(context, "This permission allows us to access phone audio recorder. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

                setAction();

            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CHANGE_NETWORK_STATE}, PERMISSION_REQUEST_CODE);

            }

        } else {




            setAction();
        }


    }



    void setAction(){


        RecorderApplication.configureLog4j();

        int count=0;
        DatabaseHandlerNew db=new DatabaseHandlerNew(context);
        try {
            db.open();

            count = db.getDelivery_optionCount();


        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            db.close();
        }

        if(count<=0) {

            if (GlobalData.isNetworkAvailable(context) ) {



                new GetMasterData(context,true).execute();


            }else {

                GlobalData.showSnackBar(layout, context.getResources().getString(R.string.no_content), true);



            }
            return;

        }else {
            new GetMasterData(context, false).execute();
        }


        // Set device Information
        GlobalData.deviceInfo=new DeviceInfo(context);
        // Set device Information
        GlobalData.setDeviceInfo(context);

        int pageId=0;

        try{
        pageId=getIntent().getIntExtra("page_id",0);


        }catch (Exception e){
            GlobalData.printError(e);
        }


        switch (pageId){
            case  0:
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                break;

            case 1:
                try {
                    int order_id=getIntent().getIntExtra("order_id",0);
                    String assignment_no=getIntent().getStringExtra("assignment_no");

                   Intent intent=new Intent(context, OrderDetailsActivity.class);

                    intent.putExtras(getIntent().getExtras());


                    startActivity(new Intent(intent));


                }catch (Exception e){
                    GlobalData.printError(e);
                }

                break;

            case 2:
                try {

                    Intent intent=new Intent(context, OrderHistoryActivity.class);

                    intent.putExtras(getIntent().getExtras());


                    startActivity(new Intent(intent));


                }catch (Exception e){
                    GlobalData.printError(e);
                }

                break;
            default:
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                break;
        }




        finish();
    }

}
