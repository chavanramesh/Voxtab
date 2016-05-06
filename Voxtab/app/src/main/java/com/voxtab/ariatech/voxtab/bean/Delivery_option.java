

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Delivery_option {



 String  delivery_opt_id ="" ;
 String  del_days ="" ;
 String  del_option ="" ;
 String  status ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setDelivery_opt_id(String delivery_opt_id) {
        this.delivery_opt_id = delivery_opt_id;
    }
 public String getDelivery_opt_id() {
        return delivery_opt_id;
    }

public void setDel_days(String del_days) {
        this.del_days = del_days;
    }
 public String getDel_days() {
        return del_days;
    }

public void setDel_option(String del_option) {
        this.del_option = del_option;
    }
 public String getDel_option() {
        return del_option;
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
if (jsonObject.has("delivery_opt_id")) { 
 try {
 delivery_opt_id= jsonObject.getString("delivery_opt_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("del_days")) { 
 try {
 del_days= jsonObject.getString("del_days");
 } catch (Exception e) {}
 }

if (jsonObject.has("del_option")) { 
 try {
 del_option= jsonObject.getString("del_option");
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