package com.voxtab.ariatech.voxtab;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.adapter.ExpandableAdapter;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.OrderHistoryChild;
import com.voxtab.ariatech.voxtab.bean.OrderHistoryGroup;
import com.voxtab.ariatech.voxtab.bean.Order_Details_Bean;
import com.voxtab.ariatech.voxtab.bean.Status_type;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
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
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<OrderHistoryGroup> catList;
    private List<List<OrderHistoryGroup>> listparts;
    private LinearLayout page_numbers;
    private Toolbar toolbar;
    private Context context;
    private ProgressBar progress_bar_new;

    private boolean isLoggedIn;
    private TextView txt_header_login, txt_no_order;
    RelativeLayout lin_parent;

    private GetOrderListWeberviceCall ws_obj = null;
    TextView lbl_view_details, lbl_download_transcription, btn_prev, btn_next;
    ExpandableListView exList;
    HashMap<String, Integer> hashMap = new HashMap<>();
    int selectedIndex = 0;
    Button btn_back;
    static int order_count = 0, offset = 0, limit = 4;
    int txt_count;
//    TextView txt_num1, txt_num2, txt_num3, txt_num4, txt_num5;

     int  selected= 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        context = this;
        GlobalData.activities.add(OrderHistoryActivity.this);

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
        isLoggedIn = GlobalData.isLoggedIn(context);
        GlobalData.setLoginAndLogout(context,navigationView,getIntent());

