

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Sys_log {



 int  logid =0;
 String  logtime ="" ;
 int  username =0;
 int  dbname =0;
 String  logmsg ="" ;
 int  log_type =0;
 int  user_id =0;
 public void setLogid(int logid) {
        this.logid = logid;
    }
 public int getLogid() {
        return logid;
    }

public void setLogtime(String logtime) {
        this.logtime = logtime;
    }
 public String getLogtime() {
        return logtime;
    }

 public void setUsername(int username) {
        this.username = username;
    }
 public int getUsername() {
        return username;
    }

 public void setDbname(int dbname) {
        this.dbname = dbname;
    }
 public int getDbname() {
        return dbname;
    }

public void setLogmsg(String logmsg) {
        this.logmsg = logmsg;
    }
 public String getLogmsg() {
        return logmsg;
    }

 public void setLog_type(int log_type) {
        this.log_type = log_type;
    }
 public int getLog_type() {
        return log_type;
    }

 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("logid")) { 
 try {
 logid= Integer.parseInt(jsonObject.getString("logid"));
 } catch (Exception e) {}
 }

if (jsonObject.has("logtime")) { 
 try {
 logtime= jsonObject.getString("logtime");
 } catch (Exception e) {}
 }

if (jsonObject.has("username")) { 
 try {
 username= Integer.parseInt(jsonObject.getString("username"));
 } catch (Exception e) {}
 }

if (jsonObject.has("dbname")) { 
 try {
 dbname= Integer.parseInt(jsonObject.getString("dbname"));
 } catch (Exception e) {}
 }

if (jsonObject.has("logmsg")) { 
 try {
 logmsg= jsonObject.getString("logmsg");
 } catch (Exception e) {}
 }

if (jsonObject.has("log_type")) { 
 try {
 log_type= Integer.parseInt(jsonObject.getString("log_type"));
 } catch (Exception e) {}
 }

if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END