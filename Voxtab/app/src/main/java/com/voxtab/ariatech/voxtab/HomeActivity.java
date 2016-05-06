package com.voxtab.ariatech.voxtab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ariatech.lib_project.CommonUtil;
import com.dropbox.chooser.android.DbxChooser;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;

    ImageView img_add_audio, img_start_recording;
    RelativeLayout img_noti;

    Button btn_start_recording, btn_add_audio;

    public LogoutWebServiceCall obj = null;


    static final String APP_KEY = "52k0xv3y3xphw16";
    static final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
    private DbxChooser mChooser;

    private AddMyRecordings add_audio = null;
    Uri _uri;

    String file_name = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    int flag_audio_permission = 0;
    SharedPreferencesUtility sharedPreferencesUtility;

    int selectedAction = 1;
    private boolean isLoggedIn;
    private TextView txt_header_login, txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(HomeActivity.this);
        setContentView(R.layout.activity_main);

        context = this;

        mChooser = new DbxChooser(APP_KEY);
        sharedPreferencesUtility = new SharedPreferencesUtility(context);

        GlobalData.activities.add(HomeActivity.this);

        toolbar = GlobalData.initToolBarMenu(this, true, true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());

//        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//
//
//        txt_email = (TextView) headerView.findViewById(R.id.txt_email);
        isLoggedIn = GlobalData.isLoggedIn(context);
