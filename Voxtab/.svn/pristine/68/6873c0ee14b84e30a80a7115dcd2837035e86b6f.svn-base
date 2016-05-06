


package com.voxtab.ariatech.voxtab.bean;


import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

public class OrderRecordings {


    int orderRecId = 0;
    int orderId = 0;
    int recId = 0;

    public void setOrderRecId(int orderRecId) {
        this.orderRecId = orderRecId;
    }

    public int getOrderRecId() {
        return orderRecId;
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


    public void parseJSON(JSONObject jsonObject) {
        try {
            if (jsonObject.has("orderRecId")) {
                try {
                    orderRecId = Integer.parseInt(jsonObject.getString("orderRecId"));
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
        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END