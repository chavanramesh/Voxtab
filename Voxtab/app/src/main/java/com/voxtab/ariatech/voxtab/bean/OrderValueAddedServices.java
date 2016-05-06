

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class OrderValueAddedServices {



 int  orderValueAddedServicesId =0;
 int  orderId =0;
 int  valueAddedServiceId =0;
 public void setOrderValueAddedServicesId(int orderValueAddedServicesId) {
        this.orderValueAddedServicesId = orderValueAddedServicesId;
    }
 public int getOrderValueAddedServicesId() {
        return orderValueAddedServicesId;
    }

 public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
 public int getOrderId() {
        return orderId;
    }

 public void setValueAddedServiceId(int valueAddedServiceId) {
        this.valueAddedServiceId = valueAddedServiceId;
    }
 public int getValueAddedServiceId() {
        return valueAddedServiceId;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("orderValueAddedServicesId")) { 
 try {
 orderValueAddedServicesId= Integer.parseInt(jsonObject.getString("orderValueAddedServicesId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("orderId")) { 
 try {
 orderId= Integer.parseInt(jsonObject.getString("orderId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("valueAddedServiceId")) { 
 try {
 valueAddedServiceId= Integer.parseInt(jsonObject.getString("valueAddedServiceId"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END