

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Msg_logs {



 int  log_id =0;
 int  user_id =0;
 int  client_id =0;
 int  app_id =0;
 String  msg ="" ;
 String  msg_date ="" ;
 String  title ="" ;
 public void setLog_id(int log_id) {
        this.log_id = log_id;
    }
 public int getLog_id() {
        return log_id;
    }

 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }

 public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
 public int getClient_id() {
        return client_id;
    }

 public void setApp_id(int app_id) {
        this.app_id = app_id;
    }
 public int getApp_id() {
        return app_id;
    }

public void setMsg(String msg) {
        this.msg = msg;
    }
 public String getMsg() {
        return msg;
    }

public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }
 public String getMsg_date() {
        return msg_date;
    }

public void setTitle(String title) {
        this.title = title;
    }
 public String getTitle() {
        return title;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("log_id")) { 
 try {
 log_id= Integer.parseInt(jsonObject.getString("log_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("client_id")) { 
 try {
 client_id= Integer.parseInt(jsonObject.getString("client_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("app_id")) { 
 try {
 app_id= Integer.parseInt(jsonObject.getString("app_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("msg")) { 
 try {
 msg= jsonObject.getString("msg");
 } catch (Exception e) {}
 }

if (jsonObject.has("msg_date")) { 
 try {
 msg_date= jsonObject.getString("msg_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("title")) { 
 try {
 title= jsonObject.getString("title");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END