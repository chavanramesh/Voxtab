package com.voxtab.ariatech.voxtab.globaldata;

import android.content.Context;

import com.voxtab.ariatech.voxtab.bean.TAT_Calculation;
import com.voxtab.ariatech.voxtab.bean.VAS_Rate;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;

import java.util.Calendar;

/**
 * Created by MAC 2 on 4/23/2016.
 */
public class TATCalculationGLobal {



    public static  TAT_Calculation getTotalFeesAndDuration(Context context,float totalDurationinMin,String  delivery_opt_id,String service_type_id,String vas_id,String timestamp_id){

        DatabaseHandlerNew db=new DatabaseHandlerNew(context);

        TAT_Calculation tat_calculation=new TAT_Calculation();
        tat_calculation.totalDuration=totalDurationinMin;


        tat_calculation.setTimestamp_id(timestamp_id);

        try {
            db.open();


            //1:     Get Time Slab
            tat_calculation.time_slab = db.getTime_slab(totalDurationinMin);

            // Check Time Slab

            if(tat_calculation.time_slab.getTime_slab_id().length()<=0){

                // No Time slab Found....
                return tat_calculation ;
            }


            //2: Get Price

            tat_calculation.price= db.getPrice(delivery_opt_id,service_type_id,tat_calculation.time_slab.getTime_slab_id());


            //3: Get Total Time And Fees

            int lastFlag= 0;

            try {
                lastFlag = Integer.parseInt(tat_calculation.time_slab.getIs_last());
            }catch (Exception e){
                GlobalData.printError(e);
            }

            // TAt
            float tat=0;

            try{
                tat = Float.parseFloat(tat_calculation.price.getTat());
            }catch (Exception e){
                GlobalData.printError(e);
            }

            // charge
            float charge=0;

            try{
                charge = Float.parseFloat(tat_calculation.price.getPrice());
            }catch (Exception e){
                GlobalData.printError(e);
            }

            // mincharge
            float mincharge=0;

            try{
                mincharge = Float.parseFloat(tat_calculation.price.getMin_charges());
            }catch (Exception e){
                GlobalData.printError(e);
            }


            float baseTime=0;
            if(lastFlag==1){

                // Calculate TAT as per  min
                baseTime= GlobalData.getTotalMins(tat,totalDurationinMin);

            }else{

                // Calculate time as per hour
                baseTime= tat;
            }


            //4:   // Total fees

            float tempPrice =0;
            float minCharge=0;
            try {
                tempPrice = Float.parseFloat(tat_calculation.price.getPrice());
                minCharge = Float.parseFloat(tat_calculation.price.getMin_charges());
            }catch (Exception e){
                GlobalData.printError(e);
            }


            tat_calculation.baseFee = totalDurationinMin * tempPrice;

            if(minCharge>  tat_calculation.baseFee){
                tat_calculation.baseFee= minCharge;
            }

            //5: Get TimeStamp

            tat_calculation.timestamps_cal =db.getTimeStampCalculationData(service_type_id, delivery_opt_id, timestamp_id);


            float perval=0;
            try{
                perval =Float.parseFloat( tat_calculation.timestamps_cal.getPercentage_value());
            }catch (Exception e){
                GlobalData.printError(e);
            }
            tat_calculation.totalPremium =0;
            tat_calculation.premiumperhour =0;
            if(perval>0) {

                try {


                tat_calculation.totalPremium= (float)( tat_calculation.baseFee * ((perval) / 100));
                }catch (Exception e){                    GlobalData.printError(e);                }

                try {
                tat_calculation.baseFee =  tat_calculation.baseFee + ( tat_calculation.baseFee * ((perval) / 100));
                }catch (Exception e){                    GlobalData.printError(e);                }

                try {
                if( tat_calculation.totalPremium>0){
                    tat_calculation.premiumperhour = (float)tat_calculation.totalPremium/ totalDurationinMin;
                }
                }catch (Exception e){                    GlobalData.printError(e);                }

            }


            ///Value Added Services

            VAS_Rate vas_rate = db.getVASPrice(tat_calculation.time_slab.getTime_slab_id(),vas_id,service_type_id);


            // Add vat TAT in baseTAT
            float vatTAT=0;
            try {
                vatTAT = Float.parseFloat(vas_rate.getTat());
            }catch (Exception e){
                GlobalData.printError(e);
            }

            float baseTimeVat=0;
            if(lastFlag==1){

                // Calculate TAT as per  min
                baseTimeVat= GlobalData.getTotalMins(vatTAT,totalDurationinMin);
            }else{
                // Calculate time as per hour

                baseTimeVat= vatTAT;
            }



            tat_calculation.totalTAT = GlobalData.addTAT(baseTime,baseTimeVat);


            // Add VAS Price

            float vatPrice=0;
            try {
                vatPrice = Float.parseFloat(vas_rate.getPrice());
            }catch (Exception e){
                GlobalData.printError(e);
            }

            tat_calculation.totalFee =   tat_calculation.baseFee +   (totalDurationinMin * vatPrice);

            float minVat=0;

            try{
                minVat= Float.parseFloat(vas_rate.getMin_charges());
            }catch (Exception e){
                GlobalData.printError(e);
            }


            if(minVat>  tat_calculation.totalFee){
                tat_calculation.totalFee= minVat;
            }



            //6: Remove Sundays and Holidays from Current Date with Added totalTAT
            int holidays=0;
            Calendar curCalendar = Calendar.getInstance();

            int sizeT=(int)( tat_calculation.totalTAT );

            for (int i=0;i< (int) sizeT ;i++){

                try{

                    GlobalData.printMessage("" + curCalendar.getTime());
                    if(curCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || db.isHolidays(curCalendar.get(Calendar.DAY_OF_MONTH),curCalendar.get(Calendar.MONTH) + 1 ,curCalendar.get(Calendar.YEAR))){
                        holidays= holidays+1;
                        sizeT = sizeT + 1;
                    }

                    curCalendar.add(Calendar.DAY_OF_MONTH,1);

                }catch (Exception e){
                    GlobalData.printError(e);
                }

            }




            tat_calculation.totalHolidays= holidays;
            tat_calculation.deliveryDateTime  = curCalendar;



            try{
                int daysAdded= tat_calculation.deliveryDateTime.get(Calendar.DAY_OF_MONTH)+ (int)( tat_calculation.totalTAT );

                GlobalData.printMessage(""+tat_calculation.deliveryDateTime.getTime());

//                tat_calculation.deliveryDateTime.add(Calendar.DAY_OF_MONTH, (int) ( tat_calculation.totalTAT ) );

                GlobalData.printMessage(""+tat_calculation.deliveryDateTime.getTime());

                int hoursAdded =(int) (( tat_calculation.totalTAT  - (int)( tat_calculation.totalTAT )) * 10);
                tat_calculation.deliveryDateTime.add(Calendar.HOUR_OF_DAY, hoursAdded);


                GlobalData.printMessage("" + tat_calculation.deliveryDateTime.getTime());

            } catch (Exception e) {
                GlobalData.printError(e);
            }





         //   tat_calculation.deliveryDateTime.add(Calendar.DAY_OF_WEEK, holidays);


            GlobalData.printMessage("" + tat_calculation.deliveryDateTime.getTime());


            //7: Get Delivery Slot

            float nexthourMM=0;
            try {
                String temp = tat_calculation.deliveryDateTime.get(Calendar.HOUR_OF_DAY)+"."+ tat_calculation.deliveryDateTime.get(Calendar.MINUTE);
                nexthourMM= Float.parseFloat(temp);
            }catch (Exception e){
                GlobalData.printError(e);
            }


            int isSat=0;

            if(curCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                isSat=1;
            }


            tat_calculation.curDelivery_slot=  db.getDelivery_slot(nexthourMM,isSat);
            tat_calculation.nextDelivery_slot  =  db.getNextDelivery_slot(tat_calculation.curDelivery_slot, isSat);

            // Set Hours And Minit to NextCal Date from Next Del. SlotFromTime

            try {
                String temp=  tat_calculation.nextDelivery_slot .getSlot_from();
                int hour = 0;
                try {
                    String sub=temp.substring(0, temp.indexOf('.'));
                    hour = Integer.parseInt(sub);
                }catch (Exception e){GlobalData.printError(e);}

                int min =0;
                try {
                    String sub=temp.substring(temp.indexOf('.')+1,temp.length());
                    min = Integer.parseInt(sub);
                }catch (Exception e){GlobalData.printError(e);}


                tat_calculation.deliveryDateTime.set(Calendar.HOUR_OF_DAY,hour);
                tat_calculation.deliveryDateTime.set(Calendar.MINUTE,min);


                GlobalData.printMessage("" + tat_calculation.deliveryDateTime.getTime());

            }catch (Exception e){
                GlobalData.printError(e);
            }

// Total fee, Time , nxtDel_slot, nxtDel_slot ,Discount

            // Discount

            tat_calculation.discountPer=0;
            try {

                String date=GlobalData.getStanderdDateFormt(Calendar.getInstance());

                tat_calculation.discount=db.getDiscount(service_type_id,delivery_opt_id,vas_id,totalDurationinMin,date);

                if( tat_calculation.discount.getDiscount().length()>0) {
                    tat_calculation.discountPer = Float.parseFloat( tat_calculation.discount.getDiscount());
                }

            }catch (Exception e){
                GlobalData.printError(e);
            }


            // Add Discount

            try {
                tat_calculation.grossFee=  tat_calculation.totalFee;
                tat_calculation.discount_amt = (float) (tat_calculation.totalFee* (tat_calculation.discountPer/100));

                tat_calculation.totalFee= tat_calculation.totalFee - (tat_calculation.totalFee* (tat_calculation.discountPer/100));

            }catch (Exception e){
                GlobalData.printError(e);
            }




        }catch (Exception e){
            GlobalData.printError(e);
        }finally {
            db.close();
        }

        return  tat_calculation;
    }

}
