package com.voxtab.ariatech.voxtab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.voxtab.ariatech.voxtab.adapter.MyRecording_list_adapter;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Local User on 01-Feb-16.
 */
public class MyRecordingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<MyRecording> recordings;
    public static LinearLayout lin_actions, lin_info, lin_bottom;
    Uri _uri;
    private AddMyRecordings add_audio = null;
    public static TextView txt_number_selected_files;
    TextView txt_file_time, txt_file_status;
    String file_name = "";
    TextView txt_assignment_num, txt_status, txt_date_time, txt_file_descr, txt_filename;
    private MyRecording_list_adapter myRecording_list_adapter;
    LinkedList list;
    public static ListView lst_recordings;
    private Toolbar toolbar;
    private Context context;
    Button btn_proceed_order, btn_add_audio;
    public static ImageView img_edit, img_delete, img_info, img_share, img_banner, img_back;
    private EditMyRecordings add_records = null;
    private DeleteMyRecordings delete_records = null;
    private ArrayAdapter<String> listAdapter;
    TextView txt_no_records;
    private File root;
    private String dir;
    private String audiopath, fileName;
    MyRecording myRecording;
    LinkedList<MyRecording> selectedFiles = new LinkedList<>();
    SharedPreferencesUtility sharedPreferencesUtility;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private boolean isLoggedIn;
    private TextView txt_header_login;

    boolean btn_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myrecordings);


        GlobalData.activities.add(MyRecordingsActivity.this);
        context = this;

        recordings = new ArrayList<>();
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

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());
//        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//
//
        isLoggedIn = GlobalData.isLoggedIn(context);
//        TextView txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//        TextView txt_email = (TextView) headerView.findViewById(R.id.txt_email);
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
//        img_user_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLoggedIn) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.putExtra("Name", context.getClass().getName());
//                    startActivity(intent);
//                }
//            }
//        });

        init();


        myRecording = new MyRecording();
        fileName = myRecording.getRecName();
       /* File mkDir = new File(root, dir);
        mkDir.mkdirs();*/
        File filePath = new File(root, dir + fileName);
        audiopath = filePath.getPath();
