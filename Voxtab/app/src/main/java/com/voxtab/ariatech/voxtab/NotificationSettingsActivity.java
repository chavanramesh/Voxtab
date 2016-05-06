package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

/**
 * Created by AriaTech on 4/12/2016.
 */
public class NotificationSettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Context context;
    private Toolbar toolbar;
    Button btn_back;

    //    RadioGroup rg_noti;
    RadioButton rb_sendall, rb_muteall, rb_notifydeli;
    private boolean isLoggedIn;
    private TextView txt_header_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(NotificationSettingsActivity.this);
        setContentView(R.layout.activity_notification_settings);
        context = this;
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
        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);

        GlobalData.setLoginAndLogout(context,navigationView,getIntent());
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
//
//                    intent.putExtra("Name", context.getClass().getName());
//                    startActivity(intent);
//                }
//            }
//        });
        init();
    }


    public void init() {


        rb_sendall = (RadioButton) findViewById(R.id.rb_sendall);
        rb_muteall = (RadioButton) findViewById(R.id.rb_muteall);
        rb_notifydeli = (RadioButton) findViewById(R.id.rb_notify_deli);

        rb_sendall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // Toast.makeText(getApplicationContext(), "SEND ALL", Toast.LENGTH_SHORT).show();
                  GlobalData.setNotificationkey(context, 0);
                    rb_muteall.setChecked(false);
                    rb_notifydeli.setChecked(false);
                }

            }
        });

        rb_muteall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // Toast.makeText(getApplicationContext(), "MUTE ALL", Toast.LENGTH_SHORT).show();
                    GlobalData.setNotificationkey(context, 1);
                    rb_sendall.setChecked(false);
                    rb_notifydeli.setChecked(false);
                }
            }
        });

        rb_notifydeli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // Toast.makeText(getApplicationContext(), "NOTIFY ", Toast.LENGTH_SHORT).show();
                    GlobalData.setNotificationkey(context, 2);


                    rb_sendall.setChecked(false);
                    rb_muteall.setChecked(false);
                }
            }
        });

        btn_back = (Button) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationSettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });




        // Init Data

        int val= GlobalData.getNotificationkey(context);


        rb_sendall.setChecked(false);
        rb_muteall.setChecked(false);
        rb_notifydeli.setChecked(false);


        if(val==2){
            rb_muteall.setChecked(true);
        }else  if(val ==3){
            rb_notifydeli.setChecked(true);
        }else {
            rb_sendall.setChecked(true);
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
                 Intent intent = new Intent(NotificationSettingsActivity.this, NotificationsActivity.class);
            startActivity(intent);

            } else {
                Intent intent = new Intent(NotificationSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(NotificationSettingsActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(NotificationSettingsActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NotificationSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(NotificationSettingsActivity.this, SettingsActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(NotificationSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(NotificationSettingsActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//
//            Intent intent = new Intent(NotificationSettingsActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(NotificationSettingsActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(NotificationSettingsActivity.this, AboutusActivity.class);
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
//                    new LogoutWebServiceCall(context,getIntent()).execute();
//                    Intent intent = new Intent(NotificationSettingsActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                }
//
//
//            } catch (Exception e) {
//                GlobalData.printError(e);
//            }
//        }
        else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(NotificationSettingsActivity.this, FeedbackActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(NotificationSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
