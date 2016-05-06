package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.bean.Feedback;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by AriaTech on 4/12/2016.
 */
public class FeedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Context context;
    private Toolbar toolbar;
    private boolean isLoggedIn;
    private TextView txt_header_login;
    RadioGroup rg_feedback;
    RadioButton rb_order_fdbk, rb_app_fdbk;
    Button btn_back, btn_send;

    TextView txt_note;
    Spinner spn_list, spn_files;
    private ProgressBar progress_bar_new;
    public GetOrderListWebServiceCall ws_obj = null;
    TextView textViewThanksMsg;

    String assignment_no = "", msg = "", feedback_type = "1", file_name = "";
    EditText edt_msg;

    int type_flag = 0;

    int orderId = 0;
    int pos = 0;

    ArrayList<String> list = new ArrayList<>();
    LinkedList<OrderDetails> orderDetailsLinkedList = new LinkedList<>();
    ArrayList<String> files = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(FeedbackActivity.this);
        setContentView(R.layout.activity_feedback);
        context = this;
        toolbar= GlobalData.initToolBarMenu(this, true, true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textViewThanksMsg = (TextView) findViewById(R.id.textViewThanksMsg);

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
//        TextView   txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//        TextView  txt_email = (TextView) headerView.findViewById(R.id.txt_email);
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
//
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
    }


    public void init() {

        try {

                Intent iin = getIntent();
            type_flag = iin.getIntExtra("OrderFeedback", 0);


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        try {

            Map<String, String> networkDetails = getConnectionDetails();
            if (networkDetails.isEmpty()) {
                GlobalData.showSnackBar(btn_back, getResources().getString(R.string.ERR_CONNECTION), false);
            } else {
                ws_obj = new GetOrderListWebServiceCall();
               // ws_obj.execute();
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }


        edt_msg = (EditText) findViewById(R.id.edt_msg);

        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);


        btn_back = (Button) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String msg="";
                try {
                    msg = edt_msg.getText().toString();
                    Map<String, String> networkDetails = getConnectionDetails();
                    if (networkDetails.isEmpty()) {
                        GlobalData.showSnackBar(btn_back, getResources().getString(R.string.ERR_CONNECTION), true);
                    }else if(msg.length()<=0) {

                        GlobalData.showSnackBar(btn_back, getResources().getString(R.string.err_feedback), true);
                    } else
                     {



                        new FeedbackWeberviceCall().execute();

                    }

                } catch (Exception e) {

                    GlobalData.printError(e);
                }

            }
        });

        txt_note = (TextView) findViewById(R.id.txt_note);
        spn_list = (Spinner) findViewById(R.id.spn_list);
        spn_files = (Spinner) findViewById(R.id.spn_files);


        rg_feedback = (RadioGroup) findViewById(R.id.rg_feedback);
        rb_order_fdbk = (RadioButton) findViewById(R.id.rb_order_fdbk);
        rb_app_fdbk = (RadioButton) findViewById(R.id.rb_app_fdbk);

        spn_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                files = new ArrayList<String>();
                try {

                    OrderDetails orderDetails = orderDetailsLinkedList.get(position);

                    if (orderDetails.getFile_meta_jsonsList().size() > 0) {

                        for (int i = 0; i < orderDetails.getFile_meta_jsonsList().size(); i++) {

                            try {

                                files.add(orderDetails.getFile_meta_jsonsList().get(i).getFile_name());

                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }
                        }

                    }


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

                if (files.size() > 0) {

                    spn_files.setVisibility(View.VISIBLE);
                } else {

                    spn_files.setVisibility(View.GONE);
                }

                spn_files.setAdapter(new ArrayAdapter<String>(FeedbackActivity.this, android.R.layout.simple_spinner_dropdown_item,
                        files));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (type_flag == 1) {
            setAppFeedBackAction(false);
        } else {
            setAppFeedBackAction(true);
        }

        int val= GlobalData.getFeedbackKey(context);


        rb_order_fdbk.setChecked(true);
        rb_app_fdbk.setChecked(false);


        if(val==1){
            rb_order_fdbk.setChecked(true);
            rb_app_fdbk.setChecked(false);


        }else {
            rb_app_fdbk.setChecked(true);
            rb_order_fdbk.setChecked(false);

        }


        rg_feedback.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_order_fdbk:

                        setAppFeedBackAction(false);

                        break;
                    case R.id.rb_app_fdbk:

                        setAppFeedBackAction(true);


                        break;

                    default:
                        break;
                }
            }

        });


    }


    public void setAppFeedBackAction(boolean visibleFlag){

        if(visibleFlag){
            txt_note.setVisibility(View.GONE);
            spn_list.setVisibility(View.GONE);
            spn_files.setVisibility(View.GONE);
            feedback_type = "2";
            GlobalData.setFeedbackkey(context, 2);

            edt_msg.setText(" ");
        }else {
            txt_note.setVisibility(View.GONE);
            spn_list.setVisibility(View.GONE);
            spn_files.setVisibility(View.GONE);

            feedback_type = "1";
            GlobalData.setFeedbackkey(context, 1);
            edt_msg.setText(" ");
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
                Intent intent = new Intent(FeedbackActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(FeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);

            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(FeedbackActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {


            if (isLoggedIn) {
                Intent intent = new Intent(FeedbackActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(FeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);

            }

        } else if (id == R.id.nav_settings) {


            if (isLoggedIn) {
                Intent intent = new Intent(FeedbackActivity.this, SettingsActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(FeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);

            }

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }
//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(FeedbackActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(FeedbackActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }
        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(FeedbackActivity.this, AboutusActivity.class);
            startActivity(intent);

        }  /*else if (id == R.id.nav_logout) {
            try {


                if (!GlobalData.isNetworkAvailable(context)) {
                    Toast.makeText(context,  R.string.ERR_CONNECTION, Toast.LENGTH_LONG).show();

                } else {
                    new LogoutWebServiceCall(context,getIntent()).execute();
                    Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                    startActivity(intent);

                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }*/

        /*else if (id == R.id.nav_feedback) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getfeeddback_data() {
        try {

//            assignment_no = spn_list.getSelectedItem().toString();
          //  file_name = spn_files.getSelectedItem().toString();
            msg = edt_msg.getText().toString();
          //  orderId = orderDetailsLinkedList.get(pos).getOrder_id();
        //    pos = spn_list.getSelectedItemPosition();

        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    // GEt orderlist for feedbaack
    public class GetOrderListWebServiceCall extends AsyncTask<Users, WebServiceResonse, WebServiceResonse> {

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse resonse = new WebServiceResonse();

        TransparentProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pd = new TransparentProgressDialog(context);
                pd.show();

                webServiceMySQl = new WebServiceMySQl(context);
                progress_bar_new.setVisibility(View.GONE);
                btn_back.setEnabled(false);
                btn_send.setEnabled(false);

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(Users... params) {
            try {
                resonse = webServiceMySQl.GetOrderListForFeedback();


            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }


        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            ws_obj = null;

            try {

                if (pd != null) {
                    pd.dismiss();
                    pd = null;
                }

                if (res.getStatus() == 200) {

                    JSONArray array1 = new JSONArray();
                    JSONArray array2 = new JSONArray();

                    try {

                        array1 = res.getJsonObject().getJSONArray("orderlist");
                        if (array1.length() > 0) {

                            setuserdata(array1);


                        } else {
                            GlobalData.showSnackBar(btn_back, getResources().getString(R.string.data_error), true);
                        }


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                } else {

                    GlobalData.showSnackBar(btn_back, res.getMessage(), true);
                }

                progress_bar_new.setVisibility(View.GONE);
            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    }


    public void setuserdata(JSONArray array1) {


        list = new ArrayList<>();
        orderDetailsLinkedList = new LinkedList<>();

        try {

            for (int i = 0; i < array1.length(); i++) {
                try {

                    JSONObject obj = array1.getJSONObject(i);

                    OrderDetails feedback = new OrderDetails();
                    feedback.parseJSON(obj);

                    list.add(feedback.getAssignment_no());

                    orderDetailsLinkedList.add(feedback);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }


            spn_list.setAdapter(new ArrayAdapter<String>(FeedbackActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    list));


        } catch (
                Exception e
                )

        {
            GlobalData.printError(e);

        }


    }


    //send feedback
    class FeedbackWeberviceCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {

        WebServiceResonse resonse = new WebServiceResonse();

        TransparentProgressDialog pd = null;

        // Set Data
        Feedback feedback1 = new Feedback();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new TransparentProgressDialog(context);
            pd.show();
            btn_back.setEnabled(false);
            btn_send.setEnabled(false);


        }

        @Override
        protected WebServiceResonse doInBackground(String... params) {

            try {

                getfeeddback_data();

                feedback1.setFeed_txt(msg);


                if (feedback_type.equals("1")) {
                    feedback1.setFeedback_type(feedback_type);
                    feedback1.setUser_id(GlobalData.userSelected.getUser_id());
                    feedback1.setAssignment_no("");
                    feedback1.setFiles_nams("");
                    feedback1.setOrder_id(orderId);
                } else {
                    feedback1.setFeedback_type("2");
                    feedback1.setUser_id(GlobalData.userSelected.getUser_id());
                    feedback1.setAssignment_no("");
                    feedback1.setFiles_nams("");
                    feedback1.setOrder_id(0);
                }


                resonse = SendFeedback(feedback1);


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
                    btn_back.setEnabled(true);
                    btn_send.setEnabled(true);
                    progress_bar_new.setVisibility(View.GONE);


                    if(res.getStatus()==200){

                        startActivity(new Intent(context,ThankYouFeedbackActivity.class));
                        finish();


                    }else {


                        GlobalData.showSnackBar(btn_send, res.getMessage(), false);
                    }

                    clear_fields();
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }


    }

    private void clear_fields() {

        edt_msg.setText("");
        spn_files.setVisibility(View.GONE);
    }


    public WebServiceResonse SendFeedback(Feedback feedback) {


        WebServiceResonse res = new WebServiceResonse();
        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        Gson gson = new Gson();


        try {


            res = webServiceMySQl.SendFeedback(new JSONObject(gson.toJson(feedback)));

            GlobalData.showSnackBar(btn_send, res.getMessage(), false);


        } catch (Exception e) {


            GlobalData.printError(e);
        }


        return res;


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
