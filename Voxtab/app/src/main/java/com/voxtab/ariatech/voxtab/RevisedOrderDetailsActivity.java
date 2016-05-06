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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.adapter.SelectedFilesAdapterOrderDetails;
import com.voxtab.ariatech.voxtab.bean.Delivery_option;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.bean.Service_type;
import com.voxtab.ariatech.voxtab.bean.TAT_Calculation;
import com.voxtab.ariatech.voxtab.bean.TimeStamb;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.bean.Vas;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.TATCalculationGLobal;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by Local User on 18-Feb-16.
 */
public class RevisedOrderDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button btn_back;
    TextView lbl_details;
    private Context context;
    private Toolbar toolbar;
    private GetOrderDetails ws_obj = null;
//    OrderDetails orderDetails = new Order_Details_Bean();
    OrderDetails orderDetails = new OrderDetails();
    int order_id;
    private ProgressBar progress_bar_new;
    String assignment_no = "";
    TextView txt_assignment_num, txt_total_duration, txt_total_fee, txt_delivery_date_time, txt_services_type, txt_delivery_option, txt_value_added_services, txt_time_stamps, txt_fs1, txt_fs2, txt_fs3, txt_fs4, txt_fs5;
    private SelectedFilesAdapterOrderDetails selectedFilesAdapter;
    LinkedList<File_Meta_JSON> selectedFiles;
    ListView lst_selected_files;

    Button btn_conf_Order;
    private boolean isLoggedIn;
    private TextView txt_header_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        GlobalData.activities.add(RevisedOrderDetailsActivity.this);
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

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());
        isLoggedIn = GlobalData.isLoggedIn(context);
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
//        txt_header_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLoggedIn) {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        loadFreeServices();
        init();

    }

    private void loadFreeServices() {

        DatabaseHandlerNew db = new DatabaseHandlerNew(context);
        try {
            db.open();
            LinkedList<Free_services> list_details = db.getFree_services();

            GlobalData.free_services_accent = list_details.get(0).getFree_service_txt();
            GlobalData.free_services_terminology = list_details.get(1).getFree_service_txt();
            GlobalData.free_services_timestamp = list_details.get(2).getFree_service_txt();
            GlobalData.free_services_identification = list_details.get(3).getFree_service_txt();
            GlobalData.free_services_type = list_details.get(4).getFree_service_txt();

        } catch (Exception e) {
        } finally {
            db.close();
        }
    }


    private void init() {

        Intent iin = getIntent();
        Bundle b = iin.getExtras();


        order_id = b.getInt("order_id", 0);
        assignment_no = b.getString("assignment_no");


        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(context, HomeActivity.class));
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }
        });


        lst_selected_files = (ListView) findViewById(R.id.lst_selected_files);

        //file_info
        txt_assignment_num = (TextView) findViewById(R.id.txt_assignment_num);
        txt_total_duration = (TextView) findViewById(R.id.txt_total_duration);
        txt_total_fee = (TextView) findViewById(R.id.txt_total_fee);
        txt_delivery_date_time = (TextView) findViewById(R.id.txt_delivery_date_time);

        btn_conf_Order=(Button)findViewById(R.id.btn_conf_order);
        //Free services
        txt_fs1 = (TextView) findViewById(R.id.txt_fs1);
        txt_fs2 = (TextView) findViewById(R.id.txt_fs2);
        txt_fs3 = (TextView) findViewById(R.id.txt_fs3);
        txt_fs4 = (TextView) findViewById(R.id.txt_fs4);
        txt_fs5 = (TextView) findViewById(R.id.txt_fs5);

        //service type
        txt_services_type = (TextView) findViewById(R.id.txt_services_type);
        txt_delivery_option = (TextView) findViewById(R.id.txt_delivery_option);
        txt_value_added_services = (TextView) findViewById(R.id.txt_value_added_services);
        txt_time_stamps = (TextView) findViewById(R.id.txt_time_stamps);


        txt_assignment_num.setText("");
        txt_total_duration.setText("");
        txt_total_fee.setText("");
        txt_delivery_date_time.setText("");
        txt_fs1.setText("");
        txt_fs2.setText("");
        txt_fs3.setText("");
        txt_fs4.setText("");
        txt_fs5.setText("");




        lbl_details = (TextView) findViewById(R.id.lbl_details);


        btn_conf_Order.setVisibility(View.VISIBLE);

        btn_conf_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new UpdateOrder().execute(orderDetails);
                }catch (Exception e){
                    GlobalData.printError(e);
                }
            }
        });


        SpannableString details = new SpannableString("Details");
        details.setSpan(new UnderlineSpan(), 0, details.length(), 0);
        lbl_details.setText(details);

        try {

            orderDetails.setOrder_id(order_id);
            orderDetails.setAssignment_no(assignment_no);

//            orderDetails=GlobalData.selectedOrder;
            setOrderDetails();

//            ws_obj = new GetOrderDetails();
//            ws_obj.execute(orderDetails);

//            setOrderDetails(orderDetails);

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
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }
        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(RevisedOrderDetailsActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {
            if (isLoggedIn) {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//               Toast.makeText(context, "Please login now", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {

            if (isLoggedIn) {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(RevisedOrderDetailsActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }

//        else if (id == R.id.nav_confidentiality) {
//            Intent intent = new Intent(RevisedOrderDetailsActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(RevisedOrderDetailsActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//        }

        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(RevisedOrderDetailsActivity.this, AboutusActivity.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_logout) {
            try {


                if (!GlobalData.isNetworkAvailable(context)) {
                    Toast.makeText(context, getResources().getString(R.string.ERR_CONNECTION), Toast.LENGTH_LONG).show();

                } else {
                    new LogoutWebServiceCall(context,getIntent()).execute();
                    Intent intent = new Intent(RevisedOrderDetailsActivity.this, HomeActivity.class);
                    startActivity(intent);

                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }

        }*/ else if (id == R.id.nav_feedback) {

            if (isLoggedIn) {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, FeedbackActivity.class);
                startActivity(intent);

            } else {
                Intent intent = new Intent(RevisedOrderDetailsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class GetOrderDetails extends AsyncTask<OrderDetails, WebServiceResonse, WebServiceResonse> {

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse webServiceResonse = new WebServiceResonse();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar_new.setVisibility(View.VISIBLE);
            try {
                webServiceMySQl = new WebServiceMySQl(context);


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(OrderDetails... params) {

            try {
                OrderDetails orderDetails = params[0];

                webServiceResonse = webServiceMySQl.GetOrderDetails(orderDetails.getAssignment_no(), orderDetails.getOrder_id());

                GlobalData.printMessage(" WEBSERVICE RESPONSE " + webServiceResonse);

            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return webServiceResonse;
        }

        @Override
        protected void onPostExecute(WebServiceResonse webServiceResonse) {
            super.onPostExecute(webServiceResonse);

            String msg = "";
            try {


                if (webServiceResonse.getStatus() == 200) {


//                    OrderDetails orderDetails = getOrderDetails(webServiceResonse.getJsonObject());
                    progress_bar_new.setVisibility(View.GONE);


                } else {

                    msg = webServiceResonse.getMessage();
                }

                if (msg.length() > 0) {

                    GlobalData.showSnackBar(new View(context), msg, true);
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    }


    public OrderDetails setOrderDetails( ) {

        try {


            LinkedList<File_Meta_JSON> file_meta_jsons=new LinkedList<>();
            if (orderDetails.getAssignment_no().length()>0) {

                DatabaseHandlerNew db=new DatabaseHandlerNew(context);
                try {
                    db.open();

                     orderDetails=db.getOrderDetails(orderDetails.getAssignment_no());




                    for (int i=0;i<orderDetails.getRecList().size();i++){

                        File_Meta_JSON bean=new File_Meta_JSON();
                        bean.parseMyrecording(orderDetails.getRecList().get(i));
                        file_meta_jsons.add(bean);
                    }


                }catch ( Exception e){
                    GlobalData.printError(e);
                }finally {
                    db.close();
                }





                        try {

                            //filemeta
//                            LinkedList<File_Meta_JSON> file_meta_jsons = new LinkedList<>();
//                            file_meta_jsons = orderDetails.getFile_meta_jsonsList();
                            selectedFiles = new LinkedList<>();

//                            for (int k = 0; k <= file_meta_jsons.size(); k++) {
//
//                                try {
//
//                                    MyRecording bean =new MyRecording();
//
//                                    bean.parseJSON();
//
//
//                                }catch ( Exception e){
//                                    GlobalData.printError(e);
//                                }
//                                file_meta_jsons.get(k).getFile_name();
//                                file_meta_jsons.get(k).getFile_duration();
//
//
//                            }


                            try {
                                selectedFilesAdapter = new SelectedFilesAdapterOrderDetails(context, file_meta_jsons);
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
                            } catch (Exception e) {
                                GlobalData.printError(e);
                            }


                            //set file info
                            txt_assignment_num.setText(orderDetails.getAssignment_no());
                            txt_total_duration.setText(orderDetails.getTotal_duration());
                            txt_total_fee.setText(orderDetails.getTotal_fees());
                            txt_delivery_date_time.setText(orderDetails.getDelivery_date());

                            //set services_info
                            String service_txt = "", delivey_opt = "", vas = "", time_stamp = "";
                            DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
                            Service_type service_type = new Service_type();
                            Delivery_option delivery_option = new Delivery_option();
                            Vas vas1 = new Vas();
                            TimeStamb timeStamb = new TimeStamb();
                            try {
                                databaseHandler.open();
                                service_type = databaseHandler.getService_type(orderDetails.getService_type_id());
                                service_txt = service_type.getService_text();

                                delivery_option = databaseHandler.getDelivery_option(orderDetails.getDelivery_opt_id());
                                delivey_opt = delivery_option.getDel_option();

                                vas1 = databaseHandler.getVas(orderDetails.getVas_id());
                                vas = vas1.getVas_text();

                                timeStamb = databaseHandler.getTimestamp(orderDetails.getTime_slab_id());
                                time_stamp = timeStamb.getTimestamp_txt();

                                txt_services_type.setText(service_txt);
                                txt_delivery_option.setText(delivey_opt);
                                txt_value_added_services.setText(vas);
                                txt_time_stamps.setText(time_stamp);

                            } catch (Exception e) {
                                GlobalData.printError(e);
                            } finally {
                                databaseHandler.close();
                            }


                            //set free_services
                            txt_fs1.setText(GlobalData.free_services_accent);
                            txt_fs2.setText(GlobalData.free_services_terminology);
                            txt_fs3.setText(GlobalData.free_services_identification);
                            txt_fs4.setText(GlobalData.free_services_timestamp);
                            txt_fs5.setText(GlobalData.free_services_type);

//                    startActivity(new Intent(context,OrderDetails.class));


                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }


                setTAT();

            } else {

                GlobalData.printMessage("No Order Details Key Found");


            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }


        return orderDetails;

    }





    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent in = new Intent(RevisedOrderDetailsActivity.this,
                    OrderHistoryActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }

    }



    /// Update Order Details
    class UpdateOrder extends AsyncTask <OrderDetails,WebServiceResonse,WebServiceResonse>{


        WebServiceMySQl mySQl =null;
        WebServiceResonse resonse=new WebServiceResonse();

        TransparentProgressDialog pd=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd=new TransparentProgressDialog(context);
            pd.show();

        }

        @Override
        protected WebServiceResonse doInBackground(OrderDetails... params) {


            try {
                OrderDetails order= params[0];

                try {
                    //Convert Local Date to IST Date
                    order.setDelivery_date(GlobalData.getLocalToIndianStanderdTime( order.getDelivery_date()));

                }catch (Exception e){
                    GlobalData.printError(e);
                }



                // set Order Placed Details
                order.setOrder_status_id("conf");
                JSONObject object=new JSONObject();
                object.put("user_id",""+ GlobalData.userSelected.getUser_id());
                object.put("total_duration",""+order.getTotal_duration());
                object.put("delivery_date",""+order.getDelivery_date());
                object.put("total_fees",""+order.getTotal_fees());
                object.put("order_complete_details",""+order.getOrder_complete_details());
                object.put("assignment_no",""+order.getAssignment_no());
                object.put("order_id",""+order.getServer_Id());
                object.put("order_status_id",""+order.getOrder_status_id());

                mySQl =new WebServiceMySQl(context);

                resonse= mySQl.updateorder(object);





            }catch (Exception e){
                GlobalData.printError(e);
            }



            return resonse;




        }

        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            String msg="";

            try {

                if(pd!=null) {
                    pd.dismiss();
                    pd=null;
                }

                //
                // {"status":"200","message": "Your Order with Assignment number CAPGEJ-1M is updated !","assignment_no":"CAPGEJ-1M" }


                if(res.getJsonObject().getString("status").equalsIgnoreCase("200")){

               startActivity(new Intent(context, HomeActivity.class));

                }else {


                }
                msg= res.getJsonObject().getString("message");

                if(msg.length()>0){

				//	GlobalData.showSnackBar(btn_conf_Order,msg,true);
                }

            }catch ( Exception e){
                GlobalData.printError(e);
            }


        }
    }

    public void setTAT(){

        try {


            //Context context,int totalDurationinMin,String  delivery_opt_id,String service_type_id,String vas_id,String timestamp_id
            int totalDUration=0;

            try {
                totalDUration= Integer.parseInt(orderDetails.getTotalDurationMin());

            }catch (Exception e){
                GlobalData.printError(e);
            }

           TAT_Calculation tat_calculation=  TATCalculationGLobal.getTotalFeesAndDuration(context, totalDUration, orderDetails.getDelivery_opt_id(), orderDetails.getService_type_id(), orderDetails.getVas_id(), orderDetails.getTime_slab_id());

            orderDetails.setTotal_fees("" + tat_calculation.totalFee);

          if(tat_calculation.totalFee >0) {
              txt_total_fee.setText(context.getResources().getString(R.string.currency) + " " + "" + Math.round(tat_calculation.totalFee));
          }

            orderDetails.setDelivery_date(""+  GlobalData.getStanderdDateFormt(tat_calculation.getDeliveryDateTime()));
            String showdate = GlobalData.showdate(tat_calculation.getDeliveryDateTime().getTime().toString());

            txt_delivery_date_time.setText(showdate);
//            currnetTime.setTimeInMillis(tat_calculation.getDeliveryDateTime().getTimeInMillis());
//            new_todate = tat_calculation.curDelivery_slot.getSlot_to();
//
//            selectedTAT_calculation = tat_calculation;

        }catch (Exception e){
            GlobalData.printError(e);
        }
    }





}
