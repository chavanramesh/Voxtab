package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.bean.Company_info;
import com.voxtab.ariatech.voxtab.bean.Company_location;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Local User on 03-Feb-16.
 */
public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    Button sign_in_button;
    LinkedList<MyRecording> selectedFiles;

    private ProgressBar progress_bar_new;
    String email = "";
    String password = "";

    EditText editTextUserName;
    EditText editTextPassword;


    CheckBox checkBoxRememberMe;
    TextView textViewForgotPassword;
    private boolean isLoggedIn;
    private TextView txt_header_login;

    public ForgotPasswordWebServiceCall ws_obj = null;
    private String ActivityName = "";
    SharedPreferencesUtility sharedPreferencesUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GlobalData.activities.add(LoginActivity.this);
        context = LoginActivity.this;
        toolbar= GlobalData.initToolBarMenu(this, true, true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sharedPreferencesUtility=new SharedPreferencesUtility(context);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        isLoggedIn = GlobalData.isLoggedIn(context);


        GlobalData.setLoginAndLogout(context,navigationView,getIntent());
//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);
//
//        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
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
//
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


        init();



    }


    public void init() {

        ActivityName = getIntent().getStringExtra("Name");
/*
        ArrayList<MyRecording> items = (ArrayList<MyRecording>)
                getIntent().getSerializableExtra("selected_files");

        selectedFiles = new LinkedList<MyRecording>();
        selectedFiles.addAll(items);
        System.out.println(" List Size " + items.size());
       */
        editTextUserName = (EditText) findViewById(R.id.mobile);
        editTextPassword = (EditText) findViewById(R.id.password);

        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);

        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkboxRemeberMe);
        textViewForgotPassword = (TextView) findViewById(R.id.txt_forgot_password);


        // editTextUserName.setText();
        //editTextPassword.setText();

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Map<String, String> networkDetails = getConnectionDetails();
                    if (networkDetails.isEmpty()) {
                        GlobalData.showSnackBar(sign_in_button, getResources().getString(R.string.ERR_CONNECTION), false);
                    }
                    else {


                        if(editTextUserName.getText().length()>0) {

                            ws_obj = new ForgotPasswordWebServiceCall();
                            ws_obj.execute();
                        }else {
                            GlobalData.showSnackBar(sign_in_button, getResources().getString(R.string.err_username), false);
                        }
                    }


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        });

        sign_in_button = (Button) findViewById(R.id.sign_in_button);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


