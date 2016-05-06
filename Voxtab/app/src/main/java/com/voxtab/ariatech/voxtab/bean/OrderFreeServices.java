

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class OrderFreeServices {



 int  orderFreeServId =0;
 int  orderId =0;
 int  freeServId =0;
 public void setOrderFreeServId(int orderFreeServId) {
        this.orderFreeServId = orderFreeServId;
    }
 public int getOrderFreeServId() {
        return orderFreeServId;
    }

 public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
 public int getOrderId() {
        return orderId;
    }

 public void setFreeServId(int freeServId) {
        this.freeServId = freeServId;
    }
 public int getFreeServId() {
        return freeServId;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("orderFreeServId")) { 
 try {
 orderFreeServId= Integer.parseInt(jsonObject.getString("orderFreeServId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("orderId")) { 
 try {
 orderId= Integer.parseInt(jsonObject.getString("orderId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("freeServId")) { 
 try {
 freeServId= Integer.parseInt(jsonObject.getString("freeServId"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END