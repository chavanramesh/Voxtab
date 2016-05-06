

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Delivery_speed {



 String  delivery_speed_id ="" ;
 String  service_type_id ="" ;
 String  time_slab_id ="" ;
 String  delivery_option_id ="" ;
 String  rate ="" ;
 String  status ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setDelivery_speed_id(String delivery_speed_id) {
        this.delivery_speed_id = delivery_speed_id;
    }
 public String getDelivery_speed_id() {
        return delivery_speed_id;
    }

public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }
 public String getService_type_id() {
        return service_type_id;
    }

public void setTime_slab_id(String time_slab_id) {
        this.time_slab_id = time_slab_id;
    }
 public String getTime_slab_id() {
        return time_slab_id;
    }

public void setDelivery_option_id(String delivery_option_id) {
        this.delivery_option_id = delivery_option_id;
    }
 public String getDelivery_option_id() {
        return delivery_option_id;
    }

public void setRate(String rate) {
        this.rate = rate;
    }
 public String getRate() {
        return rate;
    }

public void setStatus(String status) {
        this.status = status;
    }
 public String getStatus() {
        return status;
    }

public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
 public String getCreated_date() {
        return created_date;
    }

public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
 public String getModified_date() {
        return modified_date;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("delivery_speed_id")) { 
 try {
 delivery_speed_id= jsonObject.getString("delivery_speed_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("service_type_id")) { 
 try {
 service_type_id= jsonObject.getString("service_type_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("time_slab_id")) { 
 try {
 time_slab_id= jsonObject.getString("time_slab_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("delivery_option_id")) { 
 try {
 delivery_option_id= jsonObject.getString("delivery_option_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("rate")) { 
 try {
 rate= jsonObject.getString("rate");
 } catch (Exception e) {}
 }

if (jsonObject.has("status")) { 
 try {
 status= jsonObject.getString("status");
 } catch (Exception e) {}
 }

if (jsonObject.has("created_date")) { 
 try {
 created_date= jsonObject.getString("created_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("modified_date")) { 
 try {
 modified_date= jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("soft_del")) { 
 try {
 soft_del= jsonObject.getString("soft_del");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END