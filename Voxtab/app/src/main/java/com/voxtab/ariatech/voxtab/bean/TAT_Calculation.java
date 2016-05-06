package com.voxtab.ariatech.voxtab.bean;

import java.util.Calendar;

/**
 * Created by MAC 2 on 4/22/2016.
 */
public class TAT_Calculation {


    // Total fee, Time , nxtDel_slot, nxtDel_slot ,Discount

 public    OrderDetails orderDetails=new OrderDetails();

    public  Time_slab time_slab=new Time_slab();
    public  Price price=new Price();
    public  Timestamps_cal timestamps_cal=new Timestamps_cal();
    public  VAS_Rate vas_rate=new VAS_Rate();


    public  float totalFee=0;
    public  float grossFee=0;
    public  float baseFee =0;

    public  float totalPremium =0;
    public  float premiumperhour =0;

    public  float vasFee =0;
    public  float totalFeeBeforeDis =0;

    public  float baseTAT=0;
    public  float vasTAT=0;
    public  float totalTAT=0;

    public float discountPer=0;


    public  float totalDuration;
    public  int totalHolidays=0;
    public  int holidaysDB=0;
    public  int holidaysSundays=0;

    String timestamp_id ="";


    public Delivery_slot curDelivery_slot= new Delivery_slot();
    public  Delivery_slot nextDelivery_slot= new Delivery_slot();

    public  Discount discount=new Discount();
    public  double discount_amt=0.0;


    public Calendar deliveryDateTime=Calendar.getInstance();


    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Time_slab getTime_slab() {
        return time_slab;
    }

    public void setTime_slab(Time_slab time_slab) {
        this.time_slab = time_slab;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Timestamps_cal getTimestamps_cal() {
        return timestamps_cal;
    }

    public void setTimestamps_cal(Timestamps_cal timestamps_cal) {
        this.timestamps_cal = timestamps_cal;
    }

    public VAS_Rate getVas_rate() {
        return vas_rate;
    }

    public void setVas_rate(VAS_Rate vas_rate) {
        this.vas_rate = vas_rate;
    }

    public float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public float getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(float baseFee) {
        this.baseFee = baseFee;
    }

    public float getVasFee() {
        return vasFee;
    }

    public void setVasFee(float vasFee) {
        this.vasFee = vasFee;
    }

    public float getTotalFeeBeforeDis() {
        return totalFeeBeforeDis;
    }

    public void setTotalFeeBeforeDis(float totalFeeBeforeDis) {
        this.totalFeeBeforeDis = totalFeeBeforeDis;
    }

    public float getBaseTAT() {
        return baseTAT;
    }

    public void setBaseTAT(float baseTAT) {
        this.baseTAT = baseTAT;
    }

    public float getVasTAT() {
        return vasTAT;
    }

    public void setVasTAT(float vasTAT) {
        this.vasTAT = vasTAT;
    }

    public float getTotalTAT() {
        return totalTAT;
    }

    public void setTotalTAT(float totalTAT) {
        this.totalTAT = totalTAT;
    }

    public float getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(float discountPer) {
        this.discountPer = discountPer;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getTotalHolidays() {
        return totalHolidays;
    }

    public void setTotalHolidays(int totalHolidays) {
        this.totalHolidays = totalHolidays;
    }

    public int getHolidaysDB() {
        return holidaysDB;
    }

    public void setHolidaysDB(int holidaysDB) {
        this.holidaysDB = holidaysDB;
    }

    public int getHolidaysSundays() {
        return holidaysSundays;
    }

    public void setHolidaysSundays(int holidaysSundays) {
        this.holidaysSundays = holidaysSundays;
    }

    public Delivery_slot getCurDelivery_slot() {
        return curDelivery_slot;
    }

    public void setCurDelivery_slot(Delivery_slot curDelivery_slot) {
        this.curDelivery_slot = curDelivery_slot;
    }

    public Delivery_slot getNextDelivery_slot() {
        return nextDelivery_slot;
    }

    public void setNextDelivery_slot(Delivery_slot nextDelivery_slot) {
        this.nextDelivery_slot = nextDelivery_slot;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Calendar getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(Calendar deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public float getGrossFee() {
        return grossFee;
    }

    public void setGrossFee(float grossFee) {
        this.grossFee = grossFee;
    }

    public double getDiscount_amt() {
        return discount_amt;
    }

    public void setDiscount_amt(double discount_amt) {
        this.discount_amt = discount_amt;
    }

    public String getTimestamp_id() {
        return timestamp_id;
    }

    public void setTimestamp_id(String timestamp_id) {
        this.timestamp_id = timestamp_id;
    }

    public float getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(float totalPremium) {
        this.totalPremium = totalPremium;
    }

    public float getPremiumperhour() {
        return premiumperhour;
    }

    public void setPremiumperhour(float premiumperhour) {
        this.premiumperhour = premiumperhour;
    }
}
