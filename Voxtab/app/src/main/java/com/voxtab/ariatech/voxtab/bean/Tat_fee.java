

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Tat_fee {
 int  tat_fee_id =0;
 String  delivery_opt_id ="" ;
 String  time_slab_id ="" ;
 String  min_charges ="" ;
 String  tat ="" ;
 String  feepermin ="" ;
 String  status ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
 public void setTat_fee_id(int tat_fee_id) {
        this.tat_fee_id = tat_fee_id;
    }
 public int getTat_fee_id() {
        return tat_fee_id;
    }

public void setDelivery_opt_id(String delivery_opt_id) {
        this.delivery_opt_id = delivery_opt_id;
    }
 public String getDelivery_opt_id() {
        return delivery_opt_id;
    }

public void setTime_slab_id(String time_slab_id) {
        this.time_slab_id = time_slab_id;
    }
 public String getTime_slab_id() {
        return time_slab_id;
    }

public void setMin_charges(String min_charges) {
        this.min_charges = min_charges;
    }
 public String getMin_charges() {
        return min_charges;
    }

public void setTat(String tat) {
        this.tat = tat;
    }
 public String getTat() {
        return tat;
    }

public void setFeepermin(String feepermin) {
        this.feepermin = feepermin;
    }
 public String getFeepermin() {
        return feepermin;
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
if (jsonObject.has("tat_fee_id")) { 
 try {
 tat_fee_id= Integer.parseInt(jsonObject.getString("tat_fee_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("delivery_opt_id")) { 
 try {
 delivery_opt_id= jsonObject.getString("delivery_opt_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("time_slab_id")) { 
 try {
 time_slab_id= jsonObject.getString("time_slab_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("min_charges")) { 
 try {
 min_charges= jsonObject.getString("min_charges");
 } catch (Exception e) {}
 }

if (jsonObject.has("tat")) { 
 try {
 tat= jsonObject.getString("tat");
 } catch (Exception e) {}
 }

if (jsonObject.has("feepermin")) { 
 try {
 feepermin= jsonObject.getString("feepermin");
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