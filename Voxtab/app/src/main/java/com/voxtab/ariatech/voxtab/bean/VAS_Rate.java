package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/2/2016.
 */
public class VAS_Rate {

    String vas_rate = "" ;
    String time_slab_id = "";

    String vas_id  = "";
    String tat = "";
    String price ="";
    String min_charges ="";

    String modified_date ="";
    String created_date ="";
    String soft_del ="";

    String service_type_id="eng";

    public String getVas_rate() {
        return vas_rate;
    }

    public void setVas_rate(String vas_rate) {
        this.vas_rate = vas_rate;
    }

    public String getTime_slab_id() {
        return time_slab_id;
    }

    public void setTime_slab_id(String time_slab_id) {
        this.time_slab_id = time_slab_id;
    }

    public String getVas_id() {
        return vas_id;
    }

    public void setVas_id(String vas_id) {
        this.vas_id = vas_id;
    }

    public String getTat() {
        return tat;
    }

    public void setTat(String tat) {
        this.tat = tat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMin_charges() {
        return min_charges;
    }

    public void setMin_charges(String min_charges) {
        this.min_charges = min_charges;
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

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

//vas_rate INTEGER PRIMARY KEY,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT, modified_date TEXT, created_date TEXT, soft_del TEXT);


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("vas_rate")) {
                try {
                    vas_rate = (jsonObject.getString("vas_rate"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("time_slab_id")) {
                try {
                    time_slab_id = jsonObject.getString("time_slab_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("vas_id")) {
                try {
                    vas_id = jsonObject.getString("vas_id");
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("tat")) {
                try {
                    tat = jsonObject.getString("tat");
                } catch (Exception e) {
                }
            }



            if (jsonObject.has("price")) {
                try {
                    price = jsonObject.getString("price");
                } catch (Exception e) {
                }
            }


            if (jsonObject.has("min_charges")) {
                try {
                    min_charges = jsonObject.getString("min_charges");
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


            if (jsonObject.has("service_type_id")) {
                try {
                    service_type_id = jsonObject.getString("service_type_id");
                } catch (Exception e) {
                }
            }



        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }
}
