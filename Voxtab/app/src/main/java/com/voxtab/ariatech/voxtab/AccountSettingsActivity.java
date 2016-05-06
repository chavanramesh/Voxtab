package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariatech.lib_project.CommonUtil;
import com.google.gson.Gson;
import com.voxtab.ariatech.voxtab.bean.Company_info;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.customimages.CircularSmartImageView;
import com.voxtab.ariatech.voxtab.customimages.SmartImageView;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.LogoutWebServiceCall;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

/**
 * Created by AriaTech on 4/12/2016.
 */
public class AccountSettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Context context;
    private Toolbar toolbar;

    private static int RESULT_LOAD_IMAGE = 1;
    SmartImageView ivImage;
    private ProgressBar progress_bar_new, progress_bar_news;
    LinearLayout lin_change_pass, lin_my_profile, lin_contents, lin_profile_details;
    boolean flag_lin_pass = false, flag_lin_profile = false;
    Button btn_edit;
    public GetProfileDetailsWeberviceCall ws_obj = null;
    public UpdateProfileWeberviceCall obj_update = null;

    //profile details
    EditText edt_name, edt_org, edt_email, edt_contact;
    String name = "", org_name = "", email = "", contact = "";


    // change pass
    EditText edt_old_pass, edt_new_pass, edt_re_pass;
    Button btn_save;
    String old_pass = "", new_pass = "", re_pass = "", img_path = " ";
    private boolean isLoggedIn;
    private TextView txt_header_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.activities.add(AccountSettingsActivity.this);
        setContentView(R.layout.activity_account_settings);
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
        isLoggedIn = GlobalData.isLoggedIn(context);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
