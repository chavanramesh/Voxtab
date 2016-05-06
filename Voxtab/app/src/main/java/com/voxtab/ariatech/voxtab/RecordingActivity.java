package com.voxtab.ariatech.voxtab;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.github.lassana.recorder.AudioRecorder;
import com.github.lassana.recorder.AudioRecorderBuilder;
import com.voxtab.ariatech.voxtab.audio.AudioDataReceivedListener;
import com.voxtab.ariatech.voxtab.audio.RecordingThread;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;
import com.voxtab.ariatech.voxtab.views.PausableChronometer;
import com.voxtab.ariatech.voxtab.views.WaveformView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Local User on 28-Jan-16.
 */
public class RecordingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private Context context;
    private Toolbar toolbar;
    SharedPreferencesUtility sharedPreferencesUtility;

    private static NotificationManager mNotifyManager;
    private static NotificationCompat.Builder mBuilder;
    int id = 1;
    boolean isNotify = false;
    private WaveformView mRealtimeWaveformView;
    private RecordingThread mRecordingThread;

    View view;
    LinearLayout lin1;
    long space = 0;
    String hh = "", mm = "", ss = "";

    ImageView record_play, record_stop, record_start, record_pause, btn_delete;
    private static MediaRecorder myAudioRecorder;

    private File root, sdroot;
    private String dir;
    private String audiopath;

    String rercord_name, fileName;

    private AddMyRecordings add_records = null;
    PausableChronometer myChronometer;
    private static final int PERMISSION_REQUEST_CODE = 2;

    //Recorder

    private static final int REQUEST_CODE_PERMISSIONS = 0x1;

    private AudioRecorder mAudioRecorder = null;
    private Uri mAudioRecordUri;
    private String mActiveRecordFileName;
    private boolean isLoggedIn;
    private TextView txt_header_login, txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordingscreen);
        context = this;

        id=(int)System.currentTimeMillis();

        GlobalData.activities.add(RecordingActivity.this);

        toolbar= GlobalData.initToolBarMenu(this, true, true);
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());

//        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
        isLoggedIn = GlobalData.isLoggedIn(context);
