package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.adapter.ChangePriceAdapter;
import com.voxtab.ariatech.voxtab.adapter.SelectedFilesAdapter;
import com.voxtab.ariatech.voxtab.bean.ChangePrice;
import com.voxtab.ariatech.voxtab.bean.Delivery_option;
import com.voxtab.ariatech.voxtab.bean.Delivery_slot;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.Holidays;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Price;
import com.voxtab.ariatech.voxtab.bean.Service_type;
import com.voxtab.ariatech.voxtab.bean.TAT_Calculation;
import com.voxtab.ariatech.voxtab.bean.TimeStamb;
import com.voxtab.ariatech.voxtab.bean.Time_slab;
import com.voxtab.ariatech.voxtab.bean.Timestamps_cal;
import com.voxtab.ariatech.voxtab.bean.Transcription_type;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.bean.VAS_Rate;
import com.voxtab.ariatech.voxtab.bean.Vas;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.TATCalculationGLobal;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 * Created by Local User on 15-Feb-16.
 */
public class OrderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    public static ListView lst_price_list;
    boolean flag;
    LinearLayout lin_plan;
    LinkedList<OrderDetails> recLast;

    String service_type_id = "", transcription_type_id = "", timestamp_duration_id = "", valueadded_id = "", deliveryOptionId = "ECO", timeSlabId = "", st = "", new_todate = "";
    ;
    int orderId = 0;

    private boolean isLoggedIn;
    private TextView txt_header_login;

    private ChangePriceAdapter changePriceAdapter;
    private SelectedFilesAdapter selectedFilesAdapter;
    //    private GetSchemeDetails records = null;
    private GetServiceTypes service_Types = null;
    private GetTranscriptionType transcription_Type = null;
    private GetTimestampDuration timestamp_Duration = null;
    private GetValueAddedServices value_Added_Services = null;
    private GetFreeServices free_Services = null;
    private GetTimeSlabs time_Slabs = null;
    private GetPrice price = null;
    private GetVASPrice VAS_Price = null;
    private GetDeliveryOption delivery_option = null;
    private GetHolidays holidays = null;
    private GetDeliverySlot deliverySlot = null;
    private GetTimeStampCalculation timestampcalculation = null;

    private AddOrderREC add_records = null;

    LinkedList<Free_services> freeServicesLinkedList;
    LinkedList<Service_type> freeServiceTypeLinkedList;
    LinkedList<ChangePrice> changePriceLinkedList;
    LinkedList<Transcription_type> transcriptionTypeLinkedList;
    LinkedList<TimeStamb> timesStampLinkedList;
    LinkedList<Vas> valueAddedServicesLinkedList;
    public static ArrayList<Time_slab> timeSlabs;
    public static ArrayList<Delivery_option> deliveryOption;
    public static ArrayList<Price> priceArrayList;
    public static ArrayList<VAS_Rate> VASPrice_list;
    public ArrayList<Holidays> holidays_list;
    public static ArrayList<Delivery_slot> deliverySlot_list;
    public static ArrayList<Timestamps_cal> timeStampCalculationArrayList;
    public static ArrayList<VAS_Rate> vasPriceArrayList;

    Time_slab selectedTimeslab;
    Delivery_option deliveryOption_obj;
    Delivery_slot deliverySlot_obj;
    Price price_obj, selectedPrice;
    VAS_Rate VASPrice_obj;
    Holidays holidays_obj;
    Timestamps_cal timeStampCalculation_obj;

    int totalDuration = 0, tat_value = 0;
    float totalMinutes = 0, totalFees = 0;
    String timeslab_name = "";
    String CreatedDate = "";

    TextView txt_total_duration, txt_total_fee, txt_delivery_date_time;
    TextView txt_change;

    Spinner spn_service_type, spn_transcription_type, spn_timestamp_duration, spn_value_added_services;

    TextView ch_free_service1, ch_free_service2, ch_free_service3, ch_free_service4, ch_free_service5;

    EditText edt_subject_file, edt_instr_order;

    ListView lst_selected_files;

    Button btn_order, btn_cancel;

    ArrayList<MyRecording> myList;
    LinkedList<MyRecording> list;
    PopupWindow popupWindow;


    public static String total_duration = "", total_fee = "", delivery_date_time = "", subject_file = "", instr_order = "";
    String free_services, transcription_type_name = "", timestamp_duration_name = "";
    String time = "";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    Calendar currnetTime = Calendar.getInstance();


    TAT_Calculation selectedTAT_calculation = new TAT_Calculation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        GlobalData.activities.add(OrderActivity.this);
        context = this;
        toolbar = GlobalData.initToolBarMenu(this, true, true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        list = GlobalData.sList;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);




        GlobalData.setLoginAndLogout(context, navigationView, getIntent());


        isLoggedIn = GlobalData.isLoggedIn(context);
