package com.voxtab.ariatech.voxtab.bean;

import java.util.Calendar;

/**
 * Created by Local User on 17-Feb-16.
 */
public class ChangePrice {
    String price, date_time, scheme_name, scheme_duration;


    TAT_Calculation tat_calculation=new TAT_Calculation();
    String id, date_to = "";

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public Calendar deliveryTime = Calendar.getInstance();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }

    public String getScheme_duration() {
        return scheme_duration;
    }

    public void setScheme_duration(String scheme_duration) {
        this.scheme_duration = scheme_duration;
    }

    public Calendar getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Calendar deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public TAT_Calculation getTat_calculation() {
        return tat_calculation;
    }

    public void setTat_calculation(TAT_Calculation tat_calculation) {
        this.tat_calculation = tat_calculation;
    }
}
