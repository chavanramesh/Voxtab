package com.voxtab.ariatech.voxtab.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.OrderDetails;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.utils.DownloadAndOpenPDF;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Local User on 30-Jan-16.
 */
public class DownloadTranscription_list_adapter extends BaseAdapter {

    private Context context;
    private LinkedList<File_Meta_JSON> rowItems;
    ViewHolder holder = new ViewHolder();
    OrderDetails orderDetails=new OrderDetails();

    public DownloadTranscription_list_adapter(Context context, LinkedList<File_Meta_JSON> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {


        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        convertView = null;
        final LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_list_downloadtranscription, null);

            holder.txt_filename = (TextView) convertView.findViewById(R.id.txt_file_name);
            holder.txt_date_time = (TextView) convertView.findViewById(R.id.txt_delivery_date_time);
            holder.txt_duration = (TextView) convertView.findViewById(R.id.txt_duration);
            holder. img_share=(LinearLayout)convertView.findViewById(R.id.layimg_share);
            holder.  headerLayout=(LinearLayout)convertView.findViewById(R.id.headerLayout);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

        }catch (Exception e){
            GlobalData.printError(e);
        }


        holder.  headerLayout.setVisibility(View.GONE);


        if(position==0){
            holder.  headerLayout.setVisibility(View.VISIBLE);

        }
        holder.txt_filename.setText("");
        holder.txt_date_time.setText("");
        holder.txt_duration.setText("");

        holder.txt_filename.setText(rowItems.get(position).getFile_name());
        holder.txt_filename.setTag(position);

        holder.txt_date_time.setText( GlobalData.convertShortDateDownTRPg(rowItems.get(position).getTrans_file_up_date()));
        holder.txt_date_time.setTag(position);

        holder.txt_duration.setText(rowItems.get(position).getFile_duration());
        holder.txt_duration.setTag(position);

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    if(rowItems.get(position).getTrans_file_name().length()>0){

                        try {

                            new openPDF().execute(GlobalData.PDF_URL+""+    rowItems.get(position).getTrans_file_name());

//                            new openPDF().execute(   rowItems.get(position).getTrans_file_name());

                        }catch (Exception e){
                            GlobalData.printError(e);
                        }
                    }

                }catch (Exception e){
                    GlobalData.printError(e);
                }
            }
        });


        convertView.setTag(holder);
        return convertView;

    }


    public class ViewHolder {

        TextView txt_filename, txt_date_time, txt_duration;
        LinearLayout img_share,headerLayout;


    }


    class openPDF extends AsyncTask<String,String,String>{


        TransparentProgressDialog pd=null;

        ProgressDialog pDialog=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
            pd= new TransparentProgressDialog(context);
            pd.show();


            }catch (Exception e){
                GlobalData.printError(e);
            }
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                DownloadAndOpenPDF downloadAndOpenPDF=new DownloadAndOpenPDF(context,params[0]);
                downloadAndOpenPDF.openPDF();

            }catch (Exception e){
                GlobalData.printError(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if(pd!=null){
                    pd.dismiss();
                    pd=null;
                }

//                if(pDialog!=null){
//                    pDialog.dismiss();
//                    pDialog=null;
//                }
            }catch (Exception e){
                GlobalData.printError(e);
            }
        }
    }



}
