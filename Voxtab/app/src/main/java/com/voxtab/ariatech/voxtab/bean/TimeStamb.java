package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/2/2016.
 */
public class TimeStamb {


    String timestamp_id ="";
    String timestamp_txt ="";
    String default_set="";
    String status="";
    String modified_date ="";
    String  created_date ="";
    String soft_del ="";
    String premium_type_id ="";


    public String getTimestamp_id() {
        return timestamp_id;
    }

    public void setTimestamp_id(String timestamp_id) {
        this.timestamp_id = timestamp_id;
    }

    public String getTimestamp_txt() {
        return timestamp_txt;
    }

    public void setTimestamp_txt(String timestamp_txt) {
        this.timestamp_txt = timestamp_txt;
    }

    public String getDefault_set() {
        return default_set;
    }

    public void setDefault_set(String default_set) {
        this.default_set = default_set;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
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
//timestamp_id TEXT PRIMARY KEY,   TEXT,   TEXT,   TEXT,  modified_date TEXT,  created_date TEXT, soft_del TEXT

    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("timestamp_id")) {
                try {
                    timestamp_id = jsonObject.getString("timestamp_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("timestamp_txt")) {
                try {
                    timestamp_txt = jsonObject.getString("timestamp_txt");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("default_set")) {
                try {
                    default_set = jsonObject.getString("default_set");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("status")) {
                try {
                    status = jsonObject.getString("status");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("modified_date")) {
                try {
                    modified_date = jsonObject.getString("modified_date");
                } catch (Exception e) {
                }
            }


            if (jsonObject.has("soft_del")) {
                try {
                    soft_del = jsonObject.getString("soft_del");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("created_date")) {
                try {
                    created_date = jsonObject.getString("created_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("premium_type_id")) {
                try {
                    premium_type_id = jsonObject.getString("premium_type_id");
                } catch (Exception e) {
                }
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }






}
