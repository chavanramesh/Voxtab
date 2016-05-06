
package com.voxtab.ariatech.voxtab.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.DownloadTranscriptionActivity;
import com.voxtab.ariatech.voxtab.OrderDetailsActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.OrderHistoryChild;
import com.voxtab.ariatech.voxtab.bean.OrderHistoryGroup;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private List<OrderHistoryGroup> catList;
    private int itemLayoutId;
    private int groupLayoutId;
    private Context ctx;

    public ExpandableAdapter(List<OrderHistoryGroup> catList, Context ctx) {

        this.itemLayoutId = R.layout.item_layout;
        this.groupLayoutId = R.layout.group_layout;
        this.catList = catList;
        this.ctx = ctx;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return catList.get(groupPosition).getItemList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return catList.get(groupPosition).getItemList().get(childPosition).hashCode();
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_layout, parent, false);
        }

        TextView txt_total_fee = (TextView) v.findViewById(R.id.txt_total_fee);
        TextView txt_delivery_date_time = (TextView) v.findViewById(R.id.txt_delivery_date_time);
        TextView txt_order_status_title = (TextView) v.findViewById(R.id.txt_order_status_title);
        TextView txt_order_status_detail = (TextView) v.findViewById(R.id.txt_order_status_detail);
        TextView txt_order_placed_date = (TextView) v.findViewById(R.id.txt_order_placed_date);
        TextView txt_audio_uploaded_status_title = (TextView) v.findViewById(R.id.txt_audio_uploaded_status_title);
        TextView txt_audio_uploaded_date = (TextView) v.findViewById(R.id.txt_audio_uploaded_date);
        TextView txt_delivered_status_title = (TextView) v.findViewById(R.id.txt_delivered_status_title);
        TextView txt_delivered_status_details = (TextView) v.findViewById(R.id.txt_delivered_status_details);
        TextView txt_order_delivered_date = (TextView) v.findViewById(R.id.txt_order_delivered_date);
        TextView lbl_view_details = (TextView) v.findViewById(R.id.lbl_view_details);
        TextView lbl_download_transcription = (TextView) v.findViewById(R.id.lbl_download_transcription);


        ImageView img_order_status = (ImageView) v.findViewById(R.id.img_order_status);
        ImageView img_upload_status = (ImageView) v.findViewById(R.id.img_upload_status);
        ImageView img_delivered_status = (ImageView) v.findViewById(R.id.img_delivered_status);


        LinearLayout linearLayout1= (LinearLayout) v.findViewById(R.id.lin_one);
        LinearLayout linearLayout2= (LinearLayout) v.findViewById(R.id.lin_two);
        LinearLayout linearLayout3= (LinearLayout) v.findViewById(R.id.lin_three);



        final OrderHistoryChild det = catList.get(groupPosition).getItemList().get(childPosition);

        /*if (det.getTxt_status_title().equals("conf")) {
            txt_status_title.setText("Confirmed");
        }*/

        //lbl_view_details.setTag(groupPosition, "order_id");



        try {
            txt_audio_uploaded_date.setText("");
            txt_order_delivered_date.setText("");


            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.GONE);


            String prdPlaced ="";
            try {
                if(det.getTxt_order_date().length()>0) {
                    prdPlaced = GlobalData.convertLongDate(det.getTxt_order_date());
                }

            }catch (Exception e){
                GlobalData.printError(e);
            }



                txt_order_placed_date.setText(prdPlaced);



            String confddate ="";
            try {
                if(det.getOrderConfirmDate().length()>0) {
                    confddate = GlobalData.convertLongDate(det.getOrderConfirmDate());
                }

            }catch (Exception e){
                GlobalData.printError(e);
            }

            String delDate ="";
            try {
                if(det.getOrderConfirmDate().length()>0) {
                    delDate = GlobalData.convertShortDate(det.getOrderDeliveryDate());
                }

            }catch (Exception e){
                GlobalData.printError(e);
            }







            if(confddate.length()>0){
                linearLayout2.setVisibility(View.VISIBLE);

//                try {
//                    confddate= GlobalData.convertShortDateWithHHmm(confddate);
//                }catch (Exception e){
//                    GlobalData.printError(e);
//                }

                txt_audio_uploaded_date.setText("" + confddate);
            }


            if(delDate.length()>0){
                linearLayout3.setVisibility(View.VISIBLE);
                txt_order_delivered_date.setText("" + delDate);
            }

        }catch (Exception e){
            GlobalData.printError(e);
        }

        try {

        }catch (Exception e){
            GlobalData.printError(e);
        }

        txt_total_fee.setText("");
        if(det.getTxt_total_fee().length()>0) {
            txt_total_fee.setText(ctx.getResources().getString(R.string.currency) + " " + det.getTxt_total_fee());
        }

        txt_delivery_date_time.setText("");
        try {

            txt_delivery_date_time.setText( GlobalData.convertDeliveryDateOrderHistory(det.getTxt_delivery_date()));


        }catch (Exception e){
            GlobalData.printError(e);
        }




        // txt_order_status_title.setText(det.getTxt_status_title());
