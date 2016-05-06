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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

/**
 * Created by Local User on 16-Feb-16.
 */
public class ThankYouFeedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView txt_goto_order_history;
    ImageView imageView2;

    private Context context;
    private Toolbar toolbar;


    String assignment_no = "";

    TextView textViewThanksMsg;
    private boolean isLoggedIn;
    private TextView txt_header_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_feedback);
        GlobalData.activities.add(ThankYouFeedbackActivity.this);
        context = this;

        toolbar= GlobalData.initToolBarMenu(this, true, true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textViewThanksMsg = (TextView) findViewById(R.id.textViewThanksMsg);

        try {


            assignment_no = getIntent().getStringExtra("assignment_no");
        } catch (Exception e) {
            GlobalData.printError(e);
        }

        if (assignment_no.length() > 0) {

            try {

                String message = getString(R.string.thank_note);

                message = message.replace("ASS_NO", "" + assignment_no);

             //   textViewThanksMsg.setText(message);

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }


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
//                    intent.putExtra("Name", context.getClass().getName());
//                    startActivity(intent);
//                }
//            }
//        });
        init();

    }

    private void init() {

        txt_goto_order_history = (TextView) findViewById(R.id.txt_goto_order_history);
        txt_goto_order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_order_history();
            }
        });

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*

                Intent intent = new Intent(ThankYouActivity.this, RevisedOrderActivity.class);
                startActivity(intent);
*/

            }
        });

    }

    private void goto_order_history() {

        try {

            Intent intent = new Intent(ThankYouFeedbackActivity.this, HomeActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            GlobalData.printError(e);
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
                Intent intent = new Intent(ThankYouFeedbackActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);

            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(ThankYouFeedbackActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);

            }

        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);

            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(ThankYouFeedbackActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(ThankYouFeedbackActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(ThankYouFeedbackActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(ThankYouFeedbackActivity.this, AboutusActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_logout) {
            try {


                if (!GlobalData.isNetworkAvailable(context)) {
                    Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();

                } else {
                    new LogoutWebServiceCall(context,getIntent()).execute();
                    Intent intent = new Intent(ThankYouFeedbackActivity.this, HomeActivity.class);
                    startActivity(intent);

                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        } */else if (id == R.id.nav_feedback) {


            if (isLoggedIn) {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ThankYouFeedbackActivity.this, LoginActivity.class);
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
            Intent in = new Intent(ThankYouFeedbackActivity.this,
                    MyRecordingsActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }

    }
}