//
//
//        CircularSmartImageView img_user_photo = (CircularSmartImageView) headerView.findViewById(R.id.img_user_photo);
//
//        if (isLoggedIn) {
//            navigationView.inflateMenu(R.menu.activity_main_drawer);
//            Users memberId = GlobalData.getMemberId(context);
//            String photo = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + memberId.getPhoto());
//
//            txt_header_login.setText(memberId.getMembership_id());
//            txt_email.setText(memberId.getEmail());
//            img_user_photo.setImageUrl(photo, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);
//
//
//        } else {
//            navigationView.inflateMenu(R.menu.activity_menu_drawer_login);
//        }
//
//        init();
//        txt_header_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLoggedIn) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.putExtra("Name", context.getClass().getName());
//                    startActivity(intent);
//                }
//            }
//        });
//        View header = navigationView
//        txt_header_login = (TextView) header.findViewById(R.id.txt_header_login);
//        txt_header_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        init();

        GlobalData.printMessage("IST Time :"+GlobalData.getIndianToLocalStanderdTime(GlobalData.getStanderdDateFormt(Calendar.getInstance())));
    }

    private void init() {

        btn_start_recording = (Button) findViewById(R.id.btn_start_recording);
        img_start_recording = (ImageView) findViewById(R.id.img_start_recording);
        img_start_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAction = 0;
                permissionCheck();
            }
        });
        btn_start_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAction = 0;
                permissionCheck();
            }
        });
        btn_add_audio = (Button) findViewById(R.id.btn_add_audio);
        img_add_audio = (ImageView) findViewById(R.id.img_add_audio);
        btn_add_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAction = 1;
                permissionCheck();
            }
        });
        img_add_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedAction = 1;
                permissionCheck();
            }
        });


    }

    public void addAudio() {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, 1);

        /*IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder()
                .setMimeType(new String[] { DriveFolder.MIME_TYPE })  // <--- FOLDER
                        //.setMimeType(new String[] { "text/plain", "text/html" }) // <- TEXT FILES
                .build(getGoogleApiClient());*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            try {
                if (resultCode == RESULT_OK) {
                    //the selected audio.
                    _uri = data.getData();
                    String filePath = null;

                    Log.d("", "URI = " + _uri);
                    if (_uri != null && "content".equals(_uri.getScheme())) {
                        Cursor cursor = this.getContentResolver().query(_uri, new String[]{MediaStore.Audio.AudioColumns.DATA}, null, null, null);
                        cursor.moveToFirst();
                        filePath = cursor.getString(0);
                        cursor.close();
                    } else {
                        filePath = _uri.getPath();
                    }


                    if (filePath == null) {
                        filePath = GlobalData.getPath(context, _uri);
                    }

                    Log.d("", "Chosen path = " + filePath);
               /* if (uri != null) {
                    if (uri.toString().startsWith("file:")) {
                        file_name = uri.getPath();
                    } else { // uri.startsWith("content:")
                        Cursor c = getContentResolver().query(uri, null, null, null, null);
                        if (c != null && c.moveToFirst()) {
                            int id = c.getColumnIndex(MediaStore.Audio.Media.DATA);
                            if (id != -1) {
                                file_name = c.getString(id);
                            }
                        }
                    }*/

                    String scheme = _uri.getScheme();
                    String d = "", s = "", date = "";
                    if (scheme.equals("file")) {
                        file_name = _uri.getLastPathSegment();


                        if (file_name == null && filePath.length() > 0) {

                            try {
                                File userFile = new File(filePath);
                                if (userFile.isFile()) {
                                    file_name = userFile.getName();
                                    if (file_name.indexOf(".") > 0) {
                                        file_name = file_name.substring(0, file_name.lastIndexOf("."));
                                    }
                                }


                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }
                        }
                        //size
                        File file = new File(filePath);
                        long size = file.length();
                        s = String.valueOf(size);

                        //duration
                        MediaPlayer mp = MediaPlayer.create(HomeActivity.this, Uri.parse(filePath));
                        int duration = mp.getDuration();
                        d = GlobalData.convertSecondsToHMmSs(duration);

                        //date
                        Date createDate = new Date();
                        date = GlobalData.dateDDMMYYYY(new Date());
//                        Date lastModDate = new Date(file.lastModified());
//                        date=lastModDate.toString();
                        Log.i("File last modified @ : ", date);


                    } else if (scheme.equals("content")) {
                        String[] proj = {MediaStore.Audio.Media.TITLE};
                        Cursor cursor = context.getContentResolver().query(_uri, proj, null, null, null);
                        if (cursor != null && cursor.getCount() != 0) {
                            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                            cursor.moveToFirst();
                            file_name = cursor.getString(columnIndex);

                            //size
                            File file = new File(filePath);
                            long size = file.length();
                            s = String.valueOf(size);

                            //duration
                            MediaPlayer mp = MediaPlayer.create(HomeActivity.this, Uri.parse(filePath));
                            int duration = mp.getDuration();
                            d = GlobalData.convertSecondsToHMmSs(duration);

                            //date
                            Date createDate = new Date();
                            date = createDate.toString();
//                            Date lastModDate = new Date(file.lastModified());
//                            date=lastModDate.toString();
                            Log.i("File last modified @ : ", date);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }

                    MyRecording myRecording = new MyRecording();
                    myRecording.setRecName(file_name);
                    myRecording.setRecDesc("");
                    myRecording.setRecSize(s);
                    myRecording.setCreatedDate(date);
                    myRecording.setServerId(0);
                    myRecording.setRecDuration(d);
                    myRecording.setUserId(GlobalData.userSelected.getUser_id());
                    myRecording.setUpMasterId("");
                    myRecording.setSourceTypeId(getResources().getString(R.string.ext_storage));
                    myRecording.setRecLocalPath("" + filePath);
                    myRecording.setUploadingConnectionMode("");
                    myRecording.setSourceLink(filePath);
                    myRecording.setRecUploadDuration(d);
                    add_audio = new AddMyRecordings(myRecording);
                    add_audio.execute((Void) null);
                }
            } catch (Exception e) {
                GlobalData.printError(e);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void AccessAudioRecorderPermission() {


        int firstTime = sharedPreferencesUtility.getInteger(GlobalData.permissionRecAudioFlag, 0);

        if (firstTime == 0 && Build.VERSION.SDK_INT >= 23) {


//                    if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
            if (GlobalData.selfPermissionGranted(context, Manifest.permission.RECORD_AUDIO) && GlobalData.selfPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //  Toast.makeText(context, "This permission allows us to access phone audio recorder. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

                selectedAtion();

            } else {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            }

        } else {


            selectedAtion();


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
                    startActivity(new Intent(HomeActivity.this, RecordingActivity.class));


                } else {

                    flag_audio_permission = 0;
                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecAudioFlag, flag_audio_permission);
                    //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                    //    Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                }


                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

//                    if(selfPermissionGranted(permissions[0])){

                    flag_audio_permission = 1;

                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, flag_audio_permission);
                    //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();

                    //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));
                    startActivity(new Intent(HomeActivity.this, RecordingActivity.class));


                } else {

                    flag_audio_permission = 0;
                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, flag_audio_permission);
                    //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                    //    Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_notifications) {

            if (isLoggedIn) {
                Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(HomeActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(HomeActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }

        } /*else if (id == R.id.nav_home) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);

        }*/ else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//
//
//            Intent intent = new Intent(HomeActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(HomeActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(HomeActivity.this, AboutusActivity.class);
            startActivity(intent);

        }
