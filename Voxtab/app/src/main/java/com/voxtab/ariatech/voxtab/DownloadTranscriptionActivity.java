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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.voxtab.ariatech.voxtab.adapter.DownloadTranscription_list_adapter;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import java.util.LinkedList;

/**
 * Created by Local User on 01-Feb-16.
 */
public class DownloadTranscriptionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DownloadTranscription_list_adapter downloadTranscription_list_adapter;
    public static ListView lst_recordings;
    private Toolbar toolbar;
    private Context context;
    Button btn_back, btn_feedback;
    int order_id = 0;

    String assignment_no = "";
    LinkedList<File_Meta_JSON> Files = new LinkedList<>();
    TextView textViewAssignMentNo;

    ImageView imageViewLoader;


    OrderDetails orderDetails = new OrderDetails();
    TextView textViewMsg;
    Button buttonBack;
    Button buttonFeedback;
    private boolean isLoggedIn;
    private TextView txt_header_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_downloadtranscription);


        GlobalData.activities.add(DownloadTranscriptionActivity.this);
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

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());
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

        // Get IntentData
        try {
            Intent iin = getIntent();


            try {
                order_id = iin.getIntExtra("order_id", 0);
            } catch (Exception e) {
                GlobalData.printError(e);
            }

            assignment_no = iin.getStringExtra("assignment_no");

            orderDetails = GlobalData.selectedOrder;

        } catch (Exception e) {
            GlobalData.printError(e);
        }


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
                Intent intent = new Intent(DownloadTranscriptionActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);

            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(DownloadTranscriptionActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DownloadTranscriptionActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);

            }
        } else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);

            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(DownloadTranscriptionActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }
//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(DownloadTranscriptionActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(DownloadTranscriptionActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }
        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(DownloadTranscriptionActivity.this, AboutusActivity.class);
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
//                    Intent intent = new Intent(DownloadTranscriptionActivity.this, HomeActivity.class);
//                    startActivity(intent);
//
//                }
//
//
//            } catch (Exception e) {
//                GlobalData.printError(e);
//            }
//
//        }
        else if (id == R.id.nav_feedback) {


            if (isLoggedIn) {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(DownloadTranscriptionActivity.this, LoginActivity.class);
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


            try {


                buttonBack = (Button) findViewById(R.id.btn_back);
                buttonFeedback = (Button) findViewById(R.id.btn_feedback);

                textViewAssignMentNo = (TextView) findViewById(R.id.textViewAssNo);
                textViewMsg = (TextView) findViewById(R.id.txt_progress_note);
                imageViewLoader = (ImageView) findViewById(R.id.img_thanku);

                lst_recordings = (ListView) findViewById(R.id.lst_files);
//                lst_recordings.setLongClickable(true);
                lst_recordings.setDivider(null);


                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();

                    }
                });

                buttonFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in = new Intent(DownloadTranscriptionActivity.this,
                                FeedbackActivity.class);
                        in.putExtra("OrderFeedback",1);
                        startActivity(in);
                    }
                });




            } catch (Exception e) {
                GlobalData.printError(e);
            }

            textViewAssignMentNo.setText("");
            textViewMsg.setText("");

            imageViewLoader.setVisibility(View.GONE);


            Files = new LinkedList<>();

            try {

                for (int i = 0; i < orderDetails.getFile_meta_jsonsList().size(); i++) {
                    try {

                        if (orderDetails.getFile_meta_jsonsList().get(i).getTrans_file_name().length() > 0) {
                            Files.add(orderDetails.getFile_meta_jsonsList().get(i));
                        }

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            }


            // Set data

            if (assignment_no.length() > 0) {
                textViewAssignMentNo.setText("" + assignment_no);
            }

            int totalFiles = 0;

            try {
                totalFiles = Integer.parseInt(orderDetails.getTotal_files());
            } catch (Exception e) {
                GlobalData.printError(e);
            }


            imageViewLoader.setVisibility(View.GONE);

            String message = "";

            if (Files.size() == totalFiles) {
                imageViewLoader.setVisibility(View.VISIBLE);
                message = getResources().getString(R.string.order_comp);
            } else {
                message = getResources().getString(R.string.progress_note);


                message = message.replace("count", Files.size() + "/" + orderDetails.getTotal_files());


                message = message + "" + GlobalData.convertShortDate(orderDetails.getDelivery_date());
            }

            textViewMsg.setText(message);


            try {

                if (Files.size() > 0) {
                    downloadTranscription_list_adapter = new DownloadTranscription_list_adapter(context, Files);
                    lst_recordings.setAdapter(downloadTranscription_list_adapter);
                    lst_recordings.setOnTouchListener(new View.OnTouchListener() {
                        // Setting on Touch Listener for handling the touch inside ScrollView
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // Disallow the touch request for parent scroll on touch of child view
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            return false;
                        }
                    });
                }
            } catch (Exception e) {
                GlobalData.printError(e);
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }

    }


    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent in = new Intent(DownloadTranscriptionActivity.this,
                    OrderHistoryActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }

    }


}