//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);
//
//
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
//
//
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


        try {
//public float getTotalFeesAndDuration(int totalDurationinMin,String  delivery_opt_id,String service_type_id,String vas_id,String timestamp_id){

//            getTotalFeesAndDuration(150,"ECO","eng","copy-edit","5min");


            loadInitDBData();
        } catch (Exception e) {

            GlobalData.printError(e);

        }


    }


    private void init() {
        //selected files
        lst_selected_files = (ListView) findViewById(R.id.lst_selected_files);
        txt_total_fee = (TextView) findViewById(R.id.txt_total_fee);
        txt_delivery_date_time = (TextView) findViewById(R.id.txt_delivery_date_time);


      //  setplan();

        txt_delivery_date_time.setText("");
        txt_total_fee.setText("");


        selectedFilesAdapter = new SelectedFilesAdapter(context, list);
        lst_selected_files.setAdapter(selectedFilesAdapter);


        //new
        txt_total_duration = (TextView) findViewById(R.id.txt_total_duration);
        try {

            for (int i = 0; i < list.size(); i++) {
                totalDuration = totalDuration + GlobalData.parseSeconds(list.get(i).getRecDuration());

                //get totalmins
                totalMinutes = totalMinutes + (float) GlobalData.parseTimeToMinute(list.get(i).getRecDuration());

                Log.e("recduration", list.get(i).getRecDuration());
                CreatedDate = list.get(i).getCreatedDate();
            }
//            totalMinutes = 73;


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        int millis = totalDuration * 1000;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        time = df.format(new Date(millis));
        System.out.println(time);

        txt_total_duration.setText("" + time);

        lst_selected_files.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    /*    txt_change = (TextView) findViewById(R.id.txt_change);
        SpannableString change = new SpannableString("Change");
        change.setSpan(new UnderlineSpan(), 0, change.length(), 0);
        txt_change.setText(change);
        txt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });*/


        //select service details
        spn_service_type = (Spinner) findViewById(R.id.spn_service_type);

        spn_transcription_type = (Spinner) findViewById(R.id.spn_transcription_type);

        spn_timestamp_duration = (Spinner) findViewById(R.id.spn_timestamp_duration);

        spn_value_added_services = (Spinner) findViewById(R.id.spn_value_added_services);

        //Free services
        ch_free_service1 = (TextView) findViewById(R.id.ch_free_service1);
        ch_free_service2 = (TextView) findViewById(R.id.ch_free_service2);
        ch_free_service3 = (TextView) findViewById(R.id.ch_free_service3);
        ch_free_service4 = (TextView) findViewById(R.id.ch_free_service4);
        ch_free_service5 = (TextView) findViewById(R.id.ch_free_service5);


        //Edittext
        edt_subject_file = (EditText) findViewById(R.id.edt_subject_file);
        edt_instr_order = (EditText) findViewById(R.id.edt_instr_order);


        //buttons
        btn_order = (Button) findViewById(R.id.btn_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOrderDetails();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              cancel();
                                          }
                                      }
        );


        spn_service_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service_type_id = GlobalData.serviceTypes.get(position).getService_type_id();
                st = service_type_id;
                if (service_type_id.equals("eng")) {
//                    loadValueAddedServices();
                    spn_value_added_services.setVisibility(View.VISIBLE);
                } else {
                    spn_value_added_services.setVisibility(View.GONE);

                }

                setData();

