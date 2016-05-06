package com.voxtab.ariatech.voxtab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.ChangePrice;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.util.ArrayList;

/**
 * Created by Local User on 17-Feb-16.
 */
public class ChangePriceAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<ChangePrice> rowItems;
    int selectedIndex = -1;
    ViewHolder holder = new ViewHolder();

    public ChangePriceAdapter(Context context, ArrayList<ChangePrice> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        try {

            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.custom_plan_list, null);
                holder.scheme_name = (TextView) convertView.findViewById(R.id.scheme_name);
                holder.imageViewSelect=(ImageView)convertView.findViewById(R.id.imageviewRadio);
                holder.scheme_duration = (TextView) convertView.findViewById(R.id.scheme_duration);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
                holder.lin_plan = (LinearLayout) convertView.findViewById(R.id.lin_plan);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.scheme_name.setText(rowItems.get(position).getScheme_name());
//            holder.scheme_duration.setText(rowItems.get(position).getScheme_duration());
            if (selectedIndex == position) {


                holder.lin_plan.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                holder.imageViewSelect.setBackground(context.getResources().getDrawable(R.drawable.selected_option_box));

            } else {
                holder.lin_plan.setBackground(context.getResources().getDrawable(R.drawable.unselected_card));
                holder.imageViewSelect.setBackground(context.getResources().getDrawable(R.drawable.unselected_option_box));

            }



            double totalFees_new = 0;
            try {
                totalFees_new = Double.parseDouble(rowItems.get(position).getPrice());

                totalFees_new = (double) Math.round(totalFees_new);
            } catch (Exception e) {
                GlobalData.printError(e);
            }

            holder.price.setText("");

            if(totalFees_new>0) {
                holder.price.setText(context.getResources().getString(R.string.currency) + " " + "" + totalFees_new);
            }



            holder.date_time.setText(rowItems.get(position).getDate_time());


        } catch (Exception e) {
            GlobalData.printError(e);

        }

        convertView.setTag(holder);

        return convertView;
    }

    static class ViewHolder {

        TextView scheme_name, scheme_duration, price, date_time;
        LinearLayout lin_plan;
        ImageView imageViewSelect;

    }
}