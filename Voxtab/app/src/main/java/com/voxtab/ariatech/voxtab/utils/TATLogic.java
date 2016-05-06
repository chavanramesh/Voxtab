package com.voxtab.ariatech.voxtab.utils;

import android.content.Context;

import com.voxtab.ariatech.voxtab.bean.Delivery_option;
import com.voxtab.ariatech.voxtab.bean.Delivery_slot;
import com.voxtab.ariatech.voxtab.bean.Holidays;
import com.voxtab.ariatech.voxtab.bean.Price;
import com.voxtab.ariatech.voxtab.bean.Time_slab;
import com.voxtab.ariatech.voxtab.bean.Timestamps_cal;
import com.voxtab.ariatech.voxtab.bean.VAS_Rate;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by MAC 2 on 4/14/2016.
 */
public class TATLogic {


    Context context = null;
    // Set Data
    public static ArrayList<Time_slab> timeSlabs=new ArrayList<>();
    public static ArrayList<Delivery_option> deliveryOption=new ArrayList<>();
    public static ArrayList<Price> priceArrayList=new ArrayList<>();
    public static ArrayList<VAS_Rate> VASPrice_list=new ArrayList<>();
    public ArrayList<Holidays> holidays_list=new ArrayList<>();
    public static ArrayList<Delivery_slot> deliverySlot_list=new ArrayList<>();
    public static ArrayList<Timestamps_cal> timeStampCalculationArrayList=new ArrayList<>();
    public static ArrayList<VAS_Rate> vasPriceArrayList=new ArrayList<>();



    public TATLogic(Context context){

        this.context =context;
    }


    void init(){
        DatabaseHandlerNew databaseHandler = new DatabaseHandlerNew(context);
        try {
            databaseHandler.open();


            // Load Delivery Option
           LinkedList list_details = databaseHandler.getDelivery_option();
            deliveryOption = new ArrayList<>(list_details);



           // Load delivery Slot





//            loadDelivereySlots();
//            loadGetTimeStampCalculation();
//            loadVASPrice();
//            loadHolidays();




        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            databaseHandler.close();
        }
    }



}
