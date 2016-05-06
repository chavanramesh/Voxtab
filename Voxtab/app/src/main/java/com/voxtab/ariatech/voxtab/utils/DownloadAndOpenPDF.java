package com.voxtab.ariatech.voxtab.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Ariatech 2 on 4/19/2016.
 */
public class DownloadAndOpenPDF {

    String dest_file_path = "voxtab_report.pdf";
    int downloadedSize = 0, totalsize;
    String download_file_url = "";
    float per = 0;

    Context context =null;

    public DownloadAndOpenPDF(Context context,String download_file_url){

        try {
            File f=new File(download_file_url);
            if(f.getName().length()>0) {
                dest_file_path = f.getName();
            }

        }catch (Exception e) {
            GlobalData.printError(e);

        }

        this.download_file_url = download_file_url;
        this.context = context;
    }




    public void openPDF(){

        Uri path = Uri.fromFile(downloadFile(download_file_url));
        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(path, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            this.context. startActivity(intent);

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//            Uri screenshotUri = Uri.parse(path);

            sharingIntent.setType("application/pdf");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, path);
            context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getText(R.string.send_to)));


//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.setDataAndType(path, "application/pdf");
//
//            context.startActivity(Intent.createChooser(intent, context.getResources().getText(R.string.aboutus)));

        } catch (ActivityNotFoundException e) {
            GlobalData.printError(e, "PDF Reader application is not installed in your device");
        }
    }

    File downloadFile(String dwnload_file_path) {
        File file = null;
        try {

            dwnload_file_path= dwnload_file_path.replaceAll(" ", "%20");

            URL url = new URL(dwnload_file_path);
            URLConnection urlConnection =  url
                    .openConnection();

//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            // connect
            urlConnection.connect();

            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, to save the downloaded files

           String dir = GlobalData.storageDirectoryPDF;
           File mkDir = new File(SDCardRoot, dir);
           mkDir.mkdirs();

            file = new File(SDCardRoot, dir +"/"+ dest_file_path);

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();
           GlobalData.printMessage("Starting PDF download...");

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
                GlobalData.printMessage("Total PDF File size  : "
                        + (totalsize / 1024)
                        + " KB\n\nDownloading PDF " + (int) per
                        + "% complete");


            }
            // close the output stream when complete //
            fileOutput.close();
            GlobalData.printMessage("Download Complete. Open PDF Application installed in the device.");

        } catch (final Exception e) {
            GlobalData.printError(e, "Some error occured. Press back and try again.");
        }

        return file;
    }



}
