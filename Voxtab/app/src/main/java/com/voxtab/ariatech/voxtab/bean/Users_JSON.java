package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/4/2016.
 */
public class Users_JSON {

    String membership_id = "";
    String location_id = "";
    String email = "";
    String last_name = "";
    String first_name = "";
    String imei = "";
    String device_type = "";
    String device_name = "";
    String device_os = "";
    int user_id =0;

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_os() {
        return device_os;
    }

    public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

  public void parseJSON(JSONObject jsonObject) {


        try {
            if (jsonObject.has("user_id")) {
                try {
                    user_id= Integer.parseInt(jsonObject.getString("user_id"));
                } catch (Exception e) {}
            }

            if (jsonObject.has("membership_id")) {
                try {
                    membership_id= jsonObject.getString("membership_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("location_id")) {
                try {
                    location_id= jsonObject.getString("location_id");
                } catch (Exception e) {}
            }


            if (jsonObject.has("email")) {
                try {
                    email= jsonObject.getString("email");
                } catch (Exception e) {}
            }


            if (jsonObject.has("last_name")) {
                try {
                    last_name= jsonObject.getString("last_name");
                } catch (Exception e) {}
            }


            if (jsonObject.has("imei")) {
                try {
                    imei= jsonObject.getString("imei");
                } catch (Exception e) {}
            }

            if (jsonObject.has("first_name")) {
                try {
                    first_name= jsonObject.getString("first_name");
                } catch (Exception e) {}
            }
            if (jsonObject.has("device_type")) {
                try {
                    device_type= jsonObject.getString("device_type");
                } catch (Exception e) {}
            }


            if (jsonObject.has("device_name")) {
                try {
                    device_name= jsonObject.getString("device_name");
                } catch (Exception e) {}
            }

            if (jsonObject.has("device_os")) {
                try {
                    device_os= jsonObject.getString("device_os");
                } catch (Exception e) {}
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END