//        else if (id == R.id.nav_logout) {
//            SharedPreferences settings = PreferenceManager
//                    .getDefaultSharedPreferences(context);
//            SharedPreferences.Editor prefsEditor = settings.edit();
//            prefsEditor.putString("users", "");
//            prefsEditor.commit();
//
//
//            try {
//
//                Map<String, String> networkDetails = getConnectionDetails();
//                if (networkDetails.isEmpty()) {
//                    GlobalData.showSnackBar(btn_add_audio, getResources().getString(R.string.ERR_CONNECTION), false);
//                } else {
//                    obj = new LogoutWebServiceCall();
//                    obj.execute();
//                }
//
//
//            } catch (Exception e) {
//                GlobalData.printError(e);
//            }
//
//
//        }
            else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void permissionCheck() {


        if ((sharedPreferencesUtility.getInteger(GlobalData.permissionRecAudioFlag, 0) == 0 || sharedPreferencesUtility.getInteger(GlobalData.permissionRecStorageFlag, 0) == 0) && Build.VERSION.SDK_INT >= 23) {

            AccessAudioRecorderPermission();


        } else {


//            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
            selectedAtion();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            GlobalData.finishAll();
        } catch (Exception e) {

        }
    }

    /*  @Override
      public void onBackPressed() {
          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          if (drawer.isDrawerOpen(GravityCompat.START)) {
              drawer.closeDrawer(GravityCompat.START);
          } else

          {
              //super.onBackPressed();
              new AlertDialog.Builder(this)
                      // .setTitle(getString(R.string.app_name))
                      .setMessage(getString(R.string.app_close_msg))
                      .setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"))
                      .setNegativeButton("No", null)
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                          public void onClick(DialogInterface arg0, int arg1) {
  //                            HomeActivity.super.onBackPressed();
                              finish();
                              System.exit(0);
                          }
                      }).create().show();
          }
      }
  */
    public class AddMyRecordings extends AsyncTask<Void, Void, Boolean> {

        MyRecording myRecording1;


        AddMyRecordings(MyRecording myRecording) {
            myRecording1 = myRecording;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean f = true;
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            try {
                databaseHandler.open();
                databaseHandler.addRecording(myRecording1);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return f;


        }

        @Override
        protected void onPostExecute(final Boolean bean) {
            add_audio = null;
            if (bean != null) {
                Intent intent = new Intent(HomeActivity.this, MyRecordingsActivity.class);
                startActivity(intent);
            }

        }

        protected void onCancelled() {
            add_audio = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    }


    public void selectedAtion() {


        try {

            if (selectedAction == 1) {
                // Add Audio from External Device
                addAudio();

            } else {
                //Add audio from recording
                startActivity(new Intent(HomeActivity.this, RecordingActivity.class));
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }


    // Logout
    public class LogoutWebServiceCall extends AsyncTask<Users, WebServiceResonse, WebServiceResonse> {

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse resonse = new WebServiceResonse();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                webServiceMySQl = new WebServiceMySQl(context);

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(Users... params) {
            try {
                resonse = webServiceMySQl.Logout();


            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }


        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            obj = null;

            try {


                if (res.getStatus() == 200) {


                    finish();
                    startActivity(getIntent());


                }
                GlobalData.showSnackBar(btn_add_audio, res.getMessage(), true);


            } catch (Exception e) {
                GlobalData.printError(e);
            }


        }
    }


    private Map<String, String> getConnectionDetails() {
        Map<String, String> networkDetails = new HashMap<String, String>();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {

                networkDetails.put("Type", wifiNetwork.getTypeName());
                networkDetails.put("Sub type", wifiNetwork.getSubtypeName());
                networkDetails.put("State", wifiNetwork.getState().name());
            }

            NetworkInfo mobileNetwork = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                networkDetails.put("Type", mobileNetwork.getTypeName());
                networkDetails.put("Sub type", mobileNetwork.getSubtypeName());
                networkDetails.put("State", mobileNetwork.getState().name());
                if (mobileNetwork.isRoaming()) {
                    networkDetails.put("Roming", "YES");
                } else {
                    networkDetails.put("Roming", "NO");
                }
            }
        } catch (Exception e) {
            networkDetails.put("Status", e.getMessage());
        }
        return networkDetails;
    }
}



