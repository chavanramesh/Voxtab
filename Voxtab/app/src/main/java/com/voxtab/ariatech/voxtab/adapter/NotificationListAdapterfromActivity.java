package com.voxtab.ariatech.voxtab.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.Notification;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Local User on 17-Feb-16.
 */
public class NotificationListAdapterfromActivity extends BaseAdapter {

    private Context context;

    private LinkedList<Notification> Items;

    ViewHolder holder = new ViewHolder();

    public NotificationListAdapterfromActivity(Context context, LinkedList<Notification> rowItems) {
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


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.custom_notifications_activity, null);
            holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
            holder.txt_noti_title = (TextView) convertView.findViewById(R.id.txt_noti_title);
            holder.lin_file_info = (LinearLayout) convertView.findViewById(R.id.lin_file_info);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date_time.setText("");

        holder.txt_noti_title.setText("");





        if (Items.get(position).getSoft_del().equalsIgnoreCase("0")) {
            holder.lin_file_info.setBackgroundColor(context.getResources().getColor(R.color.color_unread));
        } else {
            holder.lin_file_info.setBackgroundColor(context.getResources().getColor(R.color.color_read));
        }


        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            long time= Long.parseLong(Items.get(position).getCreated_date());
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(time);
            holder.date_time.setText(df.format(calendar.getTime()));
        } catch (Exception e) {
            GlobalData.printError(e);
        }
        holder.txt_noti_title.setText(Items.get(position).getNotifi_txt());



        convertView.setTag(holder);

        return convertView;
    }

    static class ViewHolder {

        TextView txt_noti_title, date_time;
        LinearLayout lin_file_info;


    }
}
