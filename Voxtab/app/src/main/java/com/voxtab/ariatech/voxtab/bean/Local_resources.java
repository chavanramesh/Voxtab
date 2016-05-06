

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Local_resources {



 int  resource_id =0;
 String  lng_id ="" ;
 String  resource_name ="" ;
 String  resource_value ="" ;
 String  soft_del ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }
 public int getResource_id() {
        return resource_id;
    }

public void setLng_id(String lng_id) {
        this.lng_id = lng_id;
    }
 public String getLng_id() {
        return lng_id;
    }

public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }
 public String getResource_name() {
        return resource_name;
    }

public void setResource_value(String resource_value) {
        this.resource_value = resource_value;
    }
 public String getResource_value() {
        return resource_value;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
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




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("resource_id")) { 
 try {
 resource_id= Integer.parseInt(jsonObject.getString("resource_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("lng_id")) { 
 try {
 lng_id= jsonObject.getString("lng_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("resource_name")) { 
 try {
 resource_name= jsonObject.getString("resource_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("resource_value")) { 
 try {
 resource_value= jsonObject.getString("resource_value");
 } catch (Exception e) {}
 }

if (jsonObject.has("soft_del")) { 
 try {
 soft_del= jsonObject.getString("soft_del");
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
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END