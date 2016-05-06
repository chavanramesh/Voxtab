

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Security_permission {



 int  elm_id =0;
 int  group_id =0;
 String  lastmodifed_date ="" ;
 public void setElm_id(int elm_id) {
        this.elm_id = elm_id;
    }
 public int getElm_id() {
        return elm_id;
    }

 public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
 public int getGroup_id() {
        return group_id;
    }

public void setLastmodifed_date(String lastmodifed_date) {
        this.lastmodifed_date = lastmodifed_date;
    }
 public String getLastmodifed_date() {
        return lastmodifed_date;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("elm_id")) { 
 try {
 elm_id= Integer.parseInt(jsonObject.getString("elm_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("group_id")) { 
 try {
 group_id= Integer.parseInt(jsonObject.getString("group_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("lastmodifed_date")) { 
 try {
 lastmodifed_date= jsonObject.getString("lastmodifed_date");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END