/*
        lst_recordings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    if (!GlobalData.longpressFlag) {
                        lin_actions.setVisibility(View.VISIBLE);
                        img_banner.setVisibility(View.GONE);
                        GlobalData.longpressFlag = true;

                        if (recordings.size() > position) {
                            recordings.get(position).setSelectFlag(true);

                            int count = getSelectedRecordingCount();
                            MyRecordingsActivity.txt_number_selected_files.setText(count + "");

                            myRecording_list_adapter = new MyRecording_list_adapter(context, recordings);
                            lst_recordings.setAdapter(myRecording_list_adapter);
                        }

                    }
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
                return true;
            }
        });
*/


        img_edit.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {
                                            try {

                                                if (getSelectedcount() == 1) {
                                                    CustomDialogClass cdd = new CustomDialogClass(MyRecordingsActivity.this, getSelectedRecording().get(0));
                                                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    cdd.show();
                                                } else if (getSelectedcount() > 0) {

                                                    select_file_alert(getSelectedcount(), true);
                                                } else {
                                                    select_file_alert(getSelectedcount(), true);

                                                }
                                            } catch (Exception e) {
                                                GlobalData.printError(e);
                                            }


                                        }
                                    }

        );
        img_delete.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View v) {
                                              try {

                                                  if (getSelectedRecording().size() > 0) {
                                                      delete_alert();

                                                  } else {
                                                      select_file_alert(getSelectedcount(), false);

                                                  }
                                              } catch (Exception e) {
                                                  GlobalData.printError(e);
                                              }


//                File file = new File(audiopath);
//                boolean deleted = file.delete();
//                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();
                                          }
                                      }

        );


        img_back.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {

                                            removeLongPress();
                                            lin_info.setVisibility(View.GONE);
                                            lst_recordings.setVisibility(View.VISIBLE);
                                            lin_bottom.setVisibility(View.VISIBLE);


                                        }
                                    }

        );


        img_info.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {

                                            try {

                                                if (getSelectedcount() == 1) {
                                                    lin_info.setVisibility(View.VISIBLE);
                                                    lst_recordings.setVisibility(View.GONE);
                                                    lin_bottom.setVisibility(View.GONE);

                                                    btn_flag = true;
                                                    checkbtn_flag();

                                                    for (int i = 0; i <= getSelectedRecording().size(); i++) {
                                                        txt_filename.setText(getSelectedRecording().get(i).getRecName());
                                                        txt_file_descr.setText(getSelectedRecording().get(i).getRecDesc());
                                                        txt_date_time.setText(getSelectedRecording().get(i).getCreatedDate());


                                                        String status = "";

                                                        try {
                                                            if (getSelectedRecording().get(i).getUpMasterId().length() > 0) {

                                                                DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                                                                db.open();
                                                                status = db.getFile_status_type(getSelectedRecording().get(i).getUpMasterId()).getFile_status_msg();
                                                            }


                                                        } catch (Exception e) {
                                                            GlobalData.printError(e);
                                                        }


                                                        txt_status.setText("" + status);


                                                        String assignNo = "";

                                                        if (getSelectedRecording().get(i).getAssignment_no().length() > 0) {
                                                            assignNo = getSelectedRecording().get(i).getAssignment_no();

                                                        }

                                                        txt_assignment_num.setText("" + assignNo);
                                                    }


                                                } else if (getSelectedcount() > 0) {

                                                    select_file_alert(getSelectedcount(), true);
                                                } else {
                                                    select_file_alert(getSelectedcount(), true);

                                                }


                                            } catch (Exception e) {
                                                GlobalData.printError(e);
                                            }


                                        }
                                    }

        );


       /* img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (getSelectedRecording().size() > 0) {
                        List<String> paths = new ArrayList<String>();
                        for (int i = 0; i < getSelectedRecording().size(); i++) {
                            paths.add(getSelectedRecording().get(i).getRecLocalPath());
                        }

                        try {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("audio*//**//*");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + paths));
                            ArrayList<Uri> files = new ArrayList<Uri>();

                            for (String path : paths) {
                                File file = new File(path);
                                Uri uri = Uri.fromFile(file);
                                files.add(uri);
                            }

                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                            startActivity(intent.createChooser(intent, "Share Sound File"));

                            try {


                                for (int i = 0; i < MyRecordingsActivity.recordings.size(); i++) {
                                    MyRecordingsActivity.recordings.get(i).setSelectFlag(false);

                                }

                                removeLongPress();

                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }

                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }


                    } else if (getSelectedcount() > 0) {

                        select_file_alert(getSelectedcount(), true);
                    } else {
                        select_file_alert(getSelectedcount(), true);

                    }
                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        });
*/


        img_share.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View v) {
                                             try {

                                                 if (getSelectedRecording().size() > 0) {
                                                     List<String> paths = new ArrayList<String>();
                                                     for (int i = 0; i < getSelectedRecording().size(); i++) {
                                                         paths.add(getSelectedRecording().get(i).getRecLocalPath());
                                                     }

                                                     try {
                                                         Intent intent = new Intent();
                                                         intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                                         intent.setType("audio/*");
                                                         intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + paths));

                                                         ArrayList<Uri> files = new ArrayList<Uri>();

                                                         for (String path : paths /* List of the files you want to send */) {
                                                             File file = new File(path);
                                                             Uri uri = Uri.fromFile(file);
                                                             files.add(uri);
                                                         }

                                                         intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                                                         startActivity(intent.createChooser(intent, "Share Sound File"));

                                                         try {

                                                             removeLongPress();

                                                         } catch (Exception e) {
                                                             GlobalData.printError(e);
                                                         }

                                                     } catch (Exception e) {
                                                         GlobalData.printError(e);
                                                     }


                                                 } else if (getSelectedcount() > 0) {

                                                     select_file_alert(getSelectedcount(), true);
                                                 } else {
                                                     select_file_alert(getSelectedcount(), true);

                                                 }
                                             } catch (Exception e) {
                                                 GlobalData.printError(e);
                                             }

                                         }
                                     }

        );
        btn_proceed_order.setOnClickListener(new View.OnClickListener()

                                             {
                                                 @Override
                                                 public void onClick(View v) {
                                                     try {

                                                         if (getSelectedRecording().size() > 0) {
                                                             GlobalData.sList = new LinkedList<MyRecording>();
                                                             selectedFiles = getSelectedRecording();


                                                             GlobalData.sList.addAll(selectedFiles);
                                                             int totalDuration = 0;
                                                             try {

                                                                 for (int i = 0; i < list.size(); i++) {
                                                                     if (GlobalData.parseSeconds(GlobalData.sList.get(i).getRecDuration()) > 0) {

                                                                         totalDuration = totalDuration + GlobalData.parseSeconds(GlobalData.sList.get(i).getRecDuration());

                                                                     } else {
                                                                         totalDuration = 0;
                                                                         break;
                                                                     }
                                                                 }
//            totalMinutes = 73;

                                                             } catch (Exception e) {
                                                                 GlobalData.printError(e);
                                                             }

                                                             if (totalDuration > 0) {
                        /*Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                        //intent.putExtra("selected_files", selectedFiles);
                        startActivity(intent);*/


                                                                 if (isLoggedIn) {
                                                                     Intent intent = new Intent(MyRecordingsActivity.this, OrderActivity.class);
                                                                     startActivity(intent);
                                                                 } else {
                                                                     Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                                                                     intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderActivity");
                                                                     startActivity(intent);

                                                                 }


                                                             } else {
                                                                 file_alert();
                                                             }


                                                         } else {
                                                             select_file_alert(getSelectedcount(), false);

                                                         }
                                                     } catch (Exception e) {
                                                         GlobalData.printError(e);
                                                     }

                                                 }
                                             }

        );

      /*  img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRecordingsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });*/


        btn_add_audio.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View v) {


                                                 AccessStoragePermission();


                                             }
                                         }

        );


    }

    void addAudioFile() {

        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, 1);
    }

    private void AccessStoragePermission() {
        sharedPreferencesUtility = new SharedPreferencesUtility(MyRecordingsActivity.this);

        int firstTime = sharedPreferencesUtility.getInteger(GlobalData.permissionRecStorageFlag, 0);

        if (firstTime == 0 && Build.VERSION.SDK_INT >= 23) {


            if (GlobalData.selfPermissionGranted(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //  Toast.makeText(context, "This permission allows us to access phone audio recorder. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

                addAudioFile();

            } else {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            }

        } else {

            addAudioFile();
        }


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
                        MediaPlayer mp = MediaPlayer.create(MyRecordingsActivity.this, Uri.parse(filePath));
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
                            MediaPlayer mp = MediaPlayer.create(MyRecordingsActivity.this, Uri.parse(filePath));
                            int duration = mp.getDuration();
                            d = GlobalData.convertSecondsToHMmSs(duration);

                            //date

//                            Date lastModDate = new Date(file.lastModified());
//                            date=lastModDate.toString();
                            Log.i("File last modified @ : ", date);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }


                    date = GlobalData.dateDDMMYYYY(new Date());
                    ;

                    MyRecording myRecording = new MyRecording();
                    myRecording.setRecName(file_name);
                    myRecording.setRecDesc("");
                    myRecording.setRecSize(s);
                    myRecording.setCreatedDate(date);
                    myRecording.setServerId(0);
                    myRecording.setRecDuration(d);
                    myRecording.setUserId(GlobalData.userSelected.getUser_id());
                    myRecording.setUpMasterId("");
                    myRecording.setSourceTypeId("extstorage");
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

    private void renameFile(String oldname, String newName) {
        File source = new File(oldname);
        File destination = new File(newName);
        boolean success = source.renameTo(destination);
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {

        try {

            switch (requestCode) {


                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    if(selfPermissionGranted(permissions[0])){


                        sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 1);
                        //Snackbar.make(view, "Permission Granted, Now you can read phone state.", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(context, "Permission Granted, Now you can read phone state.", Toast.LENGTH_LONG).show();

                        //  startActivity(new Intent(HomeActivity.this,RecordingActivity.class));


                        addAudioFile();


                    } else {

                        sharedPreferencesUtility.setInteger(GlobalData.permissionRecStorageFlag, 0);
                        //Snackbar.make(view, "Permission Denied, You cannot read phone state.", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(context, "Permission Denied, You cannot use this application.", Toast.LENGTH_LONG).show();


                    }
                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    public void edit_alert() {


        if (getSelectedRecording().size() > 0) {

            MyRecording myRecording = getSelectedRecording().get(0);


            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            final EditText edittext_filename = new EditText(context);
            final EditText edittext_notes = new EditText(context);
            builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
            builder1.setView(edittext_filename);
            builder1.setView(edittext_notes);
            edittext_filename.setText(myRecording.getRecName());
            edittext_filename.setTextColor(getResources().getColor(R.color.colorPrimary));

            edittext_notes.setText("");
            if (myRecording.getRecDesc().length() > 0) {
                edittext_notes.setText(myRecording.getRecDesc());
            } else {
                edittext_notes.setHint(R.string.descr_hint);
            }
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    R.string.save,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            if (getSelectedRecording().size() > 0) {

                                MyRecording myRecording1 = getSelectedRecording().get(0);
                                String get_notes = edittext_notes.getText().toString();
                                String get_filename = edittext_filename.getText().toString();
                                File newPath = new File(root, dir + get_filename);
                                renameFile(audiopath, newPath.getPath());

                                myRecording1.setRecName(get_filename);
                                myRecording1.setRecDesc(get_notes);
                                add_records = new EditMyRecordings(myRecording1);
                                add_records.execute((Void) null);


                            }


                        }

                    }

            );

            builder1.setNegativeButton(
                    R.string.discard,
                    new DialogInterface.OnClickListener()

                    {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }

            );
            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }


    public int getSelectedRecordingCount() {
        int count = 0;
        try {

            for (int i = 0; i < MyRecordingsActivity.recordings.size(); i++) {

                if (MyRecordingsActivity.recordings.get(i).isSelectFlag()) {
                    count = count + 1;
                }
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return count;

    }


    public void select_file_alert(int count, boolean editFlag) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
        String message = getString(R.string.select_file);

        if (count > 1 && editFlag) {
            message = getString(R.string.single_file);
        }
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                }

        );
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void file_alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
        String message = getString(R.string.file_size);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                }

        );
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void single_file_alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
        builder1.setMessage(R.string.single_file);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                }

        );
        AlertDialog alert11 = builder1.create();
        alert11.show();

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notifications) {
            if (isLoggedIn) {
                Intent intent = new Intent(MyRecordingsActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(MyRecordingsActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(MyRecordingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(MyRecordingsActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(MyRecordingsActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(MyRecordingsActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(MyRecordingsActivity.this, AboutusActivity.class);
            startActivity(intent);

        }
//        else if (id == R.id.nav_logout) {
//            try {
//
//
//                if (!GlobalData.isNetworkAvailable(context)) {
//                    Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();
//
//                } else {
//                    new LogoutWebServiceCall(context, getIntent()).execute();
//                    Intent intent = new Intent(MyRecordingsActivity.this, HomeActivity.class);
//                    startActivity(intent);
//
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
                Intent intent = new Intent(MyRecordingsActivity.this, FeedbackActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(MyRecordingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init() {

        try {


            lst_recordings = (ListView) findViewById(R.id.lst_myrecordings);
            img_info = (ImageView) findViewById(R.id.img_info);
            img_edit = (ImageView) findViewById(R.id.img_edit);
            img_delete = (ImageView) findViewById(R.id.img_delete);
            img_share = (ImageView) findViewById(R.id.img_share);
            img_back = (ImageView) findViewById(R.id.img_back);
            txt_number_selected_files = (TextView) findViewById(R.id.txt_num_selected_files);

            img_banner = (ImageView) findViewById(R.id.img_banner);
            img_banner.setVisibility(View.VISIBLE);
            lin_actions = (LinearLayout) findViewById(R.id.lin_actions);
            lin_actions.setVisibility(View.GONE);

            lin_info = (LinearLayout) findViewById(R.id.lin_file_info);
            lin_info.setVisibility(View.GONE);

            lin_bottom = (LinearLayout) findViewById(R.id.lin_bottom);
            lin_bottom.setVisibility(View.VISIBLE);


            btn_proceed_order = (Button) findViewById(R.id.btn_proceed_order);
            btn_add_audio = (Button) findViewById(R.id.btn_add_audio);

            //file info
            txt_filename = (TextView) findViewById(R.id.txt_filename);
            txt_file_descr = (TextView) findViewById(R.id.txt_file_descr);
            txt_date_time = (TextView) findViewById(R.id.txt_date_time);
            txt_status = (TextView) findViewById(R.id.txt_status);
            txt_assignment_num = (TextView) findViewById(R.id.txt_assignment_num);


            txt_file_status = (TextView) findViewById(R.id.txt_file_status);
            txt_file_time = (TextView) findViewById(R.id.txt_status);


            txt_no_records = (TextView) findViewById(R.id.txt_no_records);
            txt_no_records.setVisibility(View.GONE);
            root = Environment.getExternalStorageDirectory();
            dir = "/Voxtab/Audio/";
            setAdapaterData();
            removeLongPress();

        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }

    public void setAdapaterData() {
        try {
            GetMyRecordings();

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    protected void GetMyRecordings() {

        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        LinkedList list_records = new LinkedList();
        try {
            databaseHandler.open();
            list_records = databaseHandler.getRecording();

        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            databaseHandler.close();
        }
        recordings = new ArrayList<>(list_records);

        if (list_records != null) {
            if (list_records.size() > 0) {
                list = list_records;
                myRecording_list_adapter = new MyRecording_list_adapter(context, recordings);
                lst_recordings.setAdapter(myRecording_list_adapter);
                lst_recordings.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);
            } else {
                lst_recordings.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
            }

        }


    }


    public class EditMyRecordings extends AsyncTask<Void, Void, Boolean> {

        MyRecording myRecording1;

        EditMyRecordings(MyRecording myRecording) {
            myRecording1 = myRecording;
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            try {
                databaseHandler.open();
                databaseHandler.editRecording(myRecording1);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean f = true;
            return f;
        }

        @Override
        protected void onPostExecute(final Boolean bean) {
            add_records = null;


            setAdapaterData();
        }


        protected void onCancelled() {
            add_records = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }


    public void delete_alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
        builder1.setMessage(R.string.delete_alert_msg);
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {


                            delete_records = new DeleteMyRecordings(getSelectedRecording());
                            delete_records.execute((Void) null);
                            setAdapaterData();
                            removeLongPress();
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
                        removeLongPress();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public class DeleteMyRecordings extends AsyncTask<Void, Void, Boolean> {

        LinkedList<MyRecording> myRecordings;

        DeleteMyRecordings(LinkedList<MyRecording> myRecordingList) {
            myRecordings = myRecordingList;

            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            try {
                databaseHandler.open();
                databaseHandler.deleteRecording(myRecordings);
                setAdapaterData();
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean f = true;
            return f;
        }

        @Override
        protected void onPostExecute(final Boolean bean) {
            add_records = null;

        }

        protected void onCancelled() {
            add_records = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Button save, discard;
        public EditText edt_filename, edt_notes;

        MyRecording myRe = new MyRecording();

        public CustomDialogClass(Activity a, MyRecording rec) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            myRe = rec;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_alert_edit);
            edt_filename = (EditText) findViewById(R.id.edt_filename);
            edt_filename.setText(myRe.getRecName());
            edt_filename.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            edt_notes = (EditText) findViewById(R.id.edt_notes);
            edt_notes.setText(myRe.getRecDesc());
            save = (Button) findViewById(R.id.btn_save);
            discard = (Button) findViewById(R.id.btn_discard);
            save.setOnClickListener(this);
            discard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:

                    if (edt_filename.getText().length() <= 0 || edt_filename.getText().equals(" ")) {
                        Toast.makeText(getContext(), R.string.enter_filename_msg, Toast.LENGTH_LONG).show();
                    } else {
                        String get_filename = edt_filename.getText().toString();
                        File newPath = new File(root, dir + get_filename);
                        renameFile(audiopath, newPath.getPath());
                        String get_notes = edt_notes.getText().toString();

                        myRe.setRecName(get_filename);
                        myRe.setRecDesc(get_notes);
                        add_records = new EditMyRecordings(myRe);
                        add_records.execute((Void) null);
                        removeLongPress();
                        // c.finish();
                        break;
                    }
                case R.id.btn_discard:
                    dismiss();
                    removeLongPress();
                    break;
                default:
                    break;

            }
            dismiss();
        }
    }


    public int getSelectedcount() {

        int selected = 0;

        try {

            for (int i = 0; i < recordings.size(); i++) {

                if (recordings.get(i).isSelectFlag()) {
                    selected = selected + 1;
                }
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return selected;

    }

    public LinkedList<MyRecording> getSelectedRecording() {
        LinkedList<MyRecording> recording = new LinkedList<>();
        try {

            for (int i = 0; i < recordings.size(); i++) {

                if (recordings.get(i).isSelectFlag()) {
                    recording.add(recordings.get(i));
                }
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return recording;

    }


    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent in = new Intent(MyRecordingsActivity.this,
                    HomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }

    }

    public void removeLongPress() {

        try {


            try {
                for (int i = 0; i < recordings.size(); i++) {
                    recordings.get(i).setSelectFlag(false);
                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }

            GlobalData.longpressFlag = false;

            try {
                myRecording_list_adapter = new MyRecording_list_adapter(context, recordings);
                lst_recordings.setAdapter(myRecording_list_adapter);
            } catch (Exception e) {
                GlobalData.printError(e);
            }


            lin_actions.setVisibility(View.GONE);
            img_banner.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            GlobalData.printError(e);
        }

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
            add_audio = null;
            if (bean != null) {
                Intent intent = new Intent(MyRecordingsActivity.this, MyRecordingsActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();

        try {

            if (myRecording_list_adapter.mediaPlayer != null) {

                myRecording_list_adapter.mediaPlayer.stop();
                myRecording_list_adapter.mediaPlayer.release();
                lst_recordings.setAdapter(lst_recordings.getAdapter());

            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public void checkbtn_flag() {

        if (btn_flag) {

           /* img_edit.setEnabled(false);
            img_delete.setEnabled(false);
            img_share.setEnabled(false);*/

            img_share.setVisibility(View.GONE);
            img_delete.setVisibility(View.GONE);
            img_edit.setVisibility(View.GONE);

        } else {
            img_share.setVisibility(View.VISIBLE);
            img_delete.setVisibility(View.VISIBLE);
            img_edit.setVisibility(View.VISIBLE);

        }


    }

}