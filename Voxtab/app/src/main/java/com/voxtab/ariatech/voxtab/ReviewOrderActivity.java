package com.voxtab.ariatech.voxtab;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.adapter.SelectedFilesAdapter;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.OrderRec;
import com.voxtab.ariatech.voxtab.bean.Recording;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.bean.Users_JSON;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.fileuploading.BackgroundService;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Local User on 16-Feb-16.
 */
public class ReviewOrderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    Button btn_complete_order, btn_cancel, btn_edit;
    TextView lbl_details, lbl_value_added_services, txt_total_duration, txt_total_fee, txt_delivery_date_time, txt_service_type, txt_delivery_option, txt_timestamp_duration, txt_valueadded_services, txt_fs1, txt_fs2, txt_fs3, txt_fs4, txt_fs5, txt_note;

    String to_newdate = "", total_duration = "", total_fee = "", delivery_date_time = "", service_type = "", transcription_type = "", timestamp_duration = "", valueadded_services = "", subject_file = "", instr_order = "";

    EditText edt_subject_file, edt_instr_order;
    private SelectedFilesAdapter selectedFilesAdapter;
    LinkedList<MyRecording> selectedFiles;
    ListView lst_selected_files;
    //LinkedList list;
    private static AddOrderDetails order_rec = null;
    private GetOrderREC records = null;
    public static OrderDetails orderRecs;
    int orderId;
    File_Meta_JSON file_meta_json = new File_Meta_JSON();
    OrderDetails orderDetailsInfo = new OrderDetails();
    private ProgressBar progress_bar_new;

    private boolean isLoggedIn;
    private TextView txt_header_login;

    SharedPreferencesUtility sharedPreferencesUtility;

    String toDate = "";

    LinkedList<Free_services> free_services = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);

        orderId = getIntent().getIntExtra("orderId", 0);

        try {
            GlobalData.activities.add(ReviewOrderActivity.this);
            context = this;
            sharedPreferencesUtility = new SharedPreferencesUtility(context);
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
//            navigationView.addHeaderView(headerView);

            GlobalData.setLoginAndLogout(context, navigationView, getIntent());


//            txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
            isLoggedIn = GlobalData.isLoggedIn(context);
//            TextView txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//            TextView txt_email = (TextView) headerView.findViewById(R.id.txt_email);
//            CircularSmartImageView img_user_photo = (CircularSmartImageView) headerView.findViewById(R.id.img_user_photo);
//
//            if (isLoggedIn) {
//                navigationView.inflateMenu(R.menu.activity_main_drawer);
//                Users memberId = GlobalData.getMemberId(context);
//                String photo = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + memberId.getPhoto());
//
//                txt_header_login.setText(memberId.getMembership_id());
//                txt_email.setText(memberId.getEmail());
//                img_user_photo.setImageUrl(photo, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);
//
//
//            } else {
//                navigationView.inflateMenu(R.menu.activity_menu_drawer_login);
//            }
//            img_user_photo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!isLoggedIn) {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        intent.putExtra("Name", context.getClass().getName());
//                        startActivity(intent);
//                    }
//                }
//            });
//            txt_header_login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!isLoggedIn) {
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            });
            init();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private void init() {


        txt_note = (TextView) findViewById(R.id.txt_note);

        //selected files
        lst_selected_files = (ListView) findViewById(R.id.lst_selected_files);

//        ArrayList<OrderRec> items1 = (ArrayList<OrderRec>)
//                getIntent().getSerializableExtra("selected_files");

        selectedFiles = GlobalData.sList;
        selectedFilesAdapter = new SelectedFilesAdapter(context, selectedFiles);
        lst_selected_files.setAdapter(selectedFilesAdapter);

        lst_selected_files.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);

        //file_info
        txt_total_duration = (TextView) findViewById(R.id.txt_total_duration);
        txt_total_fee = (TextView) findViewById(R.id.txt_total_fee);
        txt_delivery_date_time = (TextView) findViewById(R.id.txt_delivery_date_time);


        //Free services
        txt_fs1 = (TextView) findViewById(R.id.txt_fs1);
        txt_fs2 = (TextView) findViewById(R.id.txt_fs2);
        txt_fs3 = (TextView) findViewById(R.id.txt_fs3);
        txt_fs4 = (TextView) findViewById(R.id.txt_fs4);
        txt_fs5 = (TextView) findViewById(R.id.txt_fs5);

        txt_fs1.setText(GlobalData.free_services_accent);
        txt_fs2.setText(GlobalData.free_services_terminology);
        txt_fs3.setText(GlobalData.free_services_identification);
        txt_fs4.setText(GlobalData.free_services_timestamp);
        txt_fs5.setText(GlobalData.free_services_type);


        DatabaseHandlerNew db1 = new DatabaseHandlerNew(context);
        Free_services free_services1 = new Free_services();
        Free_services free_services2 = new Free_services();
        Free_services free_services3 = new Free_services();
        Free_services free_services4 = new Free_services();
        Free_services free_services5 = new Free_services();

        try {
            db1.open();
            try {
                free_services1 = db1.getFree_servicesText(GlobalData.free_services_accent);
            } catch (Exception e) {
                GlobalData.printError(e);
            }


            try {
                free_services2 = db1.getFree_servicesText(GlobalData.free_services_terminology);
            } catch (Exception e) {
                GlobalData.printError(e);
            }
            try {
                free_services3 = db1.getFree_servicesText(GlobalData.free_services_identification);
            } catch (Exception e) {
                GlobalData.printError(e);
            }
            try {
                free_services4 = db1.getFree_servicesText(GlobalData.free_services_timestamp);
            } catch (Exception e) {
                GlobalData.printError(e);
            }

            try {
                free_services5 = db1.getFree_servicesText(GlobalData.free_services_type);
            } catch (Exception e) {
                GlobalData.printError(e);
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            db1.close();
        }


        if (free_services1.getFree_service_id().length() > 0) {
            free_services.add(free_services1);
        }

        if (free_services2.getFree_service_id().length() > 0) {
            free_services.add(free_services2);
        }


        if (free_services3.getFree_service_id().length() > 0) {
            free_services.add(free_services3);
        }


        if (free_services4.getFree_service_id().length() > 0) {
            free_services.add(free_services4);
        }

        if (free_services5.getFree_service_id().length() > 0) {
            free_services.add(free_services5);
        }


        //service type
        txt_service_type = (TextView) findViewById(R.id.txt_services_type);
        txt_delivery_option = (TextView) findViewById(R.id.txt_delivery_option);
        lbl_value_added_services = (TextView) findViewById(R.id.lbl_value_added_services);
        txt_valueadded_services = (TextView) findViewById(R.id.txt_value_added_services);
        txt_timestamp_duration = (TextView) findViewById(R.id.txt_time_stamps);


        txt_service_type.setText(GlobalData.service_type_name);
        txt_delivery_option.setText(GlobalData.transcription_type_name);
        if (GlobalData.valueadded_name.equals("")) {
            txt_valueadded_services.setVisibility(View.GONE);
            lbl_value_added_services.setVisibility(View.GONE);
        } else {
            txt_valueadded_services.setText(GlobalData.valueadded_name);
        }
        txt_timestamp_duration.setText(GlobalData.timestamp_duration_name);

        //Edittext
        edt_subject_file = (EditText) findViewById(R.id.edt_subject_file);
        edt_instr_order = (EditText) findViewById(R.id.edt_instr_order);

        lbl_details = (TextView) findViewById(R.id.lbl_details);
        SpannableString details = new SpannableString("Details");
        details.setSpan(new UnderlineSpan(), 0, details.length(), 0);
        lbl_details.setText(details);

        //buttons
        btn_complete_order = (Button) findViewById(R.id.btn_complete_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_complete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete_order(v);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              cancel();
                                          }
                                      }
        );

        btn_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                edit_order();
            }
        });


        try {

            txt_total_duration.setText("");
            txt_total_fee.setText("");
            txt_delivery_date_time.setText("");
            toDate = "";


            if (GlobalData.selectedFiles.size() > 0) {


                txt_total_duration.setText(GlobalData.selectedFiles.get(0).getTotal_duration());


                if(GlobalData.selectedFiles.get(0).getTotal_fees().length() > 0){


                    txt_total_fee.setText(context.getResources().getString(R.string.currency) + " " + GlobalData.selectedFiles.get(0).getTotal_fees());
                }
                txt_delivery_date_time.setText(GlobalData.selectedFiles.get(0).getDisplayDelDateTime());

                if (GlobalData.selectedFiles.get(0).getTo_date().length() > 0) {
                    to_newdate = getResources().getString(R.string.review_hint) + " " + GlobalData.selectedFiles.get(0).getTo_date();

                    txt_note.setText(to_newdate);
                }

//                txt_service_type.setText(items1.get(i).getServiceTypeId());
//                txt_delivery_option.setText(items1.get(i).getTranscriptionTypeId());
//                txt_valueadded_services.setText(items1.get(i).getValueAddedservId());
//                txt_timestamp_duration.setText(items1.get(i).getTimestampId());

            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    public class GetOrderREC extends AsyncTask<Void, Void, OrderDetails> {


        OrderDetails list_records = new OrderDetails();

        protected OrderDetails doInBackground(Void... params) {

            return list_records;
        }

        @Override
        protected void onPostExecute(final OrderDetails list_records1) {

            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);

            try {
                databaseHandler.open();
                list_records = databaseHandler.getOrderDetails(databaseHandler.getMaxOrderId());

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            records = null;
            orderRecs = list_records;
            if (list_records != null) {


                try {

                    //orderRecs=list_records;
                 /*   txt_total_duration.setText("");
                    txt_total_fee.setText("");
                    txt_delivery_date_time.setText("");

                    txt_service_type.setText("");
                    txt_delivery_option.setText("");
                    txt_valueadded_services.setText("");
                    txt_timestamp_duration.setText("");

                    txt_fs1.setText("");
                    txt_fs2.setText("");
                    txt_fs3.setText("");
                    txt_fs4.setText("");
                    txt_fs5.setText("");*/

                    txt_total_duration.setText(orderRecs.getTotal_duration());
                    txt_total_fee.setText(orderRecs.getTotal_fees());
                    txt_delivery_date_time.setText(GlobalData.convertDelDate(orderRecs.getDelivery_date()));


                    String SID = orderRecs.getService_type_id();
                    txt_service_type.setText(String.valueOf(SID));
                    String DID = orderRecs.getDelivery_opt_id();
                    txt_delivery_option.setText(String.valueOf(DID));

                    String VID = orderRecs.getVas_id();
                    txt_valueadded_services.setText(String.valueOf(VID));

                    String TID = orderRecs.getTime_slab_id();
                    txt_timestamp_duration.setText(String.valueOf(TID));


                    if (orderRecs.getTo_date().length() > 0) {
                        to_newdate = getResources().getString(R.string.review_hint) + " " + GlobalData.showTodate(orderRecs.getTo_date());

                        txt_note.setText(to_newdate);
                    }


                } catch (Exception e) {
                    GlobalData.printError(e);
                }


            }

        }

        @Override
        protected void onCancelled() {
            records = null;
        }

    }


    private void edit_order() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.edit_alert))
                .setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(ReviewOrderActivity.this, OrderActivity.class);
                        intent.putExtra("Edit", true);
                        startActivity(intent);
                    }
                }).create().show();

    }

    private void complete_order(View v) {

        // Insert in OrderRec table
        check_connection(v);

    }

    private void cancel() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.cancel_alert))
                .setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                        try {
                            databaseHandler.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        OrderDetails rec = databaseHandler.getOrderDetailsList(orderId);
                        databaseHandler.close();
                        rec.setFlag(3);
                        SimpleDateFormat df = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
                        Date createDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(createDate);
                        // String fromDate = df.format(Date.parse(c.getTime().toString()));
                        String fromDate = c.getTime().toString();

                        rec.setDelivery_date(fromDate);

                        try {
                            databaseHandler.open();
                            databaseHandler.updateorderdetails(rec);
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        } finally {
                            databaseHandler.close();
                        }

                        Intent intent = new Intent(ReviewOrderActivity.this, MyRecordingsActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }


    class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public TextView btn_turn_wifi, btn_continue;

        public CustomDialogClass(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_alert_wifi);
            btn_turn_wifi = (TextView) findViewById(R.id.btn_turn_wifi);
            btn_continue = (TextView) findViewById(R.id.btn_continue);
            btn_turn_wifi.setOnClickListener(this);
            btn_continue.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_turn_wifi:
//                    turn_wifi();
                    dismiss();
//                    Intent intent1 = new Intent(ReviewOrderActivity.this, ThankYouActivity.class);
//                    startActivity(intent1);
                    break;
                case R.id.btn_continue:
                    dismiss();
                    DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                    try {
                        databaseHandler.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //orderjson
                    OrderDetails rec = databaseHandler.getOrderDetailsList(orderId);

                    Log.e("", rec.toString());


                    databaseHandler.close();
                    rec.setFlag(1);
//                    SimpleDateFormat df = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
//                    Date createDate = new Date();
//                    Calendar c = Calendar.getInstance();
//                    c.setTime(createDate);
//                    // String fromDate = df.format(Date.parse(c.getTime().toString()));
//                    String fromDate = c.getTime().toString();
//                    rec.setDelivery_date(fromDate);

                    try {
                        databaseHandler.open();
                        databaseHandler.updateorderdetailFlags(rec);
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    } finally {
                        databaseHandler.close();
                    }

                    try {
                        new OrderWeberviceCall().execute();

                    } catch (Exception e) {

                        GlobalData.printError(e);
                    }


                    break;
                default:
                    break;

            }
        }

    }

    private void turn_wifi() {

        try {
            WifiManager wifiManager = (WifiManager) this
                    .getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);

            //     ws_call();
        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }


    private void check_connection(View v) {


        int network = GlobalData.getUploadkey(context);


        Map<String, String> networkDetails = getConnectionDetails();
        if (networkDetails.isEmpty()) {
            GlobalData.showSnackBar(v, getResources().getString(R.string.ERR_CONNECTION), false);
        } else {

            if (networkDetails.containsKey("State")) {
                if (networkDetails.get("State").equals("CONNECTED")) {
                    if (networkDetails.containsKey("Type")) {
                        if ((networkDetails.get("Type").equals("WIFI")) && network == 1 || network == 3) {
                            ws_call();

                        } else if ((networkDetails.get("Type").equals("WIFI")) && network == 2 || network == 4)

                        {
                            CustomDialogClassNew cdd = new CustomDialogClassNew(context);
                            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            cdd.setCancelable(true);
                            cdd.show();

                        } else if ((networkDetails.get("Type").equals("MOBILE")) && network == 1 || network == 2 || network == 4) {
                            CustomDialogClass cdd = new CustomDialogClass(context);
                            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            cdd.setCancelable(true);
                            cdd.show();
                        }


                    }
                }
            }
        }
    }

    private void ws_call() {


        DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
        try {
            databaseHandler.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //orderjson
        OrderDetails rec = databaseHandler.getOrderDetailsList(orderId);

        Log.e("", rec.toString());

        try {

            Map<String, String> networkDetails1 = getConnectionDetails();
            if (networkDetails1.isEmpty()) {
                GlobalData.showSnackBar(new View(context), getResources().getString(R.string.ERR_CONNECTION), false);
            } else {
                new OrderWeberviceCall().execute();
            }


        } catch (Exception e) {

            GlobalData.printError(e);
        }

        databaseHandler.close();
        rec.setFlag(1);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a  dd MMM yyyy");
        Date createDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(createDate);
        // String fromDate = df.format(Date.parse(c.getTime().toString()));
        String fromDate = c.getTime().toString();
        rec.setDelivery_date(fromDate);

        try {
            databaseHandler.open();
            databaseHandler.updateorderdetailFlags(rec);
        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            databaseHandler.close();
        }
    }


    class CustomDialogClassNew extends Dialog implements
            View.OnClickListener {

        public TextView btn_turn_wifi, btn_continue;

        public CustomDialogClassNew(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.new_alert);
            btn_turn_wifi = (TextView) findViewById(R.id.btn_turn_wifi);
            btn_continue = (TextView) findViewById(R.id.btn_continue);
            btn_turn_wifi.setOnClickListener(this);
            btn_continue.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_turn_wifi:
                    try {

//                        turn_mobile(getContext(), false);

//                        Settings.Secure.putInt(ContentResolver,Settings.Secure.ANR_SHOW_BACKGROUND,0);
                        dismiss();
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    break;
                case R.id.btn_continue:
                    dismiss();
                    ws_call();

                    break;
                default:
                    break;

            }
        }

    }

    private void turn_mobile(Context context, boolean enabled) throws Exception {
        try {

            ConnectivityManager conman = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressWarnings("rawtypes")
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            @SuppressWarnings("unchecked")
            Class[] cArg = new Class[2];
            cArg[0] = String.class;
            cArg[1] = Boolean.TYPE;
            Method setMobileDataEnabledMethod;

            setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", cArg);

            Object[] pArg = new Object[2];
            pArg[0] = context.getPackageName();
            pArg[1] = true;
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManager, pArg);

            //  ws_call();
        } catch (Exception e) {
            GlobalData.printError(e);
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
                Intent intent = new Intent(ReviewOrderActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ReviewOrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);

            }

        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(ReviewOrderActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(ReviewOrderActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewOrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);

            }


        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(ReviewOrderActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ReviewOrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);

            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(ReviewOrderActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(ReviewOrderActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(ReviewOrderActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }


        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(ReviewOrderActivity.this, AboutusActivity.class);
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
//                    Intent intent = new Intent(ReviewOrderActivity.this, HomeActivity.class);
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

                Intent intent = new Intent(ReviewOrderActivity.this, FeedbackActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(ReviewOrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);

            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(ReviewOrderActivity.this, OrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Edit", true);
            startActivity(intent);
            finish();
        }

    }

    public class AddOrderDetails extends AsyncTask<Void, Void, Boolean> {

        OrderDetails orderRec;

        AddOrderDetails(OrderRec orderRec) {
            orderRec = orderRec;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean f = true;
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            try {
                databaseHandler.open();
                databaseHandler.addOrderRec(orderRec);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return f;


        }

        protected void onCancelled() {
            order_rec = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    }


//Webservice Call in backgroud

    class OrderWeberviceCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {

        WebServiceResonse resonse = new WebServiceResonse();

        // Set Data
        LinkedList<File_Meta_JSON> fileList = new LinkedList<>();

        Users_JSON users_json = new Users_JSON();
        TransparentProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new TransparentProgressDialog(context);
            pd.show();

            progress_bar_new.setVisibility(View.GONE);
            btn_complete_order.setEnabled(false);
        }

        @Override
        protected WebServiceResonse doInBackground(String... params) {

            try {


                DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                try {
                    databaseHandler.open();

                    orderDetailsInfo = databaseHandler.getOrderDetails(orderId);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                databaseHandler.close();


                orderDetailsInfo.setFlag(1);

                orderDetailsInfo.setOrder_status_id("aud-pen");
                orderDetailsInfo.setTo_date(toDate);


                for (int i = 0; i < selectedFiles.size(); i++) {
                    File_Meta_JSON file_meta_json = new File_Meta_JSON();
                    file_meta_json.setUser_id("" + GlobalData.userSelected.getUser_id());
                    file_meta_json.setOrder_id(String.valueOf(orderDetailsInfo.getOrder_id()));
                    file_meta_json.setTemp_media_id(String.valueOf(selectedFiles.get(i).getRecId()));
                    file_meta_json.setFile_status("");
                    file_meta_json.setSource_type(String.valueOf(selectedFiles.get(i).getSourceTypeId()));
                    file_meta_json.setSourcelink(selectedFiles.get(i).getSourceLink());
                    file_meta_json.setFile_name(selectedFiles.get(i).getRecName());
                    file_meta_json.setFile_description(selectedFiles.get(i).getRecDesc());
                    file_meta_json.setFile_duration(selectedFiles.get(i).getRecDuration());
                    file_meta_json.setFile_localpath(selectedFiles.get(i).getRecLocalPath());
                    file_meta_json.setFile_upload_duration(selectedFiles.get(i).getRecUploadDuration());
                    file_meta_json.setFile_up_conn_mode(selectedFiles.get(i).getUploadingConnectionMode());


                    fileList.add(file_meta_json);
                }

                // Create Order Free JSON
                JSONArray array = new JSONArray();

                for (int k = 0; k < free_services.size(); k++) {
                    JSONObject object = new JSONObject();
                    object.put("free_serv_id", "" + free_services.get(k).getFree_service_id());
                    array.put(object);
                }

                JSONObject freeServ = new JSONObject();
                freeServ.put("OrderFreeServiceJson", array);


                try {
                    users_json.parseJSON(new JSONObject(sharedPreferencesUtility.getString(GlobalData.userKey, "")));

                } catch (Exception e) {
                    GlobalData.printError(e);
                }

                orderDetailsInfo.setTotal_files("" + fileList.size());

                orderDetailsInfo.setFile_meta_jsonsList(fileList);

                orderDetailsInfo.setDelivery_date(GlobalData.getLocalToIndianStanderdTime(orderDetailsInfo.getDelivery_date()));


                resonse = setPlaceOrderWebservice(orderDetailsInfo, fileList, users_json, freeServ);


            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }

        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            try {

                if (pd != null) {
                    pd.dismiss();
                    pd = null;
                }

                btn_complete_order.setEnabled(true);


                setOrderDetails(res, orderDetailsInfo.getOrder_id());
                progress_bar_new.setVisibility(View.GONE);


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    }


    public WebServiceResonse setPlaceOrderWebservice(OrderDetails orderDetails, LinkedList<File_Meta_JSON> fileMetaList, Users_JSON users, JSONObject freesev) {

        // Webservice Calling Place Order

        WebServiceResonse res = new WebServiceResonse();
        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        Gson gson = new Gson();
        try {

            // INput Parameter

            orderDetails.setUser_id(GlobalData.userSelected.getUser_id());
            String order_JSON = gson.toJson(orderDetails).toString();

            JSONArray arry = new JSONArray();
            for (int i = 0; i < fileMetaList.size(); i++) {

                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(fileMetaList.get(i)));

                    arry.put(jsonObject);


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }

            JSONObject media = new JSONObject();
            media.put("MediaEntity", arry);


            String fileMetaListJSON = media.toString();

            String userJSON = gson.toJson(users).toString();

            orderDetailsInfo.setFile_meta_jsonsList(fileMetaList);
            res = webServiceMySQl.PlaceOrder(new JSONObject(gson.toJson(orderDetails)), media, new JSONObject(gson.toJson(users)), freesev);


        } catch (Exception e) {


            GlobalData.printError(e);
        }


        return res;


    }

    // Get Order Server Details Information

    public boolean setOrderDetails(WebServiceResonse res, int localOrderId) {

        boolean dataSavedValues = false;

//        {"status":"200","message": "Your Order with Assignment number CAPGEJ-3M is now confirmed !","assignment_no":"CAPGEJ-3M" ,
//         "file_meta_json":"[ {"order_media_id": "5", "user_id": "2", "assignment_no": "CAPGEJ-3M", "file_status": "6", "source_type": "66", "file_name": "666", "file_description": "666"},
//        {"order_media_id": "6", "user_id": "2", "assignment_no": "CAPGEJ-3M", "file_status": "6", "source_type": "66", "file_name": "33333", "file_description": "666"}]"}

        String assignment_no = "";
        LinkedList<Recording> recList = new LinkedList<>();
        try {

            JSONObject object = res.getJsonObject();

            if (object.getString("status").equalsIgnoreCase("200")) {

                try {

                    int order_id = 0;
                    try {
                        order_id = Integer.parseInt(object.getString("order_id"));
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    orderDetailsInfo.setServer_Id(order_id);
                    orderDetailsInfo.setAssignment_no(object.getString("assignment_no"));


                    JSONArray array = object.getJSONArray("file_meta_json");

                    for (int i = 0; i < array.length(); i++) {

                        try {
                            JSONObject ob = array.getJSONObject(i);
                            assignment_no = ob.getString("assignment_no");



                            Recording recording = new Recording();
                            try {
                                recording.setRecId(Integer.parseInt(ob.getString("temp_media_id")));
                                recording.setServerId(Integer.parseInt(ob.getString("order_media_id")));
                                recording.setUpMasterId((ob.getString("file_status")));
                                recording.setAssignment_no(assignment_no);



                                //Set Assignment Number
                                if (recording.getUpMasterId().length() <= 0) {
                                    recording.setUpMasterId("uploading");
                                }

                                recList.add(recording);
                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }


                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }

                    }

                    // Add response data to database
                    // Update Order Data

                    DatabaseHandlerNew db = new DatabaseHandlerNew(context);

                    try {
                        db.open();

                        // update Order
                        if (assignment_no.length() > 0) {
                            db.updateOrderDetails(orderId, assignment_no, order_id);
                        } else {

                            GlobalData.printMessage("Not getting assignment no");

                        }

                        //Update Recording
                        if (recList.size() > 0) {

                            db.updateRecording(recList);

                            //// Upload recording

                            OrderDetails orderDetails = new OrderDetails();


                            for (int i = 0; i < recList.size(); i++) {


                                Recording recording = recList.get(i);

                                for (int j = 0; j < selectedFiles.size(); j++) {

                                    try {
                                        if (selectedFiles.get(j).getRecId() == recording.getRecId()) {

                                            selectedFiles.get(j).setServerId(recording.getServerId());
                                            selectedFiles.get(j).setUpMasterId(recording.getUpMasterId());
                                            selectedFiles.get(j).setAssignment_no("" + recording.getAssignment_no());

                                        }


                                    } catch (Exception e) {
                                        GlobalData.printError(e);
                                    }
                                }
                            }

                            new UploadRecordingFiles().execute(selectedFiles);

                        }

                        dataSavedValues = true;

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    } finally {
                        db.close();
                    }


                } catch (Exception e) {
                    GlobalData.printError(e);
                }


                Bundle  b = new Bundle();
                b.putString(GlobalData.SHARE_ASSIGNMENT_ID, ""+orderDetailsInfo.getAssignment_no());
                b.putString(GlobalData.SHARE_DELV_DATE, ""+txt_delivery_date_time.getText().toString());
                b.putString(GlobalData.SHARE_TOTAL_FEE, ""+ orderDetailsInfo.getTotal_fees());
                Intent intent = new Intent(ReviewOrderActivity.this, ThankYouActivity.class);
                intent.putExtras(b);


                Gson gson = new Gson();
                String myJson = gson.toJson(orderDetailsInfo);


                intent.putExtra("order_details", "" + myJson);

                startActivity(intent);
//                startActivity(new Intent(context, ThankYouActivity.class).putExtras(b));

                /*Intent intent = new Intent(ReviewOrderActivity.this, ThankYouActivity.class);
                intent.putExtra("assignment_no", "" + assignment_no);
                intent.putExtra("delivery_date", "" + delivery_date_time);
                intent.putExtra("total_fees", "" + total_fee);

                startActivity(intent);*/


            } else {

                /// error In uploading
                String msg = "";
                try {
                    msg = object.getString("message");


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

                if (msg.length() > 0) {

                    GlobalData.showSnackBar(btn_complete_order, msg, true);
                }

            }

            if (object.getString("message").length() > 0) {
                GlobalData.showSnackBar(btn_complete_order, object.getString("message"), true);

            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return dataSavedValues;
    }


    public class UploadRecordingFiles extends AsyncTask<LinkedList<MyRecording>, String, String> {


        @Override
        protected String doInBackground(LinkedList<MyRecording>... params) {

            try {
                GlobalData.selectedOrder = orderDetailsInfo;
                GlobalData.selectedMyRecording = params[0];

                Intent intent = new Intent(context, BackgroundService.class);
                startService(intent);


//                FileUploading uploading=new FileUploading(context,params[0],orderDetailsInfo) ;
//
//                uploading.setData();

            } catch (Exception e) {
                GlobalData.printError(e);
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }


}