//                    login();
                    email = editTextUserName.getText().toString();
                    password = editTextPassword.getText().toString();


                    if (!GlobalData.isNetworkAvailable(context)) {
                        GlobalData.showSnackBar(sign_in_button, getString(R.string.no_connection), true);

                        return;
                    }

                    if (email.length() > 0 && password.length() > 0) {

                        new LoginCall().execute("");
                    } else {

                        GlobalData.showSnackBar(sign_in_button, getString(R.string.validation_login), true);
                    }


                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        });


        clearData();

    }

    public void clearData() {
        editTextUserName.setText("");
        editTextPassword.setText("");

    }

    public void login() {
        try {
            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
            if(ActivityName.length()>0) {
                 intent =
                        new Intent(LoginActivity.this, Class.forName(ActivityName));

            }

            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
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
                Intent intent = new Intent(LoginActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(LoginActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(LoginActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(LoginActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(LoginActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(LoginActivity.this, AboutusActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_logout) {
            try {


                if (!GlobalData.isNetworkAvailable(context)) {
                    Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();

                } else {
                    new LogoutWebServiceCall(context,getIntent()).execute();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }




        }*/ else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(LoginActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
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
//            Intent in = new Intent(LoginActivity.this,
//                    HomeActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(in);
            finish();
        }

    }


    // Login Call

    class LoginCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {

        WebServiceResonse resonse = new WebServiceResonse();
        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);

        TransparentProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new TransparentProgressDialog(context);
            pd.show();

            progress_bar_new.setVisibility(View.GONE);
            sign_in_button.setEnabled(false);

        }

        @Override
        protected WebServiceResonse doInBackground(String... params) {


            try {

                resonse = webServiceMySQl.Login(email, password);


                if (resonse.getStatus() == 200) {

                    String membership_id = "";
                    int userId = 0;
                    try {

                        sharedPreferencesUtility.setString(GlobalData.userKey,resonse.getJsonObject().toString());

                        userId = Integer.parseInt(resonse.getJsonObject().getString("user_id"));
                        membership_id = resonse.getJsonObject().getString("membership_id");


                        GlobalData.showSnackBar(sign_in_button, resonse.getMessage(), false);

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                    // Get GCM Key

                    String gcmKey = "";
                    SharedPreferences settings = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    try {
                        //


                        gcmKey = settings.getString("regID", "");


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                    if (userId > 0) {
                        WebServiceResonse updateKey = webServiceMySQl.UpdateGcmKey(gcmKey, userId);

                        if (updateKey.getStatus() == 200) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("SETGCMKEY", 1);
                            editor.commit();
                        }
                    }
                }



            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }

        @Override
        protected void onPostExecute(WebServiceResonse resonse) {
            super.onPostExecute(resonse);

            try {

                if (pd != null) {
                    pd.dismiss();
                    pd = null;
                }

                sign_in_button.setEnabled(true);

                if (resonse.getStatus() == 200) {

                    // Parse And Save Data


                    // Parse Data
                    // User  company Info  companyLocation

                    Users users = new Users();

                    int userId = 0;
                    String membership_id = "";
                    try {
                        userId = Integer.parseInt(resonse.getJsonObject().getString("user_id"));
                        membership_id = resonse.getJsonObject().getString("membership_id");
                        users.parseJSON(resonse.getJsonObject().getJSONArray("user_details").getJSONObject(0));


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    users.setUser_id(userId);
                    users.setMembership_id(membership_id);


                    // add Company Info
                    Company_info company_info = new Company_info();
                    Company_location loc = new Company_location();
                    try {

                        company_info.parseJSON(resonse.getJsonObject().getJSONArray("company_info").getJSONObject(0));


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                    try {

                        loc.parseJSON(resonse.getJsonObject().getJSONArray("company_location").getJSONObject(0));


                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                    users.setCompany_info(company_info);



                    if (userId > 0) {

                        // Set Rem Me Flag

                        try {
                            if(!checkBoxRememberMe.isChecked()){
                             GlobalData.setRemMeLoginkey(context, true);
                            }else{

                                GlobalData.setRemMeLoginkey(context, false);
                            }
                        }catch (Exception e){
                            GlobalData.printError(e);
                        }


                        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                        try {
                            db.open();

                            // Delete Data
                            db.deleteUsers();
                            db.deleteCompany_info();
                            db.deleteCompany_location();

                            db.addUsers(users);

                            db.addCompany_info(company_info);
                            db.addCompany_location(loc);
                            SharedPreferences settings = PreferenceManager
                                    .getDefaultSharedPreferences(context);
                            SharedPreferences.Editor prefsEditor = settings.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(users); // myObject - instance of MyObject
                            prefsEditor.putString("users", json);
                            prefsEditor.commit();

                            GlobalData.setUserData(context);

                            login();


                        } catch (Exception e) {
                            GlobalData.printError(e);
                        } finally {
                            db.close();
                        }


                    }


                } else {


                }

                if(resonse.getMessage().length()>0) {
                    GlobalData.showSnackBar(sign_in_button, resonse.getMessage(), false);

                }

                progress_bar_new.setVisibility(View.GONE);

            } catch (Exception e) {
                GlobalData.printError(e);
            }

        }
    }


    // Forgot Password
    public class ForgotPasswordWebServiceCall extends AsyncTask<Users, WebServiceResonse, WebServiceResonse> {

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
                sign_in_button.setEnabled(false);
                textViewForgotPassword.setEnabled(false);

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(Users... params) {
            try {
                resonse = webServiceMySQl.ForgotPassword();


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


                    GlobalData.showSnackBar(sign_in_button, res.getMessage(), true);

                } else {
                    GlobalData.showSnackBar(sign_in_button, getResources().getString(R.string.data_error), true);
                }


                progress_bar_new.setVisibility(View.GONE);

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
