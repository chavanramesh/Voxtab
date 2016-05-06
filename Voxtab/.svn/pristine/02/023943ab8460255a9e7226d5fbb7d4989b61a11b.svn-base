

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Order_free_services {



 int  order_free_serid =0;
 int  order_id =0;
 int  free_serv_id =0;
 String  modified_date ="" ;
 public void setOrder_free_serid(int order_free_serid) {
        this.order_free_serid = order_free_serid;
    }
 public int getOrder_free_serid() {
        return order_free_serid;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

 public void setFree_serv_id(int free_serv_id) {
        this.free_serv_id = free_serv_id;
    }
 public int getFree_serv_id() {
        return free_serv_id;
    }

public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
 public String getModified_date() {
        return modified_date;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("order_free_serid")) { 
 try {
 order_free_serid= Integer.parseInt(jsonObject.getString("order_free_serid"));
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("free_serv_id")) { 
 try {
 free_serv_id= Integer.parseInt(jsonObject.getString("free_serv_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("modified_date")) { 
 try {
 modified_date= jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END