//        txt_order_status_detail.setText(det.getTxt_status_detail());


        //   txt_audio_uploaded_status_title.setText(det.getTxt_upload_status_title());
//        txt_audio_uploaded_date.setText(det.getTxt_upload_date());

        // txt_delivered_status_title.setText(det.getTxt_delivered_status_title());
//        txt_delivered_status_details.setText(det.getTxt_delivered_status_details());
//        txt_order_delivered_date.setText(det.getTxt_delivery_date());

        img_order_status.setImageResource(R.drawable.flag_icon);
        img_upload_status.setImageResource(R.drawable.upload_successful);
        img_delivered_status.setImageResource(R.drawable.delivered_completed);

        lbl_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // view_details
                // Toast.makeText(ctx,""+groupPosition,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ctx, OrderDetailsActivity.class);
                intent.putExtra("order_id", catList.get(groupPosition).getOrder_id());

                System.out.print(catList.get(groupPosition).getOrder_id());
                intent.putExtra("assignment_no", catList.get(groupPosition).getAssignment_num());
                ctx.startActivity(intent);


            }
        });

        lbl_download_transcription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to transcription page

                try {
                    GlobalData.selectedOrder =catList.get(groupPosition).getOrderDetails();

                    Intent intent = new Intent(ctx, DownloadTranscriptionActivity.class);
                    intent.putExtra("order_id", catList.get(groupPosition).getOrder_id());

                    System.out.print(catList.get(groupPosition).getOrder_id());
                    intent.putExtra("assignment_no", catList.get(groupPosition).getAssignment_num());
                    ctx.startActivity(intent);
                }catch (Exception e){
                    GlobalData.printError(e);
                }







            }
        });
        return v;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = catList.get(groupPosition).getItemList().size();
        System.out.println("Child for group [" + groupPosition + "] is [" + size + "]");
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return catList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return catList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return catList.get(groupPosition).hashCode();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.group_layout, parent, false);
        }

        TextView txt_assignment_num = (TextView) v.findViewById(R.id.txt_assignment_num);
        TextView txt_status_title = (TextView) v.findViewById(R.id.txt_status_title);
        TextView txt_status_orderdate = (TextView) v.findViewById(R.id.txt_orderDate);
        ImageView img_order_status_icon = (ImageView) v.findViewById(R.id.img_order_status);

        OrderHistoryGroup cat = catList.get(groupPosition);


        txt_assignment_num.setText(cat.getAssignment_num());
        txt_status_title.setText(cat.getStatus_title());
        //  txt_status_title.setText(cat.getStatus_title());
      /*  if (cat.getStatus_title().equals("conf")) {

        }*/


       // txt_status_orderdate.setText("");
        String placeddate ="";
        try {
            if(cat.getOrderPlacedDate().length()>0) {
                placeddate = GlobalData.convertShortDate(cat.getOrderPlacedDate());
            }

        }catch (Exception e){
            GlobalData.printError(e);
        }




        txt_status_orderdate.setText("  " + placeddate + "  ");



        img_order_status_icon.setImageResource(R.drawable.flag_icon);

        int drowId=0;
        try {
            drowId= GlobalData.getImage(cat.getOrderDetails().getOrder_status_id());
        }catch (Exception e){
            GlobalData.printError(e);
        }

        if(drowId>.0){
            img_order_status_icon.setImageResource(drowId);
        }




        return v;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