//                loadDeliveryOption();
//                loadDelivereySlots();
//                loadGetTimeStampCalculation();
//                loadVASPrice();
//                loadHolidays();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    /*    spn_transcription_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                transcription_type_id = GlobalData.transcriptionType.get(position).getTransriptionTypeId();
                transcription_type_name = GlobalData.transcriptionType.get(position).getTransriptionType();

                ch_free_service5.setText(transcription_type_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spn_timestamp_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                timestamp_duration_id = GlobalData.timestampDuration.get(position).getTimestampId();

                timestamp_duration_name = GlobalData.timestampDuration.get(position).getTimestampDuration();

                ch_free_service3.setText(timestamp_duration_name);
               // ch_free_service3.setText(GlobalData.timestamp_duration_name);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        spn_transcription_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                transcription_type_id = GlobalData.transcriptionType.get(position).getTrans_type_id();

                ch_free_service5.setText(GlobalData.transcriptionType.get(position).getTrans_type_txt());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_timestamp_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                timestamp_duration_id = GlobalData.timestampDuration.get(position).getTimestamp_id();
                ch_free_service3.setText(GlobalData.timestampDuration.get(position).getTimestamp_txt());

                setData();
//                loadDeliveryOption();
//                loadDelivereySlots();
//                loadGetTimeStampCalculation();
//                loadVASPrice();
//                loadHolidays();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spn_value_added_services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueadded_id = GlobalData.valueAddedServices.get(position).getVas_id();

//                loadDeliveryOption();
//                loadDelivereySlots();
//                loadGetTimeStampCalculation();
//                loadVASPrice();
//                loadHolidays();
                setData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        flag = getIntent().getBooleanExtra("Edit", false);

        if (flag == true) {
            ch_free_service1.setText(GlobalData.free_services_accent);
            ch_free_service2.setText(GlobalData.free_services_terminology);
            ch_free_service3.setText(GlobalData.free_services_timestamp);
            ch_free_service4.setText(GlobalData.free_services_identification);
            ch_free_service5.setText(GlobalData.free_services_type);
//            deliveryOptionId = 1;
            try {
                DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                databaseHandler.open();
                LinkedList<OrderDetails> recLinkedList = databaseHandler.getOrderDetails();

                service_type_id = recLinkedList.get(recLinkedList.size() - 1).getService_type_id();
                transcription_type_id = recLinkedList.get(recLinkedList.size() - 1).getTrans_type_id();
                timestamp_duration_id = recLinkedList.get(recLinkedList.size() - 1).getTime_slab_id();
                valueadded_id = recLinkedList.get(recLinkedList.size() - 1).getVas_id();
                deliveryOptionId = recLinkedList.get(recLinkedList.size() - 1).getDelivery_opt_id();
                orderId = recLinkedList.get(recLinkedList.size() - 1).getOrder_id();


                loadSpinnerData();
                loadTranscriptionType();
                loadTimestampDuration();
                loadValueAddedServices(0);
                loadFreeServices();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {


            try {
                DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                databaseHandler.open();
                recLast = databaseHandler.getOrderDetails();

                if (recLast.size() > 0) {
                    service_type_id = recLast.get(recLast.size() - 1).getService_type_id();
                    transcription_type_id = recLast.get(recLast.size() - 1).getTrans_type_id();
                    timestamp_duration_id = recLast.get(recLast.size() - 1).getTime_slab_id();
                    valueadded_id = recLast.get(recLast.size() - 1).getVas_id();
                    deliveryOptionId = recLast.get(recLast.size() - 1).getDelivery_opt_id();
//                    orderId = recLast.get(recLast.size() - 1).getOrderId();
                    loadSpinnerData();
                    loadTranscriptionType();
                    loadTimestampDuration();
                    loadValueAddedServices(0);
                    loadFreeServices();

                } else {
                    deliveryOptionId = "ECO";
                    loadSpinnerData();
                    loadTranscriptionType();
                    loadTimestampDuration();
                    loadValueAddedServices(0);
                    loadFreeServices();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    private void setplan() {

        lst_price_list = (ListView) findViewById(R.id.lst_plans);
        changePriceAdapter = new ChangePriceAdapter(context, GlobalData.change_price_arraylist);
        lst_price_list.setAdapter(changePriceAdapter);
        changePriceAdapter.setSelectedIndex(0);
        changePriceAdapter.notifyDataSetChanged();
        lst_price_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                deliveryOptionId = GlobalData.change_price_arraylist.get(position).getId();
                setSelectedTAT(GlobalData.change_price_arraylist.get(position).getTat_calculation());
                changePriceAdapter.setSelectedIndex(position);
                changePriceAdapter.notifyDataSetChanged();



            }
        });


    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

//    private void change() {
//
//        if (popupWindow != null) {
//            if (popupWindow.isShowing() == true) {
//                popupWindow.dismiss();
//            } else {
//                LayoutInflater layoutInflater =
//                        (LayoutInflater) getBaseContext()
//                                .getSystemService(LAYOUT_INFLATER_SERVICE);
//                final View popupView = layoutInflater.inflate(R.layout.popup, null);
//                popupWindow = new PopupWindow(
//                        popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lst_price_list = (ListView) popupView.findViewById(R.id.lst_plans);
//                setAdapaterData();
//                lst_price_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        deliveryOptionId = GlobalData.change_price_arraylist.get(position).getId();
////                        txt_total_fee.setText(GlobalData.change_price_arraylist.get(position).getPrice());
////                        String showdate = GlobalData.showdate(Glob
//// alData.change_price_arraylist.get(position).getDate_time());
////                        txt_delivery_date_time.setText(showdate);
////                        currnetTime.setTimeInMillis(GlobalData.change_price_arraylist.get(position).getDeliveryTime().getTimeInMillis());
////                        new_todate = GlobalData.change_price_arraylist.get(position).getDate_to();
//
//                        setSelectedTAT(GlobalData.change_price_arraylist.get(position).getTat_calculation());
//
//                        popupWindow.dismiss();
//                    }
//                });
//                popupWindow.showAsDropDown(txt_change, 50, 0);
//            }
//        } else {
//            LayoutInflater layoutInflater =
//                    (LayoutInflater) getBaseContext()
//                            .getSystemService(LAYOUT_INFLATER_SERVICE);
//            final View popupView = layoutInflater.inflate(R.layout.popup, null);
//            popupWindow = new PopupWindow(
//                    popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lst_price_list = (ListView) popupView.findViewById(R.id.lst_change);
//            setAdapaterData();
//            lst_price_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // Toast.makeText(context, "Selected index " + position, Toast.LENGTH_LONG).show();
//
//
//                    deliveryOptionId = GlobalData.change_price_arraylist.get(position).getId();
////
////                   txt_total_fee.setText(GlobalData.change_price_arraylist.get(position).getPrice());
////
////                    String showdate = GlobalData.showdate(GlobalData.change_price_arraylist.get(position).getDate_time());
////                    txt_delivery_date_time.setText(showdate);
////                    currnetTime.setTimeInMillis(GlobalData.change_price_arraylist.get(position).getDeliveryTime().getTimeInMillis());
////                    new_todate = GlobalData.change_price_arraylist.get(position).getDate_to();
//
//
//                    setSelectedTAT(GlobalData.change_price_arraylist.get(position).getTat_calculation());
//
//                    popupWindow.dismiss();
//                }
//            });
//            popupWindow.showAsDropDown(txt_change, 50, 0);
//        }
//
//    }

    private void cancel() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.cancel_alert))
                .setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(OrderActivity.this, MyRecordingsActivity.class);
                        startActivity(intent);
                    }
                }).create().show();

    }


    public void setAdapaterData() {

        changePriceAdapter = new ChangePriceAdapter(context, GlobalData.change_price_arraylist);
        lst_price_list.setAdapter(changePriceAdapter);
        changePriceAdapter.setSelectedIndex(0);
        changePriceAdapter.notifyDataSetChanged();
    }


    private void loadSpinnerData() {
        try {
            service_Types = new GetServiceTypes();
            service_Types.execute((Void) null);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


//    private void loadDeliveryOption() {
//        try {
//            delivery_option = new GetDeliveryOption();
//            delivery_option.execute((Void) null);
//        } catch (Exception e) {
//            GlobalData.printError(e);
//        }
//    }


    private void loadTranscriptionType() {
        try {
            transcription_Type = new GetTranscriptionType();
            transcription_Type.execute((Void) null);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void loadTimestampDuration() {
        try {
            timestamp_Duration = new GetTimestampDuration();
            timestamp_Duration.execute((Void) null);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void loadValueAddedServices(int pos) {
        try {
            value_Added_Services = new GetValueAddedServices(pos);
            value_Added_Services.execute();
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void loadHolidays() {
        try {
            holidays = new GetHolidays();
            holidays.execute();
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void loadDelivereySlots() {
        try {
            deliverySlot = new GetDeliverySlot();
            deliverySlot.execute();
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    private void loadTimeSlabs(double totalMinutes) {
        try {
            time_Slabs = new GetTimeSlabs(totalMinutes);
            time_Slabs.execute();

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }


    private void loadPrice() {
        try {
            price = new GetPrice();
            price.execute();

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }

    private void loadGetTimeStampCalculation() {
        try {
            timestampcalculation = new GetTimeStampCalculation();
            timestampcalculation.execute();

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }

    private void loadVASPrice() {
        try {
            VAS_Price = new GetVASPrice();
            VAS_Price.execute();

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }


    private void loadFreeServices() {
        try {
            free_Services = new GetFreeServices();
            free_Services.execute((Void) null);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public class GetServiceTypes extends AsyncTask<Void, Void, LinkedList> {
        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getService_type();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            service_Types = null;
            GlobalData.serviceTypes = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    freeServiceTypeLinkedList = new LinkedList<>();
                    freeServiceTypeLinkedList = list_details;
                    GlobalData.name = new ArrayList<String>();
                    for (int i = 0; i < freeServiceTypeLinkedList.size(); i++) {
                        // name.add(list.get(i).getServiceType());
                        GlobalData.name.add(GlobalData.serviceTypes.get(i).getService_text());
                    }
                    ArrayAdapter<String> spn_service_typeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, GlobalData.name);
                    spn_service_type.setAdapter(spn_service_typeadapter);
                    if (recLast != null) {
                        if (recLast.size() > 0) {

                        } else {
                            service_type_id = GlobalData.serviceTypes.get(0).getService_type_id();
                        }
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            service_Types = null;
        }

    }


    public class GetTranscriptionType extends AsyncTask<Void, Void, LinkedList> {
        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getTranscription_type();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            transcription_Type = null;
            GlobalData.transcriptionType = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    transcriptionTypeLinkedList = new LinkedList<>();
                    transcriptionTypeLinkedList = list_details;
                    GlobalData.type = new ArrayList<String>();
                    for (int i = 0; i < transcriptionTypeLinkedList.size(); i++) {
                        GlobalData.type.add(GlobalData.transcriptionType.get(i).getTrans_type_txt());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, GlobalData.type);
                    spn_transcription_type.setAdapter(adapter);

                    if (recLast != null) {
                        if (recLast.size() > 0) {

                        } else {
                            transcription_type_id = GlobalData.transcriptionType.get(0).getTrans_type_id();
                        }
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            transcription_Type = null;
        }

    }


    public class GetTimestampDuration extends AsyncTask<Void, Void, LinkedList> {
        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();

                list_details = databaseHandler.getTimestamp();

            } catch (Exception e) {

                GlobalData.printError(e);


            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            timestamp_Duration = null;
            GlobalData.timestampDuration = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    timesStampLinkedList = new LinkedList<>();
                    timesStampLinkedList = list_details;
                    GlobalData.duration = new ArrayList<String>();
                    for (int i = 0; i < timesStampLinkedList.size(); i++) {
                        GlobalData.duration.add(GlobalData.timestampDuration.get(i).getTimestamp_txt());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, GlobalData.duration);
                    spn_timestamp_duration.setAdapter(adapter);

                    if (recLast != null) {
                        if (recLast.size() > 0) {

                        } else {
                            timestamp_duration_id = GlobalData.timestampDuration.get(0).getTimestamp_id();
                        }
                    }

                }
            }
        }

        @Override
        protected void onCancelled() {
            timestamp_Duration = null;
        }

    }

    public class GetValueAddedServices extends AsyncTask<Void, Void, LinkedList> {

        int position;

        GetValueAddedServices(int pos) {
            position = pos;
        }

        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getVas();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            value_Added_Services = null;
            GlobalData.valueAddedServices = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    valueAddedServicesLinkedList = new LinkedList<>();
                    valueAddedServicesLinkedList = list_details;
                    GlobalData.services = new ArrayList<String>();
                    for (int i = 0; i < valueAddedServicesLinkedList.size(); i++) {

                        GlobalData.services.add(GlobalData.valueAddedServices.get(i).getVas_text());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, GlobalData.services);
                    spn_value_added_services.setAdapter(adapter);
                    if (flag == true) {
                        int valueadded_pos = GlobalData.valueAddedServices.indexOf(new Object() {
                            public boolean equals(Object obj) {
                                return ((Vas) obj).getVas_id().equals(valueadded_id);
                            }
                        });
                        spn_value_added_services.setSelection(valueadded_pos);
//                        valueadded_id = GlobalData.valueAddedServices.get(position).getVas_id();
                    } else {
                        if (recLast != null) {
                            if (recLast.size() > 0) {

                            } else {
                                valueadded_id = GlobalData.valueAddedServices.get(0).getVas_id();
                            }
                        }
                    }
                }
            }
        }


        @Override
        protected void onCancelled() {
            value_Added_Services = null;
        }

    }


    public class GetFreeServices extends AsyncTask<Void, Void, LinkedList> {
        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getFree_services();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            free_Services = null;
            GlobalData.freeServices = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    freeServicesLinkedList = new LinkedList<>();
                    freeServicesLinkedList = list_details;
                    GlobalData.services = new ArrayList<String>();
                    for (int i = 0; i < freeServicesLinkedList.size(); i++) {
                        GlobalData.services.add(GlobalData.freeServices.get(i).getFree_service_txt());
                    }
                    // ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, services);
                    // spn_value_added_services.setAdapter(adapter);
                    ch_free_service1.setText(GlobalData.services.get(0));
                    ch_free_service2.setText(GlobalData.services.get(1));
                    ch_free_service3.setText(GlobalData.services.get(2));
                    ch_free_service4.setText(GlobalData.services.get(3));
                    ch_free_service5.setText(GlobalData.services.get(4));
                    GlobalData.free_services_accent = GlobalData.services.get(0);
                    GlobalData.free_services_terminology = GlobalData.services.get(1);
                    GlobalData.free_services_timestamp = GlobalData.services.get(2);
                    GlobalData.free_services_identification = GlobalData.services.get(3);
                    GlobalData.free_services_type = GlobalData.services.get(4);

                    if (flag == true) {
                        spn_service_type.setSelection(getIndex(spn_service_type, GlobalData.service_type_name));
                        spn_transcription_type.setSelection(getIndex(spn_transcription_type, GlobalData.transcription_type_name));
                        spn_timestamp_duration.setSelection(getIndex(spn_timestamp_duration, GlobalData.timestamp_duration_name));
                        spn_value_added_services.setSelection(getIndex(spn_value_added_services, GlobalData.valueadded_name));
                    } else {
                        if (recLast.size() > 0) {

                            int service_type_pos = GlobalData.serviceTypes.indexOf(new Object() {
                                public boolean equals(Object obj) {
                                    return ((Service_type) obj).getService_type_id().equals(service_type_id);
                                }
                            });
                            spn_service_type.setSelection(service_type_pos);

                            int transcription_type_pos = GlobalData.transcriptionType.indexOf(new Object() {
                                public boolean equals(Object obj) {
                                    return ((Transcription_type) obj).getTrans_type_id().equals(transcription_type_id);
                                }
                            });
                            spn_transcription_type.setSelection(transcription_type_pos);

                            int timestamp_type_pos = GlobalData.timestampDuration.indexOf(new Object() {
                                public boolean equals(Object obj) {
                                    return ((TimeStamb) obj).getTimestamp_id().equals(timestamp_duration_id);
                                }
                            });
                            spn_timestamp_duration.setSelection(timestamp_type_pos);

                            int valueadded_pos = GlobalData.valueAddedServices.indexOf(new Object() {
                                public boolean equals(Object obj) {
                                    return ((Vas) obj).getVas_id().equals(valueadded_id);
                                }
                            });
                            spn_value_added_services.setSelection(valueadded_pos);

                        }
                    }


                    //get respective slabid
                    loadTimeSlabs(totalMinutes);

//            getSelectedValues();
                    //get days -- getservice_type,getslabid,delivery_option


                }
            }
        }

        @Override
        protected void onCancelled() {
            free_Services = null;
        }

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
                Intent intent = new Intent(OrderActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(OrderActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(OrderActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(OrderActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(OrderActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(OrderActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(OrderActivity.this, AboutusActivity.class);
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
//                    Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
//                    startActivity(intent);
//
//
//                }
//
//
//            } catch (Exception e) {
//                GlobalData.printError(e);
//            }
//        }
        else if (id == R.id.nav_feedback) {


            if (isLoggedIn) {
                Intent intent = new Intent(OrderActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
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
            Intent in = new Intent(OrderActivity.this,
                    MyRecordingsActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }

    }


    public void setOrderDetails() {

        subject_file = edt_subject_file.getText().toString();
        instr_order = edt_instr_order.getText().toString();

        OrderDetails orderRec = new OrderDetails();
        String Duration = txt_total_duration.getText().toString();
        total_fee = txt_total_fee.getText().toString();
        CreatedDate = txt_delivery_date_time.getText().toString();

        orderRec.setTotal_duration(String.valueOf(Duration));
        orderRec.setTotal_fees("" + selectedTAT_calculation.getTotalFee());
        orderRec.setDelivery_date(GlobalData.getStanderdDateFormt(currnetTime));
        orderRec.setTo_date(new_todate);

        orderRec.setService_type_id(service_type_id);
        orderRec.setTrans_type_id(transcription_type_id);
        orderRec.setTime_slab_id(timestamp_duration_id);
        orderRec.setVas_id(valueadded_id);
        orderRec.setDelivery_opt_id(deliveryOptionId);
        orderRec.setDelivery_opt_id(deliveryOptionId);

        orderRec.setFlag(2);

        orderRec.setSubject_of_file(subject_file);
        orderRec.setClient_instruction(instr_order);

        orderRec.setDisplayDelDateTime(txt_delivery_date_time.getText().toString());



        // Get Premiun Type
        String preType = "";
        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();

            preType = db.getPremium_type(selectedTAT_calculation.getTimestamp_id()).getPremium_type();

        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {

            db.close();
        }

        String descType = "";

        if (selectedTAT_calculation.getTimestamps_cal().getPercentage_value().length() <= 0) {
            descType = getResources().getString(R.string.no_discount);
        }

        float prePerHour = 0;
        try {


            if (selectedTAT_calculation.totalDuration > 0 && selectedTAT_calculation.totalFee > 0) {
                prePerHour = selectedTAT_calculation.totalFee / selectedTAT_calculation.totalDuration;
            }
        } catch (Exception e) {
            GlobalData.printError(e);
        }

        orderRec.setPremium_type("" + preType);
        orderRec.setDiscount_type("" + descType);
        orderRec.setGross_fees("" + selectedTAT_calculation.getGrossFee());
        orderRec.setPremium_per_hour("" + selectedTAT_calculation.premiumperhour);
        orderRec.setDiscount("" + selectedTAT_calculation.getDiscount_amt());
        orderRec.setTotal_discount("" + selectedTAT_calculation.getDiscount_amt());
        orderRec.setTotal_premium("" + selectedTAT_calculation.getTotalPremium());

        orderRec.setDisplayDelDateTime(txt_delivery_date_time.getText().toString());
        orderRec.setTotalDurationMin("" + totalDuration);

        if (flag == true) {
            orderRec.setOrder_id(orderId);
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            try {
                databaseHandler.open();
                databaseHandler.updateorderdetails(orderRec);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }

            GlobalData.selectedFiles = new LinkedList<>();
            GlobalData.selectedFiles.add(orderRec);

            getSelectedValues();
            Intent intent = new Intent(OrderActivity.this, ReviewOrderActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        } else {
            add_records = new AddOrderREC(orderRec);
            add_records.execute((Void) null);

        }


    }


    private void getSelectedValues() {
        if (spn_value_added_services.getVisibility() == View.VISIBLE) {
            GlobalData.valueadded_name = spn_value_added_services.getItemAtPosition(spn_value_added_services.getSelectedItemPosition()).toString();
        } else {
            // Either gone or invisible
            GlobalData.valueadded_name = "";
        }

        GlobalData.timestamp_duration_name = spn_timestamp_duration.getItemAtPosition(spn_timestamp_duration.getSelectedItemPosition()).toString();
        GlobalData.service_type_name = spn_service_type.getItemAtPosition(spn_service_type.getSelectedItemPosition()).toString();
        GlobalData.transcription_type_name = spn_transcription_type.getItemAtPosition(spn_transcription_type.getSelectedItemPosition()).toString();
    }

    public class AddOrderREC extends AsyncTask<Void, Void, Boolean> {

        OrderDetails orderRec;

        AddOrderREC(OrderDetails orderRec) {
            this.orderRec = orderRec;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean f = true;
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            try {
                databaseHandler.open();

                orderRec.setDelivery_date(GlobalData.getStanderdDateFormt(currnetTime));


                databaseHandler.addOrderRec(orderRec);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return f;


        }

        @Override
        protected void onPostExecute(final Boolean bean) {
            add_records = null;
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            try {
                databaseHandler.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LinkedList<OrderDetails> recLinkedList = databaseHandler.getOrderDetails();
            orderId = recLinkedList.get(recLinkedList.size() - 1).getOrder_id();
            databaseHandler.close();
            GlobalData.selectedFiles = new LinkedList<>();
            GlobalData.selectedFiles.add(orderRec);

            getSelectedValues();
            Intent intent = new Intent(OrderActivity.this, ReviewOrderActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
          /*  if (bean != null) {

            }*/

        }

        @Override
        protected void onCancelled() {
            add_records = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    }

    //calculate total fee for default selections
    private double calculate_total_fee_default() {

      /*  double fee = 0, val1 = 0;
        val1 = totalMinutes * tat_value;
        timestamp_duration_id;*/
        return 0;

    }

    public class GetTimeSlabs extends AsyncTask<Void, Void, LinkedList> {

        GetTimeSlabs(double totalMinutes) {
            totalMinutes = totalMinutes;
        }

        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getTime_slab();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

       /* protected void onPostExecute(final ArrayList list_details) {
            time_Slabs = null;
            timeSlabs = new ArrayList<>(list_details);
            double from_min = 0;
            double to_min = 0;

            if (list_details != null) {
                if (list_details.size() > 0) {
                    timeSlabs = list_details;
                    for (int i = 0; i < timeSlabs.size(); i++) {
                        from_min = Double.parseDouble(timeSlabs.get(i).getFromTime());
                        to_min = Double.parseDouble(timeSlabs.get(i).getToTime());
                        if (totalMinutes > from_min && totalMinutes < to_min) {
                            int tid = timeSlabs.get(i).getTimeSlabId();
                            String tname = timeSlabs.get(i).getTimeSlabName();
                            Log.e("Tname", tname);
                            Log.e("Tname", "" + tid);

                            timeSlabId = tid;
                        }

                    }
                }

            }
        }*/

        @Override
        protected void onPostExecute(LinkedList list_details) {
            time_Slabs = null;

            double from_min = 0;
            double to_min = 0;

            if (list_details != null) {
                if (list_details.size() > 0) {
                    timeSlabs = new ArrayList<>(list_details);
                    for (int i = 0; i < timeSlabs.size(); i++) {
                        selectedTimeslab = new Time_slab();

                        from_min = Double.parseDouble(timeSlabs.get(i).getSlab_from());
                        to_min = Double.parseDouble(timeSlabs.get(i).getSlab_to());
                        if (totalMinutes > from_min && totalMinutes < to_min) {
                            selectedTimeslab = new Time_slab();
                            selectedTimeslab = timeSlabs.get(i);
                            timeSlabId = selectedTimeslab.getTime_slab_id();
                            break;
                        }

                    }

//                    loadDeliveryOption();
//                    loadDelivereySlots();
//                    loadGetTimeStampCalculation();
//                    loadVASPrice();
//                    loadHolidays();


                }

            }

        }

        @Override
        protected void onCancelled() {
            time_Slabs = null;
        }

    }

    public class GetDeliveryOption extends AsyncTask<Void, Void, LinkedList> {

        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getDelivery_option();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(LinkedList list_details) {
            delivery_option = null;


            if (list_details != null) {
                deliveryOption = new ArrayList<>(list_details);
                if (list_details.size() > 0) {
                    for (int i = 0; i < deliveryOption.size(); i++) {
                        deliveryOption_obj = new Delivery_option();
                        deliveryOption_obj = deliveryOption.get(i);
                        //  String dname = deliveryOption.get(i).getDeliveryOption();
                    }
                }
            }
        }


        @Override
        protected void onCancelled() {
            delivery_option = null;
        }

    }


    public class GetDeliverySlot extends AsyncTask<Void, Void, LinkedList> {

        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getDelivery_slot();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(LinkedList list_details) {
            deliverySlot = null;


            if (list_details != null) {
                deliverySlot_list = new ArrayList<>(list_details);
                if (list_details.size() > 0) {
                    for (int i = 0; i < deliverySlot_list.size(); i++) {
                        deliverySlot_obj = new Delivery_slot();
                        deliverySlot_obj = deliverySlot_list.get(i);
                    }
                }
            }
        }


        @Override
        protected void onCancelled() {
            deliverySlot = null;
        }

    }

    public class GetVASPrice extends AsyncTask<Void, Void, LinkedList> {

        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getVASPrice(timeSlabId, valueadded_id);
            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            VAS_Price = null;


            if (list_details != null) {
                if (list_details.size() > 0) {
                    VASPrice_list = new ArrayList<>(list_details);
                }
            }

        }

        @Override
        protected void onCancelled() {
            VAS_Price = null;
        }

    }


    public class GetPrice extends AsyncTask<Void, Void, LinkedList> {

        TransparentProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new TransparentProgressDialog(context);
            pd.show();
        }

        @Override
        protected LinkedList doInBackground(Void... params) {


            return null;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details1) {
            price = null;


            try {
                //getTaDetails();
                setData();

            } catch (Exception e) {
                GlobalData.printError(e);
            }

            if (pd != null) {
                pd.dismiss();
                ;
                pd = null;
            }


        }

        @Override
        protected void onCancelled() {
            price = null;
        }

    }


    public class GetHolidays extends AsyncTask<Void, Void, LinkedList> {


        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getHolidays();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }


        @Override
        protected void onPostExecute(LinkedList lst_holiday) {
            holidays = null;

            if (lst_holiday != null) {
                if (lst_holiday.size() > 0) {
                    holidays_list = new ArrayList<>();
                    holidays_list.addAll(lst_holiday);
                    loadPrice();
                }
            }
        }


        @Override
        protected void onCancelled() {
            holidays = null;
        }

    }

    public class GetTimeStampCalculation extends AsyncTask<Void, Void, LinkedList> {


        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();

                if (deliveryOptionId.length() <= 0) {
                    deliveryOptionId = "ECO";
                }

                list_details = databaseHandler.getTimeStampCalculation(service_type_id, deliveryOptionId, timestamp_duration_id);

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }


        @Override
        protected void onPostExecute(LinkedList list_details) {
            timestampcalculation = null;
            if (list_details != null) {
                if (list_details.size() > 0) {
                    timeStampCalculationArrayList = new ArrayList<>(list_details);
                }
            }
        }


        @Override
        protected void onCancelled() {
            timestampcalculation = null;
        }

    }


    public void getTaDetails() {

        try {

            double totalFees_new = 0;
            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
            LinkedList list_details = null;
            try {
                databaseHandler.open();
                list_details = databaseHandler.getPrice();


            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            if (list_details != null) {
                priceArrayList = new ArrayList<>(list_details);
                GlobalData.change_price_arraylist = new ArrayList<>();
                Calendar c = Calendar.getInstance();
                for (int i = 0; i < priceArrayList.size(); i++) {

                    try {

                        price_obj = priceArrayList.get(i);
                        for (int j = 0; j < deliveryOption.size(); j++) {
                            if (price_obj.getDelivery_opt_id().equalsIgnoreCase(deliveryOption.get(j).getDelivery_opt_id()) && price_obj.getService_type_id().equalsIgnoreCase(service_type_id) && price_obj.getTime_slab_id().equalsIgnoreCase(timeSlabId)) {
                                ChangePrice changePrice = new ChangePrice();
                                changePrice.setId(deliveryOption.get(j).getDelivery_opt_id());
                                changePrice.setScheme_name(deliveryOption.get(j).getDel_option());


                                // Get Total Minite Cal Id
                                totalFees = totalMinutes * (float) Double.parseDouble(price_obj.getPrice());


                                if (totalFees < Integer.parseInt(price_obj.getMin_charges())) {
                                    totalFees = Integer.parseInt(price_obj.getMin_charges());
                                }


                                double timestamp_per_Val = 0;
                                double vas_Val = 0;
                                if (timeStampCalculationArrayList.size() > 0) {
                                    timestamp_per_Val = (totalFees * Integer.parseInt(timeStampCalculationArrayList.get(0).getPercentage_value()) / 100);
                                    timestamp_per_Val = timestamp_per_Val + totalFees;
                                }

                                Date createDate = new Date();
                                c.setTime(createDate);

                                if (VASPrice_list != null) {
                                    if (VASPrice_list.size() > 0) {
                                        vas_Val = totalMinutes * Double.parseDouble(VASPrice_list.get(0).getPrice());
                                        String tatValue = String.valueOf(VASPrice_list.get(0).getTat());

                                        String[] split = tatValue.split("\\.");


                                        int days = 0;
                                        try {
                                            days = Integer.parseInt(split[0]);
                                        } catch (Exception e) {
                                            GlobalData.printError(e);


                                            try {
                                                days = Integer.parseInt(tatValue);

                                            } catch (Exception e1) {
                                                GlobalData.printError(e1);
                                            }
                                        }

                                        c.add(Calendar.DATE, days);


                                        int hour = 0;
                                        try {
                                            hour = Integer.parseInt(split[1]);
                                        } catch (Exception e) {
                                            GlobalData.printError(e);
                                        }
                                        c.add(Calendar.HOUR, hour);
                                    }
                                }
                                totalFees_new = totalFees + timestamp_per_Val + vas_Val;
                                // String fromDate = df.format(parse(c.getTime().toString()));
                                String fromDate = c.getTime().toString();

                                String tatValue = String.valueOf(price_obj.getTat());

                                String[] split = tatValue.split("\\.");


                                int days_T = 0;
                                try {
                                    days_T = Integer.parseInt(split[0]);
                                } catch (Exception e) {
                                    GlobalData.printError(e);


                                    try {
                                        days_T = Integer.parseInt(tatValue);

                                    } catch (Exception e1) {
                                        GlobalData.printError(e1);
                                    }
                                }

                                c.add(Calendar.DATE, days_T);

                                if (split.length > 1) {
                                    int hour_T = 0;
                                    try {
                                        hour_T = Integer.parseInt(split[1]);
                                    } catch (Exception e) {
                                        GlobalData.printError(e);
                                    }
                                    c.add(Calendar.HOUR, hour_T);
                                }

                                //      String toDate = df.format(parse(c.getTime().toString()));
                                String toDate = c.getTime().toString();


                                for (int k = 0; k < holidays_list.size(); k++) {
                                    holidays_obj = holidays_list.get(k);
                                    //  String currentDate = df.format(parse(holidays_obj.getHolidayDate()));
                                    String currentDate = GlobalData.convertholidayDate(holidays_obj.getHoliday_date());
                                    String fDate = GlobalData.convertDates(fromDate);
                                    String tDate = GlobalData.convertDates(toDate);
                                    try {
                                        if (GlobalData.checkdates(currentDate, fDate, tDate)) {
                                            c.add(Calendar.DATE, 1);
                                            //   toDate = df.format(parse(c.getTime().toString()));
                                            toDate = c.getTime().toString();


                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                int dj = 0;
                                String newStr = GlobalData.convert24Time(toDate);
                                for (; dj < deliverySlot_list.size(); dj++) {
                                    deliverySlot_obj = deliverySlot_list.get(dj);
                                    try {
                                        if (GlobalData.timesCheck(newStr, deliverySlot_obj.getSlot_from(), deliverySlot_obj.getSlot_to())) {
                                            break;
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                String[] timesplit;
                                String time = "";


                                // Note To Date

                                if (dj < deliverySlot_list.size()) {
                                    timesplit = deliverySlot_list.get(dj).getSlot_to().split("\\.");
                                    time = deliverySlot_list.get(dj).getSlot_to();
                                } else {
                                    timesplit = deliverySlot_list.get(0).getSlot_to().split("\\.");
                                    time = deliverySlot_list.get(0).getSlot_to();
                                }

                                Calendar toTime = c;

                                int hour = 0;
                                try {
                                    hour = Integer.parseInt(timesplit[0]);
                                } catch (Exception e) {
                                    GlobalData.printError(e);


                                    try {
                                        hour = Integer.parseInt(time);

                                    } catch (Exception e1) {
                                        GlobalData.printError(e1);
                                    }
                                }
                                toTime.set(Calendar.HOUR_OF_DAY, hour);

                                int MINUTE = 0;
                                try {
                                    MINUTE = Integer.parseInt(timesplit[1]);
                                } catch (Exception e) {
                                    GlobalData.printError(e);
                                }
                                toTime.set(Calendar.MINUTE, MINUTE);


                                hour = 0;
                                MINUTE = 0;


                                dj = dj + 1;


                                if (dj < deliverySlot_list.size()) {
                                    timesplit = deliverySlot_list.get(dj).getSlot_from().split("\\.");
                                    time = deliverySlot_list.get(dj).getSlot_from();
                                } else {
                                    timesplit = deliverySlot_list.get(0).getSlot_from().split("\\.");
                                    time = deliverySlot_list.get(0).getSlot_from();
                                }


                                hour = 0;
                                try {
                                    hour = Integer.parseInt(timesplit[0]);
                                } catch (Exception e) {
                                    GlobalData.printError(e);


                                    try {
                                        hour = Integer.parseInt(time);

                                    } catch (Exception e1) {
                                        GlobalData.printError(e1);
                                    }
                                }
                                c.set(Calendar.HOUR_OF_DAY, hour);

                                MINUTE = 0;
                                try {
                                    MINUTE = Integer.parseInt(timesplit[1]);
                                } catch (Exception e) {
                                    GlobalData.printError(e);
                                }
                                c.set(Calendar.MINUTE, MINUTE);

                                //  toDate = df.format(parse(c.getTime().toString()));

                                toDate = toTime.getTime().toString();


                                changePrice.deliveryTime = c;
                                changePrice.setDate_time(c.getTime().toString());

                                changePrice.setDate_to(toDate);


                                currnetTime.setTimeInMillis(c.getTimeInMillis());
                                totalFees_new = (double) Math.ceil(totalFees_new);
                                // totalFees = (double) Math.round(totalFees);
                                changePrice.setPrice("" + String.format("%.0f", totalFees_new));
                                GlobalData.change_price_arraylist.add(changePrice);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (int k = 0; k < GlobalData.change_price_arraylist.size(); k++) {
                    if (GlobalData.change_price_arraylist.get(k).getId().equals(deliveryOptionId)) {

                        try {

                            txt_total_fee.setText(GlobalData.change_price_arraylist.get(k).getPrice());
                            String showdate = GlobalData.showdate(GlobalData.change_price_arraylist.get(k).getDate_time());
                            txt_delivery_date_time.setText(showdate);
                            currnetTime.setTimeInMillis(GlobalData.change_price_arraylist.get(k).getDeliveryTime().getTimeInMillis());
                            new_todate = GlobalData.change_price_arraylist.get(k).getDate_to();

                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
                    }
                }


            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }


    public void setData() {

        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();

            // Add Change Price Data
            GlobalData.change_price_arraylist = new ArrayList<>();
            LinkedList<Delivery_option> deloptList = db.getDelivery_option();

            for (int i = 0; i <= deloptList.size(); i++) {
                try {
                    ChangePrice bean = new ChangePrice();
//(int totalDurationinMin,String  delivery_opt_id,String service_type_id,String vas_id,String timestamp_id){
                    TAT_Calculation tat_calculation = TATCalculationGLobal.getTotalFeesAndDuration(context, totalMinutes, deloptList.get(i).getDelivery_opt_id(), service_type_id, valueadded_id, timestamp_duration_id);

                    bean.setId(deloptList.get(i).getDelivery_opt_id());
                    bean.setScheme_name(deloptList.get(i).getDel_option());

                    String showdate = GlobalData.showdate(tat_calculation.getDeliveryDateTime().getTime().toString());

                    bean.setDate_time("" + showdate);

                    bean.setDate_to("" + tat_calculation.curDelivery_slot.getSlot_to());
                    bean.setPrice("" + tat_calculation.totalFee);
                    bean.deliveryTime = tat_calculation.getDeliveryDateTime();

                    bean.setTat_calculation(tat_calculation);

                    GlobalData.change_price_arraylist.add(bean);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }


            try {





                TAT_Calculation tat_calculation = TATCalculationGLobal.getTotalFeesAndDuration(context, totalMinutes, deliveryOptionId, service_type_id, valueadded_id, timestamp_duration_id);


                setSelectedTAT(tat_calculation);

                setplan();

            } catch (Exception e) {
                GlobalData.printError(e);
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            db.close();
        }

    }


    public void setSelectedTAT(TAT_Calculation tat_calculation) {

        try {

            selectedTAT_calculation = tat_calculation;
            if(tat_calculation.totalFee>0) {


                txt_total_fee.setText(context.getResources().getString(R.string.currency)+" " + Math.round(tat_calculation.totalFee));
            }




            String showdate = GlobalData.showdate(tat_calculation.getDeliveryDateTime().getTime().toString());

            txt_delivery_date_time.setText(showdate);
            currnetTime.setTimeInMillis(tat_calculation.getDeliveryDateTime().getTimeInMillis());
            new_todate = tat_calculation.curDelivery_slot.getSlot_to();



        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public void loadInitDBData() {

        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();
            // loadDeliveryOption();
            // loadDelivereySlots();
//            loadGetTimeStampCalculation();
            // loadVASPrice();
            //     loadHolidays();


            //Load data from information
            try {
                deliveryOption = new ArrayList<Delivery_option>(db.getDelivery_option());
                ;
            } catch (Exception E) {
                GlobalData.printError(E);
            }


            try {
                deliverySlot_list = new ArrayList<Delivery_slot>(db.getDelivery_slot());
            } catch (Exception E) {
                GlobalData.printError(E);
            }

            try {
                if (deliveryOptionId.length() <= 0) {
                    deliveryOptionId = "ECO";
                }

                timeStampCalculationArrayList = new ArrayList<Timestamps_cal>(db.getTimeStampCalculation(service_type_id, deliveryOptionId, timestamp_duration_id));
            } catch (Exception E) {
                GlobalData.printError(E);
            }


            try {
                vasPriceArrayList = new ArrayList<VAS_Rate>(db.getVASPrice(timeSlabId, valueadded_id));
            } catch (Exception E) {
                GlobalData.printError(E);
            }

            try {
                holidays_list = new ArrayList<Holidays>(db.getHolidays());
            } catch (Exception E) {
                GlobalData.printError(E);
            }


            setData();


        } catch (Exception E) {
            GlobalData.printError(E);
        } finally {
            db.close();
        }

    }


}
