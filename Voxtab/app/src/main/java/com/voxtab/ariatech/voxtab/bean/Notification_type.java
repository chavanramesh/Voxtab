

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Notification_type {



 String  noti_type_id ="" ;
 String  notification_name ="" ;
 String  template_desc ="" ;
 String  created_on ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setNoti_type_id(String noti_type_id) {
        this.noti_type_id = noti_type_id;
    }
 public String getNoti_type_id() {
        return noti_type_id;
    }

public void setNotification_name(String notification_name) {
        this.notification_name = notification_name;
    }
 public String getNotification_name() {
        return notification_name;
    }

public void setTemplate_desc(String template_desc) {
        this.template_desc = template_desc;
    }
 public String getTemplate_desc() {
        return template_desc;
    }

public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
 public String getCreated_on() {
        return created_on;
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
if (jsonObject.has("noti_type_id")) { 
 try {
 noti_type_id= jsonObject.getString("noti_type_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("notification_name")) { 
 try {
 notification_name= jsonObject.getString("notification_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("template_desc")) { 
 try {
 template_desc= jsonObject.getString("template_desc");
 } catch (Exception e) {}
 }

if (jsonObject.has("created_on")) { 
 try {
 created_on= jsonObject.getString("created_on");
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