//        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);
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
        initData();


    }

    private void initData() {

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                back();
            }
        });
        lbl_view_details = (TextView) findViewById(R.id.lbl_view_details);
        lbl_download_transcription = (TextView) findViewById(R.id.lbl_download_transcription);
        btn_prev = (TextView) findViewById(R.id.btn_prev);
        btn_next = (TextView) findViewById(R.id.btn_next);
        page_numbers = (LinearLayout) findViewById(R.id.page_numbers);
        exList = (ExpandableListView) findViewById(R.id.lst_orders);
        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);

        txt_no_order = (TextView) findViewById(R.id.txt_no_orders);
        lin_parent=(RelativeLayout)findViewById(R.id.lin_parent);

        try {
            ws_obj = new GetOrderListWeberviceCall(offset, limit);
            ws_obj.execute();

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }

    private <T> List<List<T>> chooped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
        }
        return parts;
    }

    private void prev_order() {
        if (selectedIndex > 0) {

            selectedIndex = selectedIndex - 1;
            offset = 4 * selectedIndex;
            try {

                ws_obj = new GetOrderListWeberviceCall(offset, limit);
                ws_obj.execute();

            } catch (Exception e) {
                GlobalData.printError(e);
            }


        }
    }

    private void back() {

        Intent intent = new Intent(OrderHistoryActivity.this, MyRecordingsActivity.class);
        startActivity(intent);
    }

    private void next_order() {
        if (selectedIndex < txt_count) {


            selectedIndex = selectedIndex + 1;
            offset = 4 * selectedIndex;

            try {
                progress_bar_new.setVisibility(View.VISIBLE);
                ws_obj = new GetOrderListWeberviceCall(offset, limit);
                ws_obj.execute();

            } catch (Exception e) {
                GlobalData.printError(e);
            }
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
                Intent intent = new Intent(OrderHistoryActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderHistoryActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);

            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(OrderHistoryActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        }/*else if (id == R.id.nav_order_history) {


        }*/ else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(OrderHistoryActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderHistoryActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);

            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(OrderHistoryActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(OrderHistoryActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(OrderHistoryActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(OrderHistoryActivity.this, AboutusActivity.class);
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
                Intent intent = new Intent(OrderHistoryActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(OrderHistoryActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);

            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private OrderHistoryGroup createCategory(String assignment_num, String status_title, String order_status_icon, long id, int order_id,
                                             String orderConfirmDate,
                                             String orderDeliveryDate, String orderPlacedDate, OrderDetails orderDetails) {


//        public OrderHistoryGroup(long id, String assignment_num, String status_title, String order_status_icon, int order_id,
//        String orderConfirmDate,
//        String orderDeliveryDate)
        return new OrderHistoryGroup(id, assignment_num, status_title, order_status_icon, order_id, orderConfirmDate, orderDeliveryDate, orderPlacedDate, orderDetails);
    }


    private List<OrderHistoryChild> createItems(String txt_total_fee, String txt_delivery_date, String img_order_status, String txt_status_detail, String txt_order_date, String img_upload_status, String txt_upload_date, String img_delivered_status, String txt_delivered_status_details, String txt_delivered_date, int num, String orderConfirmDate, String orderDeliveryDate, OrderDetails orderDetails) {
        List<OrderHistoryChild> result = new ArrayList<OrderHistoryChild>();

        for (int i = 0; i < num; i++) {

            OrderHistoryChild item = new OrderHistoryChild(i, txt_total_fee, txt_delivery_date, " ", txt_status_detail, txt_order_date, " ", txt_upload_date, " ", txt_delivered_status_details, txt_delivered_date, orderConfirmDate, orderDeliveryDate, orderDetails);
//            OrderHistoryChild item = new OrderHistoryChild(i, " ", "Order Placed", "order is placed", "23Mar 2016", " ", "Uploaded", "23Mar 2016", " ", "Delivered", "order is delivered", "23Mar 2016");
            result.add(item);
        }
        return result;
    }


    // GEt Order List
    class GetOrderListWeberviceCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {


        int orderId = 0;

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse resonse = new WebServiceResonse();
        int offsetval, limitval;

        TransparentProgressDialog pd = null;


        GetOrderListWeberviceCall(int offsetval, int limitval) {
            this.offsetval = offsetval;
            this.limitval = limitval;
        }
        // Set Data


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_bar_new.setVisibility(View.GONE);


            pd = new TransparentProgressDialog(context);
            pd.show();

        }

        @Override
        protected WebServiceResonse doInBackground(String... params) {

            try {

                resonse = webServiceMySQl.GetOrderList(offsetval, limitval);


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

                    JSONArray array = new JSONArray();

                    try {

                        array = res.getJsonObject().getJSONArray("orderlist");
                        if (array.length() > 0) {
                            order_count = res.getJsonObject().getInt("order_count");
                            txt_no_order.setVisibility(View.GONE);
                            lin_parent.setVisibility(View.VISIBLE);

                        }
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    if (array.length() > 0) {
                        setOrderList(array);

                    } else {
                        GlobalData.showSnackBar(btn_back, res.getMessage(), true);

                    }


                } else {

                    GlobalData.showSnackBar(btn_back, res.getMessage(), true);
                    txt_no_order.setVisibility(View.VISIBLE);
                    lin_parent.setVisibility(View.GONE);
                }


                progress_bar_new.setVisibility(View.GONE);


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    }


    public void setOrderList(JSONArray array) {


        LinkedList<OrderDetails> list = new LinkedList<>();
        LinkedList<Order_Details_Bean> list1 = new LinkedList<>();

        try {

            for (int i = 0; i < array.length(); i++) {
                try {

                    JSONObject obj = array.getJSONObject(i);

                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.parseJSON(obj);
                    list.add(orderDetails);


                    //for orderdetails
                    Order_Details_Bean order_details_bean = new Order_Details_Bean();
                    order_details_bean.parseJSON(obj);
                    list1.add(order_details_bean);

                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }


            catList = new ArrayList<OrderHistoryGroup>();
            String status_text = "";
            for (int i = 0; i < list.size(); i++) {

                //get status title
                DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);

                Status_type status_type = new Status_type();
                try {
                    databaseHandler.open();
                    status_type = databaseHandler.getStatus_type(list.get(i).getOrder_status_id());

                    status_text = status_type.getStatus_txt();

                } catch (Exception e) {
                    GlobalData.printError(e);
                } finally {
                    databaseHandler.close();
                }
                OrderHistoryGroup cat = createCategory(list.get(i).getAssignment_no(), status_text, "", 1, list.get(i).getOrder_id(), list.get(i).getOrderConfirmDate(), list.get(i).getOrderDeliveryDate(), list.get(i).getDelivery_date(), list.get(i));
                cat.setItemList(createItems(list.get(i).getTotal_fees(), list.get(i).getDelivery_date(), " ", status_text, list.get(i).getOrder_date(), " ", list.get(i).getOrder_placed_details(), " ", list.get(i).getOrder_complete_details(), " ", 1, list.get(i).getOrderConfirmDate(), list.get(i).getOrderDeliveryDate(), list.get(i)));

                catList.add(cat);
            }
            setData();

            // Display Order List to list View As per database Entry

        } catch (Exception e) {
            GlobalData.printError(e);

        }


    }

    public void setData() {

        listparts = new ArrayList<>();
        listparts = chooped(catList, 4);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_order();

            }
        });


        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_order();

            }
        });


        page_numbers.removeAllViews();
        txt_count = order_count / 4;
        int remain = order_count % 4;
        if (remain > 0) {
            txt_count = txt_count + 1;
        }
        if (txt_count > 0 && txt_count < 1) {
            btn_next.setVisibility(View.GONE);
            btn_prev.setVisibility(View.GONE);
        }




        for (int i = 0; i < txt_count && i < 5; i++) {


            final TextView textView = new TextView(context);
            int txtNo = 1 + i;
            int paddingPixel = 8;
            float density = context.getResources().getDisplayMetrics().density;
            final int paddingDp = (int) (paddingPixel * density);
            textView.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            textView.setText(txtNo + "");

            if(i == selectedIndex){
                textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }else {
                textView.setBackgroundColor(getResources().getColor(R.color.whiteBackground));
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!view.isSelected()) {

                        TextView textView = (TextView) view;

//                        Toast.makeText(context, "text" + textView.getText(), Toast.LENGTH_LONG).show();
                        int no = Integer.parseInt(textView.getText().toString()) - 1;

                        selectedIndex = no;
                        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        if (selectedIndex == 0) {
                            btn_prev.setVisibility(View.GONE);
//                            btn_next.setVisibility(View.GONE);
                            if (selectedIndex == (txt_count - 1)) {
                                btn_next.setVisibility(View.GONE);

                            } else {
                                btn_next.setVisibility(View.VISIBLE);
                            }
                        } else {
                            btn_prev.setVisibility(View.VISIBLE);
                            if (selectedIndex == (txt_count - 1)) {
                                btn_next.setVisibility(View.GONE);
                            } else {
                                btn_next.setVisibility(View.VISIBLE);
                            }
                        }

                        offset = 4 * no;


                        try {
                            progress_bar_new.setVisibility(View.VISIBLE);
                            ws_obj = new GetOrderListWeberviceCall(offset, limit);
                            ws_obj.execute();

                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
//                        exList.setIndicatorBounds(5, 5);
//                        ExpandableAdapter exAdpt = new ExpandableAdapter(listparts.get(no), context);
//                        exList.setIndicatorBounds(0, 20);
//                        exList.setAdapter(exAdpt);

                    }
                }
            });


            page_numbers.addView(textView);
        }


        btn_prev.setVisibility(View.GONE);
        if (txt_count > 1) {
            btn_next.setVisibility(View.VISIBLE);
        } else {
            btn_next.setVisibility(View.GONE);
        }

        if (selectedIndex == 0) {
            btn_prev.setVisibility(View.GONE);
//                            btn_next.setVisibility(View.GONE);
            if (selectedIndex == (txt_count - 1)) {
                btn_next.setVisibility(View.GONE);

            } else {
                btn_next.setVisibility(View.VISIBLE);
            }
        } else {
            btn_prev.setVisibility(View.VISIBLE);
            if (selectedIndex == (txt_count - 1)) {
                btn_next.setVisibility(View.GONE);
            } else {
                btn_next.setVisibility(View.VISIBLE);
            }
        }
        exList.setIndicatorBounds(5, 5);
        ExpandableAdapter exAdpt = new ExpandableAdapter(listparts.get(0), this);
        exList.setIndicatorBounds(0, 20);
        exList.setAdapter(exAdpt);
        progress_bar_new.setVisibility(View.GONE);
    }

}
