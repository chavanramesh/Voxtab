


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class Time_slab {


    String time_slab_id = "";
    String slab_from = "";
    String default_set = "";
    String status = "";
    String modified_date = "";
    String created_date = "";
    String slab_to = "";
    String soft_del = "";

    String is_last="0";


    public void setTime_slab_id(String time_slab_id) {
        this.time_slab_id = time_slab_id;
    }

    public String getTime_slab_id() {
        return time_slab_id;
    }

    public void setSlab_from(String slab_from) {
        this.slab_from = slab_from;
    }

    public String getSlab_from() {
        return slab_from;
    }

    public void setDefault_set(String default_set) {
        this.default_set = default_set;
    }

    public String getDefault_set() {
        return default_set;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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

    public void setSlab_to(String slab_to) {
        this.slab_to = slab_to;
    }

    public String getSlab_to() {
        return slab_to;
    }

    public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }

    public String getSoft_del() {
        return soft_del;
    }

    public String getIs_last() {
        return is_last;
    }

    public void setIs_last(String is_last) {
        this.is_last = is_last;
    }

    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("time_slab_id")) {
                try {
                    time_slab_id = jsonObject.getString("time_slab_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("slab_from")) {
                try {
                    slab_from = jsonObject.getString("slab_from");
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

            if (jsonObject.has("created_date")) {
                try {
                    created_date = jsonObject.getString("created_date");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("slab_to")) {
                try {
                    slab_to = jsonObject.getString("slab_to");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("soft_del")) {
                try {
                    soft_del = jsonObject.getString("soft_del");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("is_last")) {
                try {
                    is_last = jsonObject.getString("is_last");
                } catch (Exception e) {
                }
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END