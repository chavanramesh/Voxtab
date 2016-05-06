package com.voxtab.ariatech.voxtab.utils;


import com.facebook.stetho.Stetho;
import com.voxtab.ariatech.voxtab.BuildConfig;

/**
 * Created by Ganesh on 25-Nov-2015.
 */


public  class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontSetting.setDefaultFont(this, "DEFAULT", "fonts/opensans_regular.ttf");
        FontSetting.setDefaultFont(this, "MONOSPACE", "fonts/opensans_regular.ttf");
        FontSetting.setDefaultFont(this, "SERIF", "fonts/opensans_regular.ttf");
        FontSetting.setDefaultFont(this, "SANS_SERIF", "fonts/opensans_regular.ttf");
        initStetho();
    }

    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}




