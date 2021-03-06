package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/5/2016.
 */
public class Timestamps_cal {

    int timestamp_calculation_id=0;
    String service_type_id="";
    String percentage_value="";
    String delivery_opt_id="";
    String timestamp_id="";
    String created_date="";
    String modified_date="";
    String soft_del ="";
    String premium_type_id = "";

    public int getTimestamp_calculation_id() {
        return timestamp_calculation_id;
    }

    public void setTimestamp_calculation_id(int timestamp_calculation_id) {
        this.timestamp_calculation_id = timestamp_calculation_id;
    }

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getPercentage_value() {
        return percentage_value;
    }

    public void setPercentage_value(String percentage_value) {
        this.percentage_value = percentage_value;
    }

    public String getDelivery_opt_id() {
        return delivery_opt_id;
    }

    public void setDelivery_opt_id(String delivery_opt_id) {
        this.delivery_opt_id = delivery_opt_id;
    }

    public String getTimestamp_id() {
        return timestamp_id;
    }

    public void setTimestamp_id(String timestamp_id) {
        this.timestamp_id = timestamp_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getSoft_del() {
        return soft_del;
    }

    public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }


    public String getPremium_type_id() {
        return premium_type_id;
    }

    public void setPremium_type_id(String premium_type_id) {
        this.premium_type_id = premium_type_id;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {



            if (jsonObject.has("timestamp_calculation_id")) {
                try {
                    timestamp_calculation_id= Integer.parseInt( jsonObject.getString("timestamp_calculation_id"));
                } catch (Exception e) {}
            }

            if (jsonObject.has("service_type_id")) {
                try {
                    service_type_id= jsonObject.getString("service_type_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("percentage_value")) {
                try {
                    percentage_value= jsonObject.getString("percentage_value");
                } catch (Exception e) {}
            }

            if (jsonObject.has("delivery_opt_id")) {
                try {
                    delivery_opt_id= jsonObject.getString("delivery_opt_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("timestamp_id")) {
                try {
                    timestamp_id= jsonObject.getString("timestamp_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("created_date")) {
                try {
                    created_date= jsonObject.getString("created_date");
                } catch (Exception e) {}
            }

            if (jsonObject.has("modified_date")) {
                try {
                    modified_date= jsonObject.getString("modified_date");
                } catch (Exception e) {}
            }

            //

            if (jsonObject.has("soft_del")) {
                try {
                    soft_del= jsonObject.getString("soft_del");
                } catch (Exception e) {}
            }

            if (jsonObject.has("premium_type_id")) {
                try {
                    premium_type_id= jsonObject.getString("premium_type_id");
                } catch (Exception e) {}
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END

