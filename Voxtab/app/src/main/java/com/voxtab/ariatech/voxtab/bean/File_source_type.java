

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class File_source_type {



 String  source_type ="" ;
 String  source_name ="" ;
 String  active ="" ;
 String  soft_del ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
public void setSource_type(String source_type) {
        this.source_type = source_type;
    }
 public String getSource_type() {
        return source_type;
    }

public void setSource_name(String source_name) {
        this.source_name = source_name;
    }
 public String getSource_name() {
        return source_name;
    }

public void setActive(String active) {
        this.active = active;
    }
 public String getActive() {
        return active;
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
if (jsonObject.has("source_type")) { 
 try {
 source_type= jsonObject.getString("source_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("source_name")) { 
 try {
 source_name= jsonObject.getString("source_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("active")) { 
 try {
 active= jsonObject.getString("active");
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