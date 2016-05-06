

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Notification {



 int  noti_id =0;
 String  noti_title ="" ;
 String  noti_type ="" ;
 String  notifi_txt ="" ;
 int  order_id =0;
 String  status ="" ;
 String  created_date ="" ;
 String  assignment_no ="" ;
 String  soft_del ="0" ;
 public void setNoti_id(int noti_id) {
        this.noti_id = noti_id;
    }
 public int getNoti_id() {
        return noti_id;
    }

public void setNoti_type(String noti_type) {
        this.noti_type = noti_type;
    }
 public String getNoti_type() {
        return noti_type;
    }

public void setNotifi_txt(String notifi_txt) {
        this.notifi_txt = notifi_txt;
    }
 public String getNotifi_txt() {
        return notifi_txt;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
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

public void setAssignment_no(String modified_date) {
        this.assignment_no = modified_date;
    }
 public String getAssignment_no() {
        return assignment_no;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
    }

        public String getNoti_title() {
            return noti_title;
        }

        public void setNoti_title(String noti_title) {
            this.noti_title = noti_title;
        }

        public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("noti_id")) { 
 try {
 noti_id= Integer.parseInt(jsonObject.getString("noti_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("noti_type")) { 
 try {
 noti_type= jsonObject.getString("noti_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("notifi_txt")) { 
 try {
 notifi_txt= jsonObject.getString("notifi_txt");
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
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

if (jsonObject.has("assignment_no")) {
 try {
     assignment_no= jsonObject.getString("assignment_no");
 } catch (Exception e) {}
 }

if (jsonObject.has("soft_del")) {
 try {
 soft_del= jsonObject.getString("soft_del");
 } catch (Exception e) {}
 }

     if (jsonObject.has("noti_title")) {
         try {
             noti_title= jsonObject.getString("noti_title");
         } catch (Exception e) {}
     }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END