//        navigationView.addHeaderView(headerView);

        GlobalData.setLoginAndLogout(context, navigationView, getIntent());

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


        lin_change_pass = (LinearLayout) findViewById(R.id.lin_change_pass);

        lin_my_profile = (LinearLayout) findViewById(R.id.lin_my_profile);
        lin_contents = (LinearLayout) findViewById(R.id.lin_contents);
        lin_profile_details = (LinearLayout) findViewById(R.id.lin_profile_details);

        lin_contents.setVisibility(View.GONE);
        lin_profile_details.setVisibility(View.GONE);
        ivImage = (SmartImageView) findViewById(R.id.item_image);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_org = (EditText) findViewById(R.id.edt_org);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_contact = (EditText) findViewById(R.id.edt_contact);


        //change pass
        edt_old_pass = (EditText) findViewById(R.id.edt_old_pass);
        edt_new_pass = (EditText) findViewById(R.id.edt_new_pass);
        edt_re_pass = (EditText) findViewById(R.id.edt_re_pass);
        btn_save = (Button) findViewById(R.id.btn_save);


        progress_bar_new = (ProgressBar) findViewById(R.id.progress_bar_new);
        progress_bar_new.setVisibility(View.GONE);

        progress_bar_news = (ProgressBar) findViewById(R.id.progress_bar_news);
        progress_bar_news.setVisibility(View.GONE);

        btn_edit = (Button) findViewById(R.id.btn_edit);


        edt_name.setEnabled(false);
        edt_email.setEnabled(false);
        edt_org.setEnabled(false);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (GlobalData.isNetworkAvailable(context)) {
                        obj_update = new UpdateProfileWeberviceCall();
                        obj_update.execute();

                    }

                } catch (Exception e) {
                    GlobalData.printError(e);
                }

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    old_pass = edt_old_pass.getText().toString();
                    new_pass = edt_new_pass.getText().toString();
                    re_pass = edt_re_pass.getText().toString();


                    if (old_pass.length() > 0 && new_pass.length() > 0 && re_pass.length() > 0) {

                        if (!GlobalData.isNetworkAvailable(context)) {
                            GlobalData.showSnackBar(btn_save, getString(R.string.no_connection), true);

                        } else {
                            new ChangePassWeberviceCall().execute();
                        }


                    } else {

                        GlobalData.showSnackBar(btn_save, getString(R.string.validation_password), true);
                    }
                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        lin_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_lin_pass) {
                    lin_contents.setVisibility(View.GONE);
                    flag_lin_pass = false;
                } else {
                    flag_lin_pass = true;
                    lin_contents.setVisibility(View.VISIBLE);

                }

            }
        });


        lin_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_lin_profile) {
                    lin_profile_details.setVisibility(View.GONE);
                    flag_lin_profile = false;
                } else {
                    flag_lin_profile = true;
                    lin_profile_details.setVisibility(View.VISIBLE);
                    try {


                        Users users=GlobalData.getMemberId(context);

                        if(users.getUser_id()>0){
                            setUserData(users);
                        }else {

                            ws_obj = new GetProfileDetailsWeberviceCall();
                            ws_obj.execute();
                        }
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }


                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            img_path = picturePath;

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
                Intent intent = new Intent(AccountSettingsActivity.this, NotificationsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.NotificationsActivity");
                startActivity(intent);
            }


        } else if (id == R.id.nav_recordings) {
            Intent intent = new Intent(AccountSettingsActivity.this, MyRecordingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_order_history) {

            if (isLoggedIn) {
                Intent intent = new Intent(AccountSettingsActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.OrderHistoryActivity");
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {
            if (isLoggedIn) {
                Intent intent = new Intent(AccountSettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            } else {
//                Toast.makeText(context, R.string.login_alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.SettingsActivity");
                startActivity(intent);
            }



        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(AccountSettingsActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {


        }
//        else if (id == R.id.nav_confidentiality) {
//
//            Intent intent = new Intent(AccountSettingsActivity.this, ConfidentialityActivity.class);
//            startActivity(intent);
//
//
//        } else if (id == R.id.nav_terms_condition) {
//
//            Intent intent = new Intent(AccountSettingsActivity.this, TermsActivity.class);
//            startActivity(intent);
//
//
//        }
        else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(AccountSettingsActivity.this, AboutusActivity.class);
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
//                    Intent intent = new Intent(AccountSettingsActivity.this, HomeActivity.class);
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
                Intent intent = new Intent(AccountSettingsActivity.this, FeedbackActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                intent.putExtra("Name", "com.voxtab.ariatech.voxtab.FeedbackActivity");
                startActivity(intent);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // GEt profile details
    public class GetProfileDetailsWeberviceCall extends AsyncTask<Users, WebServiceResonse, WebServiceResonse> {

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse resonse = new WebServiceResonse();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progress_bar_new.setVisibility(View.VISIBLE);
                btn_edit.setEnabled(false);


                webServiceMySQl = new WebServiceMySQl(context);


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(Users... params) {
            try {
                resonse = webServiceMySQl.Getprofile();


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

                if (res.getStatus() == 200) {

                    JSONArray array = new JSONArray();

                    try {

                        array = res.getJsonObject().getJSONArray("user_details");

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    if (array.length() > 0) {
                        setuserdata(array);
                    }

                   /* else {
                        GlobalData.showSnackBar(btn_edit, res.getMessage(), true);
                    }*/


                } else {

                    GlobalData.showSnackBar(btn_edit, res.getMessage(), true);
                }


                progress_bar_new.setVisibility(View.GONE);
                btn_edit.setEnabled(true);
            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    }


    public void setuserdata(JSONArray array) {


        LinkedList<Users> list = new LinkedList<>();
        Company_info list1 = new Company_info();

        try {

            for (int i = 0; i < array.length(); i++) {
                try {

                    JSONObject obj = array.getJSONObject(i);

                    Users users = new Users();
                    users.parseJSON(obj);
                    list.add(users);
                    String org_name = "";
                    DatabaseHandlerNew databaseHandlerNew = new DatabaseHandlerNew(context);

                    try {

                        databaseHandlerNew.open();
                        list1 = databaseHandlerNew.getCompany_info(users.getMembership_id());
                        edt_org.setText(list1.getOrg_name_eng());
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    } finally {
                        databaseHandlerNew.close();
                    }


                    String url = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + users.getPhoto());
                    ivImage.setImageUrl(url, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);

                    edt_name.setText(users.getFirst_name());
                    edt_email.setText(users.getEmail());
                    edt_contact.setText(users.getMobile_no());

                } catch (Exception e) {
                    GlobalData.printError(e);
                }
            }


        } catch (
                Exception e
                )

        {
            GlobalData.printError(e);

        }


    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


    //update profile details
    public class UpdateProfileWeberviceCall extends AsyncTask<Users, WebServiceResonse, WebServiceResonse> {

        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);
        WebServiceResonse resonse = new WebServiceResonse();

        Users users = new Users();
        Company_info company_info = new Company_info();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar_new.setVisibility(View.VISIBLE);
            btn_edit.setEnabled(false);

            try {
                webServiceMySQl = new WebServiceMySQl(context);
                resonse = new WebServiceResonse();

            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }

        @Override
        protected WebServiceResonse doInBackground(Users... params) {
            try {

                String base64="";
                try {
                    // convert from bitmap to byte array


// get the base 64 string
                     base64 = Base64.encodeToString(getBytesFromBitmap(BitmapFactory.decodeFile(img_path)),
                            Base64.NO_WRAP);

                }catch (Exception e){
                    GlobalData.printError(e);
                }



                getprofiledata();
                users.setFirst_name(name);
                users.setEmail(email);
                users.setMobile_no(contact);
                company_info.setOrg_name_eng(org_name);
                users.setImgBase64(base64);

                resonse = webServiceMySQl.Updateprofile(users);

            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }
        // Set Data


        @Override
        protected void onPostExecute(WebServiceResonse res) {
            super.onPostExecute(res);

            ws_obj = null;

            try {

                if (res.getStatus() == 200) {


                    try {
                    JSONObject object=res.getJsonObject();
                        JSONArray array=object.getJSONArray("user_details");

                        if (array.length()>0) {
                            JSONObject user= array.getJSONObject(0);
                            SharedPreferences settings = PreferenceManager
                                    .getDefaultSharedPreferences(context);
                            SharedPreferences.Editor prefsEditor = settings.edit();
                     // myObject - instance of MyObject
                            prefsEditor.putString("users", user.toString());
                            prefsEditor.commit();
                        }

                    }catch (Exception e){
                        GlobalData.printError(e);
                    }




                    GlobalData.showSnackBar(btn_edit, res.getMessage(), true);
                } else {

                    GlobalData.showSnackBar(btn_edit, res.getMessage(), true);
                }

                progress_bar_new.setVisibility(View.GONE);
                btn_edit.setEnabled(true);

            } catch (Exception e)

            {
                GlobalData.printError(e);
            }
        }


    }


    public void getprofiledata() {
        name = edt_name.getText().toString();
        org_name = edt_org.getText().toString();
        email = edt_email.getText().toString();
        contact = edt_contact.getText().toString();


    }

    class ChangePassWeberviceCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {

        WebServiceResonse resonse = new WebServiceResonse();
        WebServiceMySQl webServiceMySQl = new WebServiceMySQl(context);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar_news.setVisibility(View.VISIBLE);
            btn_save.setEnabled(false);

        }

        @Override
        protected WebServiceResonse doInBackground(String... params) {


            try {

                resonse = webServiceMySQl.Changepassword(old_pass, new_pass);


            } catch (Exception e) {
                GlobalData.printError(e);
            }


            return resonse;
        }

        @Override
        protected void onPostExecute(WebServiceResonse resonse) {
            super.onPostExecute(resonse);

            try {
                btn_save.setEnabled(true);

                if (resonse.getStatus() == 200) {


                    GlobalData.showSnackBar(btn_save, resonse.getMessage(), true);


                    Intent intent = new Intent(AccountSettingsActivity.this, SettingsActivity.class);
                    startActivity(intent);

                } else {

                    GlobalData.showSnackBar(btn_save, resonse.getMessage(), true);

                }

                progress_bar_news.setVisibility(View.GONE);

            } catch (Exception e) {
                GlobalData.printError(e);
            }

        }
    }



    void setUserData( Users users){


        try {
            edt_name.setText("");
            edt_email.setText("");
            edt_contact.setText("");
            edt_org.setText("");

            try {

                edt_name.setText(users.getFirst_name()+" "+users.getLast_name());
                edt_email.setText(users.getEmail());
                edt_org.setText(users.getMobile_no());

                edt_org.setText(users.getCompany_info().getOrg_name_eng());

                String url = CommonUtil.getFormatURL(GlobalData.IMAGE_URL + users.getPhoto());
                ivImage.setImageUrl(url, GlobalData.IMG_HEIGHT_M1, GlobalData.IMG_WIDTH_M1, ScalingUtilities.ScalingLogic.CROP);

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {

            }





        }catch (Exception e){
            GlobalData.printError(e);
        }


    }


}
