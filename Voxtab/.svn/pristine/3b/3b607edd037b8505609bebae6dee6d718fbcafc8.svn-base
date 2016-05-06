

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Order_vas {



 String  order_vas_id ="" ;
 int  order_id =0;
 int  vas_id =0;
public void setOrder_vas_id(String order_vas_id) {
        this.order_vas_id = order_vas_id;
    }
 public String getOrder_vas_id() {
        return order_vas_id;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

 public void setVas_id(int vas_id) {
        this.vas_id = vas_id;
    }
 public int getVas_id() {
        return vas_id;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("order_vas_id")) { 
 try {
 order_vas_id= jsonObject.getString("order_vas_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("vas_id")) { 
 try {
 vas_id= Integer.parseInt(jsonObject.getString("vas_id"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END