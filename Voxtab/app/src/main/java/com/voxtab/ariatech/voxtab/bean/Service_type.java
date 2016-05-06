


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class Service_type {


    String service_type_id = "";
    String service_text = "";
    String status = "";
    String default_select = "";
    String soft_del = "";
    String modified_date = "";
    String created_date = "";

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_text(String service_text) {
        this.service_text = service_text;
    }

    public String getService_text() {
        return service_text;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDefault_select(String default_select) {
        this.default_select = default_select;
    }

    public String getDefault_select() {
        return default_select;
    }

    public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }

    public String getSoft_del() {
        return soft_del;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_date() {
        return created_date;
    }


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("service_type_id")) {
                try {
                    service_type_id = jsonObject.getString("service_type_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("service_text")) {
                try {
                    service_text = jsonObject.getString("service_text");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("status")) {
                try {
                    status = jsonObject.getString("status");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("default_select")) {
                try {
                    default_select = jsonObject.getString("default_select");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("soft_del")) {
                try {
                    soft_del = jsonObject.getString("soft_del");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("modified_date")) {
                try {
                    modified_date = jsonObject.getString("modified_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("created_date")) {
                try {
                    created_date = jsonObject.getString("created_date");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END