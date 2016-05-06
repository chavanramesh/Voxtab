package com.voxtab.ariatech.voxtab;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.log.Log4jHelper;

import java.io.File;

/**
 * @author Nikolai Doronin {@literal <lassana.nd@gmail.com>}
 * @since 11/26/14.
 */
public class RecorderApplication extends Application {

    public static RecorderApplication getApplication(@NonNull Context context) {
        if (context instanceof RecorderApplication) {
            return (RecorderApplication) context;
        }
        return (RecorderApplication) context.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
      //  configureLog4j();

    }

    public static void configureLog4j() {

        try {
            // set file name
            String root=  Environment.getExternalStorageDirectory() + "";

            File mkDir = new File(root, GlobalData.logDirectory);
                mkDir.mkdirs();

            String fileName = Environment.getExternalStorageDirectory() + "/"+GlobalData.logDirectory+"/"
                    + "voxtab.log";
            // set log line pattern
            String filePattern = "%d - [%c] - %p : %m%n";
            // set max. number of backed up log files
            int maxBackupSize = 10;
            // set max. size of log file
            long maxFileSize = 1024 * 1024;
            // configure
            Log4jHelper
                    .Configure(fileName, filePattern, maxBackupSize, maxFileSize);
        }catch (Exception e){
            GlobalData.printError(e);
        }



    }
}