//        CircularSmartImageView img_user_photo = (CircularSmartImageView) headerView.findViewById(R.id.img_user_photo);
//        TextView  txt_email = (TextView) headerView.findViewById(R.id.txt_email);
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
//        txt_header_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLoggedIn) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        init();
        AccessStoragePermission();

    }

    private void AccessStoragePermission() {


        int firstTime = sharedPreferencesUtility.getInteger(GlobalData.permissionRecStorageFlag, 0);

        if (firstTime == 0 && Build.VERSION.SDK_INT >= 23) {


            if (GlobalData.selfPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //  Toast.makeText(context, "This permission allows us to access phone audio recorder. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

                setFileStorage();

            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            }

        } else {


            setFileStorage();
            //startActivity(new Intent(HomeActivity.this, RecordingActivity.class));
        }


    }


    @Override
    public void onDestroy() {

        try {
            if (mAudioRecorder.isRecording()) {
                mAudioRecorder.cancel();
                setResult(Activity.RESULT_CANCELED);
            }
        } catch (Exception e) {
            GlobalData.printError(e);
        }

        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        try {


            if (id == R.id.nav_notifications) {
                if (isLoggedIn) {
                    Intent intent = new Intent(RecordingActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                } else {
//                    Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecordingActivity.this, LoginActivity.class);
                    intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                    startActivity(intent);
                }
            } else if (id == R.id.nav_recordings) {

                Intent intent = new Intent(RecordingActivity.this, MyRecordingsActivity.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_order_history) {

                if (isLoggedIn) {
                    Intent intent = new Intent(RecordingActivity.this, OrderHistoryActivity.class);
                    startActivity(intent);
                } else {
//                    Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecordingActivity.this, LoginActivity.class);
                    intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                    startActivity(intent);
                }
            } else if (id == R.id.nav_settings) {

                if (isLoggedIn) {
                    Intent intent = new Intent(RecordingActivity.this, SettingsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RecordingActivity.this, LoginActivity.class);
                    intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                    startActivity(intent);
                }

            } else if (id == R.id.nav_home) {
                Intent intent = new Intent(RecordingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_help) {


            }

//            else if (id == R.id.nav_confidentiality) {
//                Intent intent = new Intent(RecordingActivity.this, ConfidentialityActivity.class);
//                startActivity(intent);
//
//
//            } else if (id == R.id.nav_terms_condition) {
//
//                Intent intent = new Intent(RecordingActivity.this, TermsActivity.class);
//                startActivity(intent);
//
//            }

            else if (id == R.id.nav_about_us) {

                Intent intent = new Intent(RecordingActivity.this, AboutusActivity.class);
                startActivity(intent);

            }
//            else if (id == R.id.nav_logout) {
//
//
//                try {
//
//
//                    if (!GlobalData.isNetworkAvailable(context)) {
//                        Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();
//
//                    } else {
//                        new LogoutWebServiceCall(context,getIntent()).execute();
//                        Intent intent = new Intent(RecordingActivity.this, HomeActivity.class);
//                        startActivity(intent);
//
//                    }
//
//
//                } catch (Exception e) {
//                    GlobalData.printError(e);
//                }
//
//            }
            else if (id == R.id.nav_feedback) {

                if (isLoggedIn) {
                    Intent intent = new Intent(RecordingActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                } else {
//                    Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecordingActivity.this, LoginActivity.class);
                    intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                    startActivity(intent);
                }


            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void start_recording(String path) {


        try {


           /*
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.reset();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(path);

            myAudioRecorder.prepare();
            myAudioRecorder.start();

            */


            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.reset();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);


            if (Build.VERSION.SDK_INT >= 10) {

                Log.d("Recording", "Recording with Mp4");

                myAudioRecorder.setAudioSamplingRate(44100);
                myAudioRecorder.setAudioEncodingBitRate(96000);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                myAudioRecorder.setOutputFile(audiopath);


            } else {

                // older version of Android, use crappy sounding voice codec
                myAudioRecorder.setAudioSamplingRate(8000);
                myAudioRecorder.setAudioEncodingBitRate(12200);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                myAudioRecorder.setOutputFile(audiopath);
            }

            myAudioRecorder.prepare();
            myAudioRecorder.start();


        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        record_start.setVisibility(View.GONE);
        record_start.setEnabled(false);

        record_pause.setVisibility(View.VISIBLE);
        record_pause.setEnabled(false);

        record_stop.setVisibility(View.VISIBLE);
        record_stop.setEnabled(true);

        btn_delete.setVisibility(View.GONE);
        btn_delete.setEnabled(true);

    }


    public void stop_recording() {
        try {
            myAudioRecorder.stop();
            myChronometer.stop();


            CustomDialogClass cdd = new CustomDialogClass(context);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();
            cdd.setCancelable(false);
            record_stop.setVisibility(View.VISIBLE);
            record_stop.setEnabled(true);

            record_start.setVisibility(View.VISIBLE);
            record_start.setEnabled(true);

            record_pause.setVisibility(View.GONE);
            record_pause.setEnabled(false);


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public void init() {


        mRealtimeWaveformView = (WaveformView) findViewById(R.id.waveformView);
        myChronometer = (PausableChronometer) findViewById(R.id.chronometer);
        lin1 = (LinearLayout) findViewById(R.id.lin_main);


        record_stop = (ImageView) findViewById(R.id.btn_record_stop);
        record_start = (ImageView) findViewById(R.id.btn_record);
        record_pause = (ImageView) findViewById(R.id.btn_record_pause);
        btn_delete = (ImageView) findViewById(R.id.btn_delete);


        record_pause.setVisibility(View.GONE);
        record_pause.setEnabled(true);


        // set Choronometer Data
        myChronometer.setText("00:00:00");
        myChronometer.setTextColor(getResources().getColor(R.color.color_recording_bg));
        myChronometer.setTextSize(getResources().getDimension(R.dimen.timer_font_size));

      /*  record_play.setVisibility(View.VISIBLE);
        record_play.setEnabled(false);
*/
        record_stop.setVisibility(View.VISIBLE);
        record_stop.setEnabled(true);

        btn_delete.setVisibility(View.GONE);

        sharedPreferencesUtility = new SharedPreferencesUtility(RecordingActivity.this);

        String space = GlobalData.convertSecondsToHMmSsS(getAvailableSpaceInKB());
        // GlobalData.showSnackBar(lin1, getString(R.string.free_memory_msg1) + space + "  " + getString(R.string.free_memory_msg2), false);
        Toast.makeText(context, getString(R.string.free_memory_msg1) + space + "  " + getString(R.string.free_memory_msg2), Toast.LENGTH_LONG).show();


        // Recording Wave Generation

//        mRecordingThread = new RecordingThread(new AudioDataReceivedListener() {
//            @Override
//            public void onAudioDataReceived(short[] data) {
//                mRealtimeWaveformView.setSamples(data);
//            }
//        });


        record_start.setImageResource(R.drawable.rec_img);


        record_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    try {
//                        myAudioRecorder.stop();
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                    try {


                        if (mAudioRecorder.isRecording()) {
                            pause();

                            if (myChronometer != null && myChronometer.isRunning()) {
                                myChronometer.stop();
                            }
                        }
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                    CustomDialogClass cdd = new CustomDialogClass(context);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    cdd.setCancelable(false);
//                        record_stop.setVisibility(View.VISIBLE);
//                        record_stop.setEnabled(false);
//                        record_start.setVisibility(View.VISIBLE);
//                        record_start.setEnabled(true);
//
//                        record_pause.setVisibility(View.GONE);
//                        record_pause.setEnabled(false);


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        });
//        record_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                //start_recording(audiopath);
//                initChronometer();
//
//            }
//        });


        // initChronometer();


        record_pause.setVisibility(View.GONE);
        record_pause.setEnabled(true);

        record_start.setVisibility(View.VISIBLE);
        record_start.setEnabled(true);


        record_start.setOnClickListener(this);
        record_pause.setOnClickListener(this);

    }


    public void initChronometer() {

        //AccessStoragePermission();

        try {


//            record_start.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//             /*   try {
//
//                    myAudioRecorder.prepare();
//                    myAudioRecorder.start();
//                    myChronometer.start();
//
//                } catch (IllegalStateException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                record_start.setVisibility(View.GONE);
//                record_start.setEnabled(false);
//
//                record_pause.setVisibility(View.VISIBLE);
//                record_pause.setEnabled(false);
//
//                record_stop.setVisibility(View.VISIBLE);
//                record_stop.setEnabled(true);
//
//                btn_delete.setVisibility(View.VISIBLE);
//                btn_delete.setEnabled(true);*/
//               /* record_play.setVisibility(View.VISIBLE);
//                record_play.setEnabled(false);*/
//
//                    //Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
//                }
//            });


//            record_pause.setOnClickListener(new View.OnClickListener()
//
//                                            {
//                                                @Override
//                                                public void onClick(View v) {
//
//              /*  myAudioRecorder.stop();
//             *//*   myAudioRecorder.release();
//                myAudioRecorder = null;
//                myChronometer.stop();*//*
//                record_stop.setVisibility(View.VISIBLE);
//                record_stop.setEnabled(true);
//
//               *//* record_play.setVisibility(View.VISIBLE);
//                record_play.setEnabled(true);*//*
//
//                record_start.setVisibility(View.VISIBLE);
//                record_start.setEnabled(true);
//
//                record_pause.setVisibility(View.GONE);
//                record_pause.setEnabled(false);
//                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();*/
//             /*   myAudioRecorder.stop();
//                myChronometer.stop();
//                record_stop.setVisibility(View.VISIBLE);
//                record_stop.setEnabled(true);
//                record_start.setVisibility(View.VISIBLE);
//                record_start.setEnabled(true);
//                record_pause.setVisibility(View.GONE);
//                record_pause.setEnabled(false);
//*/
//                                                }
//                                            }
//
//            );
       /* record_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                m = new MediaPlayer();

                try {
                    m.setDataSource(audiopath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();

                record_stop.setVisibility(View.VISIBLE);
                record_stop.setEnabled(true);

               *//* record_play.setVisibility(View.GONE);
                record_play.setEnabled(false);
*//*
                record_start.setVisibility(View.VISIBLE);
                record_start.setEnabled(true);

                record_pause.setVisibility(View.GONE);
                record_pause.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
*/

            btn_delete.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {

                                                  try {
                                                      myAudioRecorder.stop();
                                                      myAudioRecorder.release();

                                                  } catch (Exception e) {
                                                      GlobalData.printError(e);
                                                  }


                                                  myAudioRecorder = null;

                                                  try {
                                                      try {
                                                          File file = new File(audiopath);
                                                          boolean deleted = file.delete();

                                                      } catch (Exception e) {
                                                          e.printStackTrace();
                                                      }
                                                      record_stop.setVisibility(View.VISIBLE);
                                                      record_stop.setEnabled(true);
                                                      // myChronometer.reset();
                                                      myChronometer.setBase(SystemClock.elapsedRealtime());
                                                      myChronometer.start();
                                                      initChronometer();


                                                  } catch (Exception e) {
                                                      GlobalData.printError(e);
                                                  }
                                              }
                                          }

            );

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        AccessStoragePermission();


    }


    public void setFileStorage() {

        try {
            myChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer cArg) {
                    long time = SystemClock.elapsedRealtime() - cArg.getBase();
                    int h = (int) (time / 3600000);
                    int m = (int) (time - h * 3600000) / 60000;
                    int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                    hh = h < 10 ? "0" + h : h + "";
                    mm = m < 10 ? "0" + m : m + "";
                    ss = s < 10 ? "0" + s : s + "";
                    cArg.setText(hh + ":" + mm + ":" + ss);
                    cArg.setTextColor(getResources().getColor(R.color.color_recording_bg));
                    cArg.setTextSize(getResources().getDimension(R.dimen.timer_font_size));
                }
            });


            int counter = sharedPreferencesUtility.getInteger("counter", 0);

            if (counter == 0) {
                counter = 1;
                sharedPreferencesUtility.setInteger("counter", counter);
                rercord_name = String.format("%03d", counter);
            } else {

                counter = counter + 1;
                rercord_name = String.format("%03d", counter);
                sharedPreferencesUtility.setInteger("counter", counter);
            }

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


//            if (prefs.getBoolean(GlobalData.MEMORY, true)) {
//            String secStore = System.getenv("SECONDARY_STORAGE");
//            dir = GlobalData.storageDirectory;
//            try {
//                root = new File(secStore);
//
//                File mkDir = new File(root, dir);
//                mkDir.mkdirs();
//
//            } catch (Exception e) {
//                GlobalData.printError(e);
//            }
//
//            String extStore = System.getenv("EXTERNAL_STORAGE");
//            sdroot = new File(extStore);
//            File sdmkDir = new File(sdroot, dir);
//            sdmkDir.mkdirs();

            root = Environment.getExternalStorageDirectory();
            dir = GlobalData.storageDirectory;
            File mkDir = new File(root, dir);
            mkDir.mkdirs();


            fileName = "Recording_" + rercord_name + "_" + new SimpleDateFormat("ddMMyyyy")
                    .format(new Date()).toString() + ".mp3";

            File filePath = new File(root, dir + fileName.trim());

//            if(!filePath.exists()){
//                filePath.createNewFile();
//            }

            audiopath = filePath.getPath();


            //start_recording(audiopath);
            myChronometer.start();


            setRecorder();
            tryStart();


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void renameFile(String oldname, String newName) {
        File source = new File(oldname);
        File destination = new File(newName);
        boolean success = source.renameTo(destination);
    }


    public static long getAvailableSpaceInKB() {
        long milisec = 0;

        try {
            final long SIZE_KB = 1024L;
            long availableSpace = -1L;
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            long size = availableSpace / SIZE_KB;
            //App time for 1 sec equal to 6KB
            milisec = size / 6;

        } catch (Exception e) {
            GlobalData.printError(e);
        }
        return milisec;
    }

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

            try {


                add_records = null;
                if (bean != null) {
                    Intent intent = new Intent(RecordingActivity.this, MyRecordingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        protected void onCancelled() {
            add_records = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent in = new Intent(RecordingActivity.this,
                    HomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }*/

        recording_stop_alert();


    }


    public void recording_stop_alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
        builder1.setMessage(R.string.stop_alert_msg);
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        // Stop Recording
                        try {

                            pause();

                            try {
                                File file = new File(audiopath);
                                boolean deleted = file.delete();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
                        try {


                            Intent in = new Intent(RecordingActivity.this,
                                    HomeActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                            dialog.cancel();
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
                    }
                });

        builder1.setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Button save, discard;
        public EditText edt_filename, edt_notes;
        public String get_filename, get_notes;

        MyRecording myRe = new MyRecording();

        public CustomDialogClass(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            GlobalData.activities.add(RecordingActivity.this);
            context = RecordingActivity.this;
            setContentView(R.layout.custom_alert_edit);
            edt_filename = (EditText) findViewById(R.id.edt_filename);
            save = (Button) findViewById(R.id.btn_save);
            edt_filename.setText("  " + fileName);

            edt_filename.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {
                        if (edt_filename.getText().toString().length() > 0) {
                            save.setEnabled(true);
                        } else {
                            save.setEnabled(false);
                        }


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                }
            });

            String file_name = removeExtension(fileName);

            edt_filename.setText("  " + file_name);

            edt_filename.setSelectAllOnFocus(true);
          /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edt_filename, InputMethodManager.SHOW_IMPLICIT);
*/
            setCancelable(false);

            edt_filename.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            edt_notes = (EditText) findViewById(R.id.edt_notes);

            discard = (Button) findViewById(R.id.btn_discard);
            save.setOnClickListener(this);
            discard.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:

                    String name = edt_filename.getText().toString().trim();

                    if (name.length() <= 0) {
                        Toast.makeText(getContext(), "Enter file name", Toast.LENGTH_LONG).show();
                    } else {

                        try {


                            get_filename = edt_filename.getText().toString().trim();
                            get_notes = edt_notes.getText().toString();
                            int duration = 0;
                            File file = new File(audiopath);
                            long size = file.length();
                            String s = String.valueOf(size);

                            duration = 0;

                            try {

                                File testfile = new File(audiopath);

                                if (testfile.exists()) {


                                    MediaPlayer mp = MediaPlayer.create(RecordingActivity.this, Uri.parse(audiopath));


                                    duration = mp.getDuration();
                                }
                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }

//                        mp.release();


                            String d = GlobalData.convertSecondsToHMmSs(duration);
                            String date = GlobalData.dateDDMMYYYY(new Date());


                            Log.e("date", date);

                            MyRecording myRecording = new MyRecording();
                            myRecording.setRecId(1);
                            myRecording.setRecName(name);
                            myRecording.setRecDesc(get_notes);
                            myRecording.setRecSize(s);
                            myRecording.setCreatedDate(date);
                            myRecording.setServerId(0);
                            myRecording.setRecDuration(d);
                            myRecording.setUserId(GlobalData.userSelected.getUser_id());
                            myRecording.setUpMasterId("");
                            myRecording.setSourceTypeId("recording");
                            myRecording.setRecLocalPath(audiopath);
                            myRecording.setUploadingConnectionMode("");
                            myRecording.setSourceLink(audiopath);
                            myRecording.setRecUploadDuration(d);
                            add_records = new AddMyRecordings(myRecording);
                            add_records.execute((Void) null);
                            File filePath = new File(sdroot, dir + fileName.trim());
                            copy(new File(audiopath), filePath);
                            dismiss();

                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
//                        } else {
//
//                            //rename
//                            get_filename = edt_filename.getText().toString();
//                            File newPath = new File(root, dir + get_filename + ".m4a");
//                            renameFile(audiopath, newPath.getPath());
//
//                            get_notes = edt_notes.getText().toString();
//                            MyRecording myRecording = new MyRecording();
//                            myRecording.setRecId(2);
//                            myRecording.setRecName(get_filename);
//                            myRecording.setRecDesc(get_notes);
//                            myRecording.setRecSize("3mb");
//                            myRecording.setCreatedDate("23-jan-2016");
//                            myRecording.setServerId(0);
//                            myRecording.setRecDurationfloat("24");
//                            myRecording.setUserId(001);
//                            myRecording.setUpMasterId(001);
//                            myRecording.setSourceTypeId(1);
//                            myRecording.setRecLocalPath(audiopath);
//                            myRecording.setUploadingConnectionMode("");
//                            myRecording.setSourceLink("");
//                            myRecording.setRecUploadDuration("");
//                            add_records = new AddMyRecordings(myRecording);
//                            add_records.execute((Void) null);
//
//                            dismiss();
//                        }
                    }
                    break;
                case R.id.btn_discard:
                    try {
                        File file = new File(audiopath);
                        boolean deleted = file.delete();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dismiss();


                    try {

                        myChronometer.reset();
                        myChronometer.start();

                        AccessStoragePermission();
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    // myChronometer.reset();


                    break;
                default:
                    break;
            }
            dismiss();
        }
    }

    public static String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path upto the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
////                    if(selfPermissionGranted(permissions[0])){
//
//
//                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 1);
//                    //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
//                    //Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();
//
//                    //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));
//
//
//                    setFileStorage();
//
//
//                } else {
//
//                    sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 0);
//                    //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
//                    //Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();
//
//
//                    finish();
//                }
//                break;
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isApplicationSentToBackground(context)) {
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(RecordingActivity.this);
            mBuilder.setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(getResources().getString(R.string.progress_notification))
                    .setSmallIcon(R.drawable.ic_stat_noticon);
            // Creates an explicit intent for an ResultActivity to receive.
//            Intent resultIntent = new Intent(context, RecordingActivity.class);
//            resultIntent.putExtra("notify", true);
//            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Intent resultIntent = getIntent();
            resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resultIntent.setAction(Intent.ACTION_MAIN);

            // This ensures that the back button follows the recommended convention
            // for the back key.
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//
//            // Adds the back stack for the Intent (but not the Intent itself).
//            stackBuilder.addParentStack(RecordingActivity.class);
//
//            // Adds the Intent that starts the Activity to the top of the stack.
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(resultPendingIntent);
            //  new Downloader().execute();
            mBuilder.setAutoCancel(true);
            mNotifyManager.notify(id, mBuilder.build());
//            downloader = new Downloader();
//            downloader.execute((Void) null);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mNotifyManager != null) {

            mNotifyManager.cancelAll();
            mNotifyManager = null;
        }
//        init();


    }

    private class Downloader extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());

            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
//            downloader = null;
            super.onPostExecute(result);
            mBuilder.setContentText("Recording complete");
            // Removes the progress bar
            // mBuilder.addAction();
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());

        }
    }

    //method to check activity is in background
    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }


        return false;
    }


    // Recorder Activity


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.btn_record:
//
//                play();
//                break;

            case R.id.btn_record_pause:
                pause();
                break;
            case R.id.btn_record:
                tryStart();
                break;

        }


    }


    // Set Recording

    // REcording Play

    public void setRecorder() {


        //final RecorderApplication application = RecorderApplication.getApplication( context);
        mAudioRecorder = getRecorder();



        if (mAudioRecorder == null
                || mAudioRecorder.getStatus() == AudioRecorder.Status.STATUS_UNKNOWN) {
            mAudioRecorder = AudioRecorderBuilder.with(this)
                    .fileName(getNextFileName())
                    .config(AudioRecorder.MediaRecorderConfig.DEFAULT)
                    .loggable()
                    .build();
            setRecorder(mAudioRecorder);
        }


//        tryStart();


        invalidateViews();


    }


    @TargetApi(Build.VERSION_CODES.M)
    private void tryStart() {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final int checkAudio = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
                final int checkStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (checkAudio != PackageManager.PERMISSION_GRANTED || checkStorage != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.RECORD_AUDIO)) {
                        showNeedPermissionsMessage();
                    } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        showNeedPermissionsMessage();
                    } else {
                        requestPermissions(new String[]{
                                        Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_PERMISSIONS);
                    }
                } else {
                    start();
                }
            } else {
                start();
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {

        try {

            switch (requestCode) {
                case REQUEST_CODE_PERMISSIONS:
                    boolean userAllowed = true;
                    for (final int result : grantResults) {
                        userAllowed &= result == PackageManager.PERMISSION_GRANTED;
                    }
                    if (userAllowed) {
                        start();
                    } else {
                    /*
                     * Cannot show dialog from here
                     * https://code.google.com/p/android-developer-preview/issues/detail?id=2823
                     */
                        showNeedPermissionsMessage();
                    }
                    break;

                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    if(selfPermissionGranted(permissions[0])){


                        sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 1);
                        //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();

                        //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));


                        setFileStorage();


                    } else {

                        sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 0);
                        //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                        finish();
                    }
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void showNeedPermissionsMessage() {

        try {

            invalidateViews();
            message(getString(R.string.error_no_permissions));

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

//    @Override
//    public void onDestroy() {

//        try {
//
//            if (mAudioRecorder.isRecording()) {
//                mAudioRecorder.cancel();
//                setResult(Activity.RESULT_CANCELED);
//            }
//            super.onDestroy();
//
//        }catch (Exception e)
//        {
//            GlobalData.printError(e);
//        }
//    }

    private void message(String message) {

        try {

            final View root = record_play;
            if (root != null) {
                final Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void start() {

        try {

            try {
//                mRecordingThread.startRecording(audiopath);
            }catch (Exception e){
                GlobalData.printError(e);
            }


            mAudioRecorder.start(new AudioRecorder.OnStartListener() {
                @Override
                public void onStarted() {

                    try {
                            invalidateViews();

                        if (  !myChronometer.isRunning()) {
                               myChronometer.start();


                        }



                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                }

                @Override
                public void onException(Exception e) {
                    try {
                        setResult(Activity.RESULT_CANCELED);
                        invalidateViews();
                        message(getString(R.string.error_audio_recorder, e));
                    } catch (Exception e2) {
                        GlobalData.printError(e2);
                    }

                }
            });

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void pause() {


        try {


            mAudioRecorder.pause(new AudioRecorder.OnPauseListener() {
                @Override
                public void onPaused(String activeRecordFileName) {
                    try {
                        mActiveRecordFileName = activeRecordFileName;
                        invalidateViews();

                        if (myChronometer.isRunning()) {
                            myChronometer.stop();


                        }

                        try {
//                            mRecordingThread.stopRecording();
                        }catch (Exception e){
                            GlobalData.printError(e);
                        }
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


//                setResult(Activity.RESULT_OK,
//                        //new Intent().setData(Uri.parse(mActiveRecordFileName)));
//                        new Intent().setData(saveCurrentRecordToMediaDB(mActiveRecordFileName)));

                }

                @Override
                public void onException(Exception e) {
                    try {
                        setResult(Activity.RESULT_CANCELED);
                        invalidateViews();
                        message(getString(R.string.error_audio_recorder, e));
                    } catch (Exception e2) {
                        GlobalData.printError(e2);

                    }

                    GlobalData.printError(e);
                }
            });


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void play() {


        try {


            File file = new File(mActiveRecordFileName);
            if (file.exists()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        try {
            mNotifyManager.cancel(id);
        }catch (Exception e){
            GlobalData.printError(e);
        }
    }

    private String getNextFileName() {

        try {

            return audiopath;


//            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
//                    .getAbsolutePath()
//                    + File.separator
//                    + "Record_"
//                    + System.currentTimeMillis()
//                    + ".mp4";


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return "Record.mp4";
    }

    private void invalidateViews() {

        try {

            switch (mAudioRecorder.getStatus()) {
                case STATUS_UNKNOWN:
//                mCassetteImage.clearAnimation();
//                record_start.setEnabled(false);
                    record_pause.setVisibility(View.GONE);
                    record_start.setVisibility(View.VISIBLE);

                    record_pause.setVisibility(View.GONE);
                    record_start.setVisibility(View.VISIBLE);
                    break;
                case STATUS_READY_TO_RECORD:
//                mCassetteImage.clearAnimation();
//                record_start.setEnabled(true);
                    record_pause.setVisibility(View.VISIBLE);
                    record_start.setVisibility(View.GONE);
                    break;
                case STATUS_RECORDING:
//                mCassetteImage.startAnimation(
//                        AnimationUtils.loadAnimation(getActivity(), R.anim.animation_pulse));
//                record_start.setEnabled(false);
//                record_pause.setEnabled(true);
//                record_stop.setEnabled(false);

                    record_pause.setVisibility(View.VISIBLE);
                    record_start.setVisibility(View.GONE);
                    break;
                case STATUS_RECORD_PAUSED:
//                mCassetteImage.clearAnimation();

//                record_start.setEnabled(true);
//                record_pause.setEnabled(false);
//                record_play.setEnabled(true);

                    record_pause.setVisibility(View.GONE);
                    record_start.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }




    // Set Recorder


    public void setRecorder(@NonNull AudioRecorder recorder) {
        mAudioRecorder = recorder;
    }

    public AudioRecorder getRecorder() {
        return mAudioRecorder;
    }


}