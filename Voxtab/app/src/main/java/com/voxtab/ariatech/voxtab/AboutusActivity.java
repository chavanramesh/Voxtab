package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.webkit.WebView;
import android.widget.LinearLayout;
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
public class AboutusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private Toolbar toolbar;
    private boolean isLoggedIn;

    private WebView webView;

    TextView txt_read_more;
    LinearLayout lin_terms, lin_con, lin_abt;

    String url_about_us="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(AboutusActivity.this);
        setContentView(R.layout.activity_aboutus);
        context = this;
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


        GlobalData.setLoginAndLogout(context,navigationView,getIntent());


        isLoggedIn = GlobalData.isLoggedIn(context);
//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);
//
//
//        TextView txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//        TextView txt_email = (TextView) headerView.findViewById(R.id.txt_email);
//
//        CircularSmartImageView img_user_photo = (CircularSmartImageView) headerView.findViewById(R.id.img_user_photo);
//
//        TextView txt_mem_id = (TextView) headerView.findViewById(R.id.txt_mem_id);
//        if (isLoggedIn) {
//            navigationView.inflateMenu(R.menu.activity_main_drawer);
//            Users memberId = GlobalData.getMemberId(context);
//            String photo = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + memberId.getPhoto());
//
//            txt_header_login.setText("Logout");
//            txt_email.setText(memberId.getEmail());
//            txt_mem_id.setText(memberId.getMembership_id());
//
//            img_user_photo.setImageUrl(photo, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);
//
//
//        } else
//        {
//            navigationView.inflateMenu(R.menu.activity_menu_drawer_login);
//            txt_header_login.setText("Login");
//            txt_email.setVisibility(View.GONE);
//            txt_mem_id.setVisibility(View.GONE);
//
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
//
//
//        txt_header_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLoggedIn) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.putExtra("Name", context.getClass().getName());
//                    startActivity(intent);
//                } else {
//
//                    try {
//
//                        if (!GlobalData.isNetworkAvailable(context)) {
//                            Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();
//
//                        } else {
//                            new LogoutWebServiceCall(context, getIntent()).execute();
//
//                            Intent intent = new Intent(AboutusActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//
//
//                        }
//
//
//                    } catch (Exception e) {
//                        GlobalData.printError(e);
//                    }
//
//                }
//            }
//        });


        init();


    }


    public void init() {


        //txt_read_more = (TextView) findViewById(R.id.txt_read_more);
        lin_terms = (LinearLayout) findViewById(R.id.lin_terms);
        lin_abt = (LinearLayout) findViewById(R.id.lin_abt);
        lin_con = (LinearLayout) findViewById(R.id.lin_con);


        lin_abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(AboutusActivity.this, AboutusViewActivity.class);
//                startActivity(intent);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalData.ABOUT_US_URL));
                startActivity(browserIntent);
            }
        });


        lin_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalData.TERMS_URL));
                startActivity(browserIntent);
            }
        });

        lin_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalData.CONFI_URL));
                startActivity(browserIntent);
            }
        });

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
                Intent intent = new Intent(AboutusActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutusActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(AboutusActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(AboutusActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutusActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(AboutusActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutusActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(AboutusActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }
//        else if (id == R.id.nav_confidentiality) {
//
//            Intent intent = new Intent(AboutusActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(AboutusActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        } /*else if (id == R.id.nav_about_us) {*/

      /*  else if (id == R.id.nav_logout) {
            try {


                if (!GlobalData.isNetworkAvailable(context)) {
                    Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();

                } else {
                    new LogoutWebServiceCall(context, getIntent()).execute();

                    Intent intent = new Intent(AboutusActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();


                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }

        }
        */

        else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(AboutusActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutusActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
