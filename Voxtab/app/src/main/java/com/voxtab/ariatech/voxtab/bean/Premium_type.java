

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Premium_type {



 String  premium_type_id ="" ;
 String  premium_type ="" ;
 String  status ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setPremium_type_id(String premium_type_id) {
        this.premium_type_id = premium_type_id;
    }
 public String getPremium_type_id() {
        return premium_type_id;
    }

public void setPremium_type(String premium_type) {
        this.premium_type = premium_type;
    }
 public String getPremium_type() {
        return premium_type;
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
if (jsonObject.has("premium_type_id")) { 
 try {
 premium_type_id= jsonObject.getString("premium_type_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("premium_type")) { 
 try {
 premium_type= jsonObject.getString("premium_type");
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