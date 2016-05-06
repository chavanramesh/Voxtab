

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class OrderHistory {



 int  orderHistoryId =0;
 int  orderId =0;
 int  uploadingMasterId =0;
 String  orderStatus ="" ;
 String  price ="" ;
 String  deliveryDate ="" ;
 String  orderHistoryNo ="" ;
 public void setOrderHistoryId(int orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }
 public int getOrderHistoryId() {
        return orderHistoryId;
    }

 public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
 public int getOrderId() {
        return orderId;
    }

 public void setUploadingMasterId(int uploadingMasterId) {
        this.uploadingMasterId = uploadingMasterId;
    }
 public int getUploadingMasterId() {
        return uploadingMasterId;
    }

public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
 public String getOrderStatus() {
        return orderStatus;
    }

public void setPrice(String price) {
        this.price = price;
    }
 public String getPrice() {
        return price;
    }

public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
 public String getDeliveryDate() {
        return deliveryDate;
    }

public void setOrderHistoryNo(String orderHistoryNo) {
        this.orderHistoryNo = orderHistoryNo;
    }
 public String getOrderHistoryNo() {
        return orderHistoryNo;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("orderHistoryId")) { 
 try {
 orderHistoryId= Integer.parseInt(jsonObject.getString("orderHistoryId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("orderId")) { 
 try {
 orderId= Integer.parseInt(jsonObject.getString("orderId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("uploadingMasterId")) { 
 try {
 uploadingMasterId= Integer.parseInt(jsonObject.getString("uploadingMasterId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("orderStatus")) { 
 try {
 orderStatus= jsonObject.getString("orderStatus");
 } catch (Exception e) {}
 }

if (jsonObject.has("price")) { 
 try {
 price= jsonObject.getString("price");
 } catch (Exception e) {}
 }

if (jsonObject.has("deliveryDate")) { 
 try {
 deliveryDate= jsonObject.getString("deliveryDate");
 } catch (Exception e) {}
 }

if (jsonObject.has("orderHistoryNo")) { 
 try {
 orderHistoryNo= jsonObject.getString("orderHistoryNo");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END