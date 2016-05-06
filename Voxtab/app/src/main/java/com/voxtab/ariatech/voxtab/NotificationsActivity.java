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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.voxtab.ariatech.voxtab.adapter.NotificationListAdapterfromActivity;
import com.voxtab.ariatech.voxtab.bean.Notification;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import java.util.LinkedList;

/**
 * Created by AriaTech on 4/12/2016.
 */
public class NotificationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    PopupWindow popupWindow;
    private Toolbar toolbar;
    private boolean isLoggedIn;
    private TextView txt_header_login, txt_email, txt_no_notifications;

    TextView title_notifications;

    ListView lst_notifications;
    RelativeLayout img_noti;
    NotificationListAdapterfromActivity adpt;

    private LinkedList<Notification> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(NotificationsActivity.this);
        setContentView(R.layout.activity_notifications);
        context = this;
        toolbar = GlobalData.initToolBarMenu(this, true, false);
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

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());
        lst_notifications = (ListView) findViewById(R.id.lst_notifications);

        isLoggedIn = GlobalData.isLoggedIn(context);
//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);
//
//        txt_header_login = (TextView) headerView.findViewById(R.id.txt_header_login);
//
//
//        txt_email = (TextView) headerView.findViewById(R.id.txt_email);
//
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


        txt_no_notifications = (TextView) findViewById(R.id.txt_no_notifications);
        title_notifications = (TextView) findViewById(R.id.title_notifications);

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
        setAdapaterData(context);
        lst_notifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int noti_id = list.get(position).getNoti_id();
                DatabaseHandlerNew db = new DatabaseHandlerNew(context);
                try {
                    db.open();
                    db.updateReadNotification(noti_id);

                    try{
                        if(list.get(position).getOrder_id()>0 && list.get(position).getAssignment_no().length()>0){

                            Intent intent = new Intent(context, OrderDetailsActivity.class);
                            intent.putExtra("order_id", list.get(position).getOrder_id());

                            System.out.print(list.get(position).getOrder_id());
                            intent.putExtra("assignment_no", list.get(position).getAssignment_no());
                            startActivity(intent);

                        }

                    }catch (Exception e){
                        GlobalData.printError(e);
                    }
                } catch (Exception e) {
                    GlobalData.printError(e);
                } finally {
                    db.close();
                }
                adpt.notifyDataSetChanged();
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

        if (id == R.id.nav_recordings) {

            if (isLoggedIn) {
                Intent intent = new Intent(NotificationsActivity.this, MyRecordingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(NotificationsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.MyRecordingsActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(NotificationsActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(NotificationsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(NotificationsActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(NotificationsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }




        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(NotificationsActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//
//            Intent intent = new Intent(NotificationsActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(NotificationsActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(NotificationsActivity.this, AboutusActivity.class);
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
//                    Intent intent = new Intent(NotificationsActivity.this, HomeActivity.class);
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
//
//        }
        else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(NotificationsActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(NotificationsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }




        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setAdapaterData(Context context) {

        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();
            list = db.getNotification(0);


            if (list.size() <= 0) {

                txt_no_notifications.setVisibility(View.VISIBLE);
                lst_notifications.setVisibility(View.GONE);
                title_notifications.setVisibility(View.GONE);
            } else {

                txt_no_notifications.setVisibility(View.GONE);
                lst_notifications.setVisibility(View.VISIBLE);
                title_notifications.setVisibility(View.VISIBLE);
                adpt = new NotificationListAdapterfromActivity(context, list);
                lst_notifications.setAdapter(adpt);

            }


        } catch (Exception e) {
            GlobalData.printError(e);
        } finally {
            db.close();
        }
    }


}
