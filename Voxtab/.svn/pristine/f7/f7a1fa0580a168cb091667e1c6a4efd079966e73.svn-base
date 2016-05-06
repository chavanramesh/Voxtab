


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class OrderStatus {


    int orderStatusId = 0;
    int orderId = 0;
    int recId = 0;
    int statusId = 0;
    String statusTEXT = "";

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public int getRecId() {
        return recId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusTEXT(String statusTEXT) {
        this.statusTEXT = statusTEXT;
    }

    public String getStatusTEXT() {
        return statusTEXT;
    }


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("orderStatusId")) {
                try {
                    orderStatusId = Integer.parseInt(jsonObject.getString("orderStatusId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("orderId")) {
                try {
                    orderId = Integer.parseInt(jsonObject.getString("orderId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("recId")) {
                try {
                    recId = Integer.parseInt(jsonObject.getString("recId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("statusId")) {
                try {
                    statusId = Integer.parseInt(jsonObject.getString("statusId"));
                } catch (Exception e) {
                }
            }

            if (jsonObject.has("statusTEXT")) {
                try {
                    statusTEXT = jsonObject.getString("statusTEXT");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END