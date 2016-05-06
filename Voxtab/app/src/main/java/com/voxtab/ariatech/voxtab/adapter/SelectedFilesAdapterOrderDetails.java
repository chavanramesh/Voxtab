package com.voxtab.ariatech.voxtab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.beanwebservice.File_Meta_JSON;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.util.LinkedList;

/**
 * Created by Local User on 17-Feb-16.
 */
public class SelectedFilesAdapterOrderDetails extends BaseAdapter {

    private Context context;

    private LinkedList<File_Meta_JSON> Items;

    ViewHolder holder = new ViewHolder();

    public SelectedFilesAdapterOrderDetails(Context context, LinkedList<File_Meta_JSON> rowItems) {
        this.context = context;
        this.Items = rowItems;
    }


    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        try {

            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.custom_selected_files, null);
                holder.selcted_file = (TextView) convertView.findViewById(R.id.txt_selected_file);
                holder.file_duration = (TextView) convertView.findViewById(R.id.txt_file_duration);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.selcted_file.setText(Items.get(position).getFile_name());
            holder.file_duration.setText(Items.get(position).getFile_duration());


        } catch (Exception e) {
            GlobalData.printError(e);

        }

        convertView.setTag(holder);

        return convertView;
    }

    static class ViewHolder {

        TextView selcted_file, file_duration;

    }
}
