package com.voxtab.ariatech.voxtab.globaldata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.HomeActivity;
import com.voxtab.ariatech.voxtab.bean.Users;
import com.voxtab.ariatech.voxtab.utils.SharedPreferencesUtility;

/**
 * Created by MAC 2 on 4/26/2016.
 */
public class LogoutWebServiceCall extends AsyncTask<String, WebServiceResonse, WebServiceResonse> {

    WebServiceMySQl webServiceMySQl = null;
    WebServiceResonse resonse = new WebServiceResonse();

    SharedPreferencesUtility sharedPreferencesUtility=null;
    TransparentProgressDialog pd = null;

    Context context=null;
    Intent intent=null;

    public  LogoutWebServiceCall(Context context, Intent intent){
        this.context=context;
        this.intent=intent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
//            pd = new TransparentProgressDialog(context);
//            pd.show();
            webServiceMySQl = new WebServiceMySQl(context);

        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }

    @Override
    protected WebServiceResonse doInBackground(String... params) {
        try {
            resonse = webServiceMySQl.Logout();


        } catch (Exception e) {
            GlobalData.printError(e);
        }


        return resonse;
    }


    @Override
    protected void onPostExecute(WebServiceResonse res) {
        super.onPostExecute(res);


//        if (pd != null) {
//            pd.dismiss();
//            pd = null;
//        }
        try {


            if (res.getStatus() == 200) {

               GlobalData.logoutTask(context);


                       ((Activity) context).finish();
               context.startActivity(new Intent(context, HomeActivity.class));


            }
            Toast.makeText(context, res.getMessage(), Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }
}