package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.res.Configuration;

import com.voxtab.ariatech.voxtab.utils.Application;

import java.util.Locale;

/**
 * Created by AriaTech on 4/27/2016.
 */
public class ChangeLanguage extends Application
{


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setLocaleJa(Context context) {
        Locale locale = new Locale("ja");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    public static void setLocaleEn(Context context) {
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }



    public static void setLocaleLanguage(Context context) {
        Locale locale = new Locale(Locale.getDefault().